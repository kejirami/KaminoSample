package jp.or.manmaru_system.kaminosample.cifs;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.Properties;

import jcifs.CIFSContext;
import jcifs.config.PropertyConfiguration;
import jcifs.context.BaseContext;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import jp.or.manmaru_system.kaminosample.RespData;

public class PutFIle extends AsyncTask<Parameter, Void, RespData<SmbFile[]>> {

    private OnPutFIleListener mListener;

    public void setOnPutFIleListener(OnPutFIleListener listener) {
        mListener = listener;
    }

    public interface OnPutFIleListener {
        void OnPutFIle(RespData<SmbFile[]> res) throws Exception;
    }

    @Override
    protected RespData<SmbFile[]> doInBackground(Parameter... parameters) {
        RespData<SmbFile[]> respData = new RespData();
        String domain = parameters[0].host;
        String path = parameters[0].path;
        String user = parameters[0].user;
        String password = parameters[0].password;
        byte[] data = parameters[0].data;
        try {
            Properties prop = new Properties();
            prop.setProperty("jcifs.smb.client.minVersion", "SMB202");
            prop.setProperty("jcifs.smb.client.maxVersion", "SMB300");
            PropertyConfiguration a = new PropertyConfiguration(prop);
            BaseContext bc = new BaseContext(a);
            NtlmPasswordAuthentication creds = new NtlmPasswordAuthentication(bc, domain, user, password);
            CIFSContext auth = bc.withCredentials(creds);
            SmbFile smb=new SmbFile("smb://" + domain + "/" + path, auth);
            respData.Result = true;
            respData.Message = "";
            BufferedOutputStream bos = new BufferedOutputStream(new SmbFileOutputStream(smb));
            bos.write(data,0, data.length);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            respData.Result = false;
            respData.Message = e.getMessage();
        } finally {
        }

        return respData;
    }

    protected void onPostExecute(RespData<SmbFile[]> res) {
        try {
            mListener.OnPutFIle(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
