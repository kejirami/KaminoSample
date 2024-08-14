package jp.or.manmaru_system.kaminosample.cifs;

import android.os.AsyncTask;

import java.util.List;
import java.util.Properties;

import jcifs.CIFSContext;
import jcifs.config.PropertyConfiguration;
import jcifs.context.BaseContext;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jp.or.manmaru_system.kaminosample.RespData;

public class GetList extends AsyncTask<Parameter, Void, RespData<SmbFile[]>> {

    private OnGetListListener mListener;

    public void setOnGetListListener(OnGetListListener listener) {
        mListener = listener;
    }

    public interface OnGetListListener {
        void OnGetList(RespData<SmbFile[]> res) throws Exception;
    }

    @Override
    protected RespData<SmbFile[]> doInBackground(Parameter... parameters) {
        RespData<SmbFile[]> respData = new RespData();
        String domain = parameters[0].host;
        String path = parameters[0].path;
        String user = parameters[0].user;
        String password = parameters[0].password;
        try {
            Properties prop = new Properties();
            prop.setProperty("jcifs.smb.client.minVersion", "SMB202");
            prop.setProperty("jcifs.smb.client.maxVersion", "SMB300");
            PropertyConfiguration a = new PropertyConfiguration(prop);
            BaseContext bc = new BaseContext(a);
            NtlmPasswordAuthentication creds = new NtlmPasswordAuthentication(bc, domain, user, password);
            CIFSContext auth = bc.withCredentials(creds);
            SmbFile smb=new SmbFile("smb://" + domain + "/" + path + "/", auth);
            respData.Result = true;
            respData.Message = "";
            respData.Data = smb.listFiles();
        } catch (Exception e) {
            respData.Result = false;
            respData.Message = e.getMessage();
        } finally {
        }

        return respData;
    }

    protected void onPostExecute(RespData<SmbFile[]> res) {
        try {
            mListener.OnGetList(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
