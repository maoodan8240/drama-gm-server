package drama.gm.features.actor.gmCommond.ctrl;

import com.sun.net.httpserver.HttpExchange;

public interface SendGmCommandMsgCtrl {

    void send(String simpleIdLisStr, String command, HttpExchange exchange);
}
