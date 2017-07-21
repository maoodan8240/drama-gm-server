package ws.gm.system.httpServer.msg;

import ws.common.utils.message.interfaces.Message;

import com.sun.net.httpserver.HttpExchange;

/**
 * Created by lee on 17-2-23.
 */
public class HttpRequestMsg implements Message {
    private String sessionId;
    private HttpExchange exchange;

    public HttpRequestMsg() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setExchange(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public HttpExchange getExchange() {
        return exchange;
    }

    public HttpRequestMsg(HttpExchange exchange) {
        this.exchange = exchange;
    }
}
