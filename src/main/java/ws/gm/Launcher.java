package ws.gm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.config.MongoConfig;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.redis.RedisOpration;
import ws.common.utils.di.GlobalInjector;
import ws.gm.system.actor.WsActorSystem;
import ws.gm.system.config.AppConfig;
import ws.gm.system.config.AppConfig.Key;
import ws.gm.system.di.GlobalInjectorUtils;
import ws.gm.system.di.dbClients.GameCommonDBClient;
import ws.gm.system.httpServer.Ws_Gm_HttpServer;
import ws.relationship.base.LauncherUtils;
import ws.relationship.exception.LauncherInitException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Launcher {
    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        _init();
        _startActorSystem();
    }


    private static void _init() {
        try {
            AppConfig.init();
            GlobalInjectorUtils.init();
            _redisInit();
            _mongodbInit();
            _gameCommonMongodbInit();

            LauncherUtils._redisInit_Test();
            LauncherUtils._mongodbInit_Test();
            Ws_Gm_HttpServer.start();
        } catch (Exception e) {
            throw new LauncherInitException("初始化异常！", e);
        }
    }

    private static void _redisInit() {
        RedisOpration redisOpration = GlobalInjector.getInstance(RedisOpration.class);
        List<String> masterNames = new ArrayList<>(Arrays.asList(AppConfig.getRedisMasterNames()));
        Set<String> sentinelIpAndPorts = new LinkedHashSet<>(Arrays.asList(AppConfig.getRedisSentinelIpAndPorts()));
        int maxTotal = AppConfig.getInt(AppConfig.Key.WS_Common_Config_redis_maxTotal);
        int maxIdlel = AppConfig.getInt(AppConfig.Key.WS_Common_Config_redis_maxIdlel);
        int maxWaitSeconds = AppConfig.getInt(AppConfig.Key.WS_Common_Config_redis_maxWaitSeconds);
        String pwsd = AppConfig.getString(AppConfig.Key.WS_Common_Config_redis_pwsd);
        redisOpration.init(maxTotal, maxIdlel, maxWaitSeconds, pwsd, masterNames, sentinelIpAndPorts);
    }

    public static void _mongodbInit() {
        String host = AppConfig.getString(AppConfig.Key.WS_Common_Config_mongodb_host);
        int port = AppConfig.getInt(AppConfig.Key.WS_Common_Config_mongodb_port);
        String userName = AppConfig.getString(AppConfig.Key.WS_Common_Config_mongodb_userName);
        String password = AppConfig.getString(AppConfig.Key.WS_Common_Config_mongodb_password);
        String dbName = AppConfig.getString(AppConfig.Key.WS_Common_Config_mongodb_dbName);
        int connectionsPerHost = AppConfig.getInt(AppConfig.Key.WS_Common_Config_mongodb_connectionsPerHost);
        int minConnectionsPerHost = AppConfig.getInt(AppConfig.Key.WS_Common_Config_mongodb_minConnectionsPerHost);
        MongoConfig config = new MongoConfig(host, port, userName, password, dbName, minConnectionsPerHost, connectionsPerHost);
        GlobalInjector.getInstance(MongoDBClient.class).init(config);
    }

    public static void _gameCommonMongodbInit() {
        String host = AppConfig.getString(AppConfig.Key.WS_Common_Config_mongodb_1_host);
        int port = AppConfig.getInt(AppConfig.Key.WS_Common_Config_mongodb_1_port);
        String userName = AppConfig.getString(AppConfig.Key.WS_Common_Config_mongodb_1_userName);
        String password = AppConfig.getString(AppConfig.Key.WS_Common_Config_mongodb_1_password);
        String dbName = AppConfig.getString(AppConfig.Key.WS_Common_Config_mongodb_1_dbName);
        int connectionsPerHost = AppConfig.getInt(Key.WS_Common_Config_mongodb_1_connectionsPerHost);
        int minConnectionsPerHost = AppConfig.getInt(Key.WS_Common_Config_mongodb_1_minConnectionsPerHost);
        MongoConfig config = new MongoConfig(host, port, userName, password, dbName, minConnectionsPerHost, connectionsPerHost);
        GlobalInjector.getInstance(GameCommonDBClient.class).init(config);
    }

    private static void _startActorSystem() {
        WsActorSystem.init();
    }


}
