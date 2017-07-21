package ws.gm.system.httpServer;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.gm.system.config.AppConfig;
import ws.gm.system.config.AppConfig.Key;
import ws.relationship.exception.Ws_HttpServerException;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by lee on 17-2-22.
 */
public class Ws_Gm_HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(Ws_Gm_HttpServer.class);

    public static void start() {
        try {
            logger.info("HttpServer start ...");
            int port = AppConfig.getInt(AppConfig.Key.Gm_Web_Server_Port);
            int ConnectionsCount = AppConfig.getInt(Key.Gm_Web_Server_Connections_Count);
            //TODO 端口和同时接受请求的数量需要在配置文件中配置
            HttpServer server = HttpServer.create(new InetSocketAddress(port), ConnectionsCount);
            server.createContext("/", new Ws_Gm_HttpServerHandler());
            server.setExecutor(Executors.newCachedThreadPool());
            server.start();
            logger.info("HttpServer: start send! listening port ={}", port);
        } catch (Exception e) {
            throw new Ws_HttpServerException("HttpServer启动异常！", e);
        }
    }
}
