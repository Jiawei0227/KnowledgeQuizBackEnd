package nju.wjw.vo;

/**
 * Created by Jerry Wang on 16/03/2018.
 */
public class LoginStatusVO {

    private String openid;

    private String unionid;

    private String session_key;

    public String getOpenid() {
        return openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }
}
