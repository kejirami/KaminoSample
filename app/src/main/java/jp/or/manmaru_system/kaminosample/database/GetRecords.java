package jp.or.manmaru_system.kaminosample.database;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.or.manmaru_system.kaminosample.RespData;

public class GetRecords extends AsyncTask<GetRecords.Parameter, Void, RespData<ArrayList<ArrayList<String>>>> {

    private OnGetRecordsListener mListener;

    public void setOnGetRecordsListener(OnGetRecordsListener listener) {
        mListener = listener;
    }

    public interface OnGetRecordsListener {
        void OnGetRecords(RespData<ArrayList<ArrayList<String>>> res) throws Exception;
    }

    @Override
    protected RespData<ArrayList<ArrayList<String>>> doInBackground(Parameter... parameters) {
        RespData<ArrayList<ArrayList<String>>> respData = new RespData();
//        String url = "jdbc:sqlserver://192.168.0.100:1433;databaseName=Test;encrypt=false;trustServerCertificate=true;";
        String url = "jdbc:jtds:sqlserver://192.168.0.100:1433/Test;";
        String user = "sa";
        String password = "12345678";
        Connection conn = null;
        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
//            conn = DriverManager.getConnection(url);
            String sql = parameters[0].sql;
            PreparedStatement stm = conn.prepareStatement(sql);
            int i = 0;
            for(String param : parameters[0].params){
                stm.setString(++i, param);
            }
            ResultSet result = stm.executeQuery();
            respData.Result = true;
            respData.Message = "Succeed";

            ResultSetMetaData meta = result.getMetaData();
            int colcount = meta.getColumnCount();
            ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
            while (result.next()) {
                ArrayList<String> row = new ArrayList<String>(colcount);
                i = 0;
                while(i < colcount) {
                    row.add(result.getString(++i));
                }
                data.add(row);
            }
            result.close();
            respData.Data = data;
        } catch (Exception e) {
            respData.Result = false;
            respData.Message = e.getMessage();
        } finally {
            try {
                if(conn != null) conn.close();
            } catch (SQLException e) {
            }
        }

        return respData;
    }

    protected void onPostExecute(RespData<ArrayList<ArrayList<String>>> res) {
        try {
            mListener.OnGetRecords(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Parameter {
        /*
         * クエリ文字列
         */
        public String sql;

        /*
         * パラメータ
         */
        public List<String> params;

        public Parameter(String sql,List<String> params){
            this.sql = sql;
            this.params = params;
        }
    }
}
