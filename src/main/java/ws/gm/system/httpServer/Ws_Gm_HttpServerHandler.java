package ws.gm.system.httpServer;

import akka.actor.ActorRef;
import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.utils.message.interfaces.Message;
import ws.gm.system.actor.WsActorSystem;
import ws.gm.system.actor.WsGmActorSystemPath;
import ws.gm.system.httpServer.msg.HttpResponseMsg;
import ws.gm.system.httpServer.utils.InputStreamUtils;
import ws.gm.system.httpServer.utils.ParseJsonToMessageUtils;
import ws.gm.system.httpServer.utils.ResponseUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by lee on 17-2-22.
 */
public class Ws_Gm_HttpServerHandler implements HttpHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ws_Gm_HttpServerHandler.class);

    public Ws_Gm_HttpServerHandler() {
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LOGGER.debug("111--->{}", exchange.getHttpContext().getAttributes());
        LOGGER.debug("222--->{}", exchange.getRequestMethod());
        LOGGER.debug("333--->{}", exchange.getResponseHeaders().entrySet());
        LOGGER.debug("444--->{} ", exchange.getRequestURI(), exchange.getRequestHeaders().entrySet());
        String method = exchange.getRequestMethod();
        if (method.equalsIgnoreCase("POST")) {
            URI uri = exchange.getRequestURI();
            InputStream i = exchange.getRequestBody();
            String pro = exchange.getProtocol();
            System.out.println("connect:");
            Headers headers = exchange.getResponseHeaders();
            headers.set("Content-Type", "text/html");
            Headers request = exchange.getRequestHeaders();
            String host = request.getFirst("Host");
            System.out.println(pro + host + uri);
            try {
                String requestString = InputStreamUtils.readInfoStream(i);
                System.out.println("RequestBody : " + requestString);
                RequestJson res = JSON.parseObject(requestString, RequestJson.class);
                System.out.println("data:" + res.getData());
                System.out.println("msgType:" + res.getMsgType());
                Message msg = ParseJsonToMessageUtils.parse(res, exchange);
                WsActorSystem.get().actorSelection(WsGmActorSystemPath.WS_Common_Selection_GmRoot).tell(msg, ActorRef.noSender());
            } catch (Exception e) {
                HttpResponseMsg msg = HttpResponseMsg.createErrorResponse("json error");
                ResponseUtils.send(msg, exchange);
                e.printStackTrace();
            }
        }
    }
}
