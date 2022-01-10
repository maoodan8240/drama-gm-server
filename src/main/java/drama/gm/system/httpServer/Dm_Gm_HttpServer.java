package drama.gm.system.httpServer;

import com.sun.net.httpserver.HttpServer;
import dm.relationship.exception.Dm_HttpServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import drama.gm.system.config.AppConfig;
import drama.gm.system.config.AppConfig.Key;
import dm.relationship.exception.Dm_HttpServerException;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by lee on 17-2-22.
 */
public class Dm_Gm_HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(Dm_Gm_HttpServer.class);

    public static void start() {
        try {
            logger.info("HttpServer start ...");
            int port = AppConfig.getInt(AppConfig.Key.Gm_Web_Server_Port);
            int ConnectionsCount = AppConfig.getInt(Key.Gm_Web_Server_Connections_Count);
            //TODO 端口和同时接受请求的数量需要在配置文件配置
            InetSocketAddress address = new InetSocketAddress(AppConfig.getIp(), port);
            HttpServer server = HttpServer.create(address, ConnectionsCount);
            server.createContext("/", new Ws_Gm_HttpServerHandler());
            server.setExecutor(Executors.newCachedThreadPool());
            server.start();
            logger.info("HttpServer: start send! listening address ={}", address);
        } catch (Exception e) {
            throw new Dm_HttpServerException("HttpServer启动异常！", e);
        }
    }
}
