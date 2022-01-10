package drama.gm.system.httpServer;

import akka.actor.ActorRef;
import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import drama.gm.system.httpServer.utils.InputStreamUtils;
import drama.gm.system.httpServer.utils.ParseJsonToMessageUtils;
import drama.gm.system.httpServer.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.utils.message.interfaces.Message;
import drama.gm.system.actor.DmActorSystem;
import drama.gm.system.actor.DmGmActorSystemPath;
import drama.gm.system.httpServer.msg.HttpResponseMsg;

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
        LOGGER.debug("444--->{}", exchange.getRequestHeaders().entrySet());
        LOGGER.debug("555--->{}", exchange.getRequestURI());
        String method = exchange.getRequestMethod();
        if (method.equalsIgnoreCase("POST")) {
            URI uri = exchange.getRequestURI();
            InputStream i = exchange.getRequestBody();
            String pro = exchange.getProtocol();
            System.out.println("connect:");
            Headers request = exchange.getRequestHeaders();
            Headers headers = exchange.getResponseHeaders();
            headers.set("Content-Type", "text/html");
            String host = request.getFirst("Host");
            System.out.println(pro + host + uri);
            try {
                String requestString = "";
                if (uri.toString().equals("/uploadTable")) {
                    String msg = "<a href='http://10.0.2.116/drama-gm-client/table_data_form_basic.html'>upload success!</a>";
                    requestString = InputStreamUtils.readInfoStream(i, uri);
                } else {
                    requestString = InputStreamUtils.readInfoStream(i);
                }
                System.out.println("RequestBody : " + requestString);
                RequestJson res = JSON.parseObject(requestString, RequestJson.class);
                System.out.println("data:" + res.getData());
                System.out.println("msgType:" + res.getMsgType());
                Message msg = ParseJsonToMessageUtils.parse(res, exchange);
                DmActorSystem.get().actorSelection(DmGmActorSystemPath.DM_Common_Selection_GmRoot).tell(msg, ActorRef.noSender());
            } catch (Exception e) {
                HttpResponseMsg msg = HttpResponseMsg.createErrorResponse("json error");
                ResponseUtils.send(msg, exchange);
                e.printStackTrace();
            }
        }
    }
}
