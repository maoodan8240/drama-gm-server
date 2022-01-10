package drama.gm.system.httpServer;

import com.alibaba.fastjson.JSON;

/**
 * Created by lee on 17-2-23.
 */
public class RequestJson {
    public String msgType;
    public String data;
    public String sessionId;

    public RequestJson() {
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

    public static void main(String[] args) {
        RequestJson res = new RequestJson();
        res.setData("{'account':'lee','pwd':'admin'}");
        System.out.println(JSON.toJSONString(res));
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
