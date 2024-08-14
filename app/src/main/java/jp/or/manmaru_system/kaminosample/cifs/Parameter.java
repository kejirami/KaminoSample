package jp.or.manmaru_system.kaminosample.cifs;

public class Parameter {

    /*
     * ホスト名
     */
    public String host;

    /*
     * ユーザー
     */
    public String user;

    /*
     * パスワード
     */
    public String password;

    /*
     * パス
     */
    public String path;

    /*
     * data
     */
    public byte[] data;

    public Parameter(String host,String user,String password,String path,byte[] data){
        this.host = host;
        this.user = user;
        this.password = password;
        this.path = path;
        this.data = data;
    }
}
