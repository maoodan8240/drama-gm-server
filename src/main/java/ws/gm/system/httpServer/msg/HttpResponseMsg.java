package ws.gm.system.httpServer.msg;

/**
 * Created by lee on 17-2-24.
 */
public class HttpResponseMsg {
    /**
     * -1异常，当-1时msgType为ResultCode
     */
    private int errorCode;

    private String msgType;

    private String data;

    public HttpResponseMsg() {
    }

    public HttpResponseMsg(String msgType, String data) {
        this.msgType = msgType;
        this.data = data;
    }

    public HttpResponseMsg(int errorCode, String msgType, String data) {
        this.errorCode = errorCode;
        this.msgType = msgType;
        this.data = data;
    }


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static HttpResponseMsg createResponse(String className, String data) {
        HttpResponseMsg msg = new HttpResponseMsg();
        msg.setErrorCode(0);
        msg.setMsgType(className);
        msg.setData(data);
        return msg;
    }


    public static HttpResponseMsg createResponse(String className) {
        HttpResponseMsg msg = new HttpResponseMsg();
        msg.setErrorCode(0);
        msg.setMsgType(className);
        return msg;
    }

    public static HttpResponseMsg createErrorResponse(String errorStringMsg) {
        HttpResponseMsg msg = new HttpResponseMsg();
        msg.setErrorCode(-2);
        msg.setData(errorStringMsg);
        return msg;
    }
}
