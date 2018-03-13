package nju.wjw.util;

/**
 * Created by Jerry Wang on 12/03/2018.
 */
public class ResultMsg {

    private int status;

    private Object resultMsg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(Object resultMsg) {
        this.resultMsg = resultMsg;
    }

    public ResultMsg(int status, Object resultMsg) {
        this.status = status;
        this.resultMsg = resultMsg;
    }

}
