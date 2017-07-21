package ws.gm.system.httpServer.utils;

import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.utils.WsJsonUtils;
import ws.gm.system.httpServer.msg.HttpResponseMsg;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by lee on 17-2-24.
 */
public class ResponseUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseUtils.class);

    public static void send(HttpResponseMsg httpResponseMsg, HttpExchange httpExchange) {
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        String jsonBody = WsJsonUtils.javaObjectToJSONStr(httpResponseMsg);
        LOGGER.debug("ResposeBody : " + jsonBody);
        OutputStream out = null;
        try {
            httpExchange.sendResponseHeaders(200, jsonBody.toString().getBytes().length);
            out = httpExchange.getResponseBody();
            out.write(jsonBody.getBytes());
        } catch (Exception e) {
            String msg = "network error";
            throw new WsGmHttpServerException(msg);
        } finally {
            if (out != null) {
                try {
                    out.close();
                    httpExchange.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
