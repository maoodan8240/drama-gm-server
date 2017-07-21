package ws.gm.system.httpServer.msg;

/**
 * Created by lee on 17-2-23.
 */
public class UserLoginResponseMsg {

    private String sessionId;


    public UserLoginResponseMsg() {

    }


    public UserLoginResponseMsg(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

 
}
