package jp.or.manmaru_system.kaminosample;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.riversun.promise.Promise;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import jcifs.smb.SmbFile;
import jp.or.manmaru_system.kaminosample.barcode.InputBarcodeCallback;
import jp.or.manmaru_system.kaminosample.barcode.InputBarcodeDialog;
import jp.or.manmaru_system.kaminosample.cifs.GetList;
import jp.or.manmaru_system.kaminosample.cifs.Parameter;
import jp.or.manmaru_system.kaminosample.cifs.PutFIle;
import jp.or.manmaru_system.kaminosample.database.GetRecords;
import jp.or.manmaru_system.kaminosample.localdb.LocalDB;
import jp.or.manmaru_system.kaminosample.localdb.Work;
import jp.or.manmaru_system.kaminosample.localdb.WorkDAO;

public class MainActivity extends AppCompatActivity implements GetRecords.OnGetRecordsListener, GetList.OnGetListListener, PutFIle.OnPutFIleListener {

    private Button btnSelect;
    private Button btnDirList;
    private Button btnFileSave;
    private Button btnSelectLocal;
    private Button btnTakePicture;

    private EditText etBarCode;
    private ImageView ivTakePicture;
    private LocalDB mDb;
    private ToneGenerator mToneGenerator;
    private File mImage;

    private final ActivityResultLauncher<Intent> mArl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {

                    PutFIle pf = new PutFIle();
                    pf.setOnPutFIleListener(MainActivity.this);
                    try (FileInputStream is = new FileInputStream(mImage);) {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        while(true) {
                            int len = is.read(buffer);
                            if(len < 0) break;
                            bos.write(buffer, 0, len);
                        }
                        Parameter param = new Parameter("192.168.0.100", "kamino","12345678","test/aaa.jpg",bos.toByteArray());
                        pf.execute(param);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


/*
                    Intent data = result.getData();
                    if (data != null && data.getExtras() != null) {
                        Bitmap bmp = (Bitmap) data.getExtras().get("data");
                        ivTakePicture.setImageBitmap(bmp);
                        File file = new File(MainActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "aaa.jpg");
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.close();
                        } catch (Exception e) {
                        }
                        PutFIle pf = new PutFIle();
                        pf.setOnPutFIleListener(MainActivity.this);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                        Parameter param = new Parameter("192.168.0.100", "papa","ascapbmi","test/aaa.jpg",bos.toByteArray());
                        pf.execute(param);
                    }
*/
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = LocalDB.getDatabase(MainActivity.this);
        mToneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM,ToneGenerator.MAX_VOLUME);

        btnSelect = (Button)findViewById(R.id.btn_select);
        btnDirList = (Button)findViewById(R.id.btn_dirlist);
        btnFileSave = (Button)findViewById(R.id.btn_filesave);
        btnSelectLocal = (Button)findViewById(R.id.btn_selectlocal);
        btnTakePicture = (Button)findViewById(R.id.btn_takepicture);
        etBarCode = (EditText)findViewById(R.id.et_barcode);
        ivTakePicture = (ImageView)findViewById(R.id.iv_takepicture);

        etBarCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) return;
                InputBarcodeDialog.show(MainActivity.this, mToneGenerator, new InputBarcodeCallback() {
                    @Override
                    public void run(String value) {
                        etBarCode.setText(value);
                    }
                });
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                GetRecords gr = new GetRecords();
                gr.setOnGetRecordsListener(MainActivity.this);
                GetRecords.Parameter param = new GetRecords.Parameter("SELECT * FROM nantara", new ArrayList<String>());
                gr.execute(param);
            }
        });
        btnDirList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetList gl = new GetList();
                gl.setOnGetListListener(MainActivity.this);
                Parameter param = new Parameter("192.168.0.100", "kamino","12345678","test/",null);
                gl.execute(param);
            }
        });
        btnFileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PutFIle pf = new PutFIle();
                pf.setOnPutFIleListener(MainActivity.this);
                Parameter param = new Parameter("192.168.0.100", "kamino","12345678","test/bbb.txt","あいう\r\nえお".getBytes(Charset.forName("UTF8")));
                pf.execute(param);
            }
        });
        btnSelectLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkDAO dao = mDb.workDao();
                Promise.resolve().then(new Promise((action,data)->{
                    dao.insert(new Work("Data1"));
                    List<Work> works = dao.getAll();
                    action.resolve(works);
                })).then(new Promise((action,data)->{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for(Work w : (List<Work>)data){
                                Toast.makeText(MainActivity.this,w.getMemo(),Toast.LENGTH_LONG).show();
                            }
                            action.resolve();
                        }
                    });
                })).start();
            }
        });
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File dir = getFilesDir();
                dir = new File(dir.getPath()+"/images/");
                if(!dir.exists()) dir.mkdir();
                mImage = null;
                try {
                    mImage = File.createTempFile("aaa",".jpg",dir);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Uri uri = FileProvider.getUriForFile(MainActivity.this,  "jp.or.manmaru_system.fileprovider", mImage);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                mArl.launch(intent);
            }
        });
    }

    @Override
    public void OnGetRecords(RespData<ArrayList<ArrayList<String>>> res) throws Exception {
        if(!res.Result){
            Toast.makeText(MainActivity.this,res.Message,Toast.LENGTH_LONG).show();
        }else{
            ArrayList<ArrayList<String>> data = res.Data;
            for(ArrayList<String> row : data){
                Toast.makeText(MainActivity.this,row.get(1),Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void OnGetList(RespData<SmbFile[]> res) throws Exception {
        if(!res.Result){
            Toast.makeText(MainActivity.this,res.Message,Toast.LENGTH_LONG).show();
        }else{
            SmbFile[] data = res.Data;
            for(SmbFile f : data){
                Toast.makeText(MainActivity.this,f.getName(),Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void OnPutFIle(RespData<SmbFile[]> res) throws Exception {

    }
}