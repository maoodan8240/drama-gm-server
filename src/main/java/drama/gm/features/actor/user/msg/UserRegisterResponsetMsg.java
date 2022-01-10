package drama.gm.features.actor.user.msg;

import com.sun.net.httpserver.HttpExchange;
import drama.gm.system.httpServer.msg.HttpRequestMsg;

/**
 * Created by lee on 17-2-24.
 */
public class UserRegisterResponsetMsg extends HttpRequestMsg {

    private String sessionId;


    public UserRegisterResponsetMsg() {
    }

    public UserRegisterResponsetMsg(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserRegisterResponsetMsg(HttpExchange exchange, String sessionId) {
        super(exchange);
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
