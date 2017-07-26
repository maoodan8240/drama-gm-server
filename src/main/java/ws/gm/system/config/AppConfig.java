package ws.gm.system.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import ws.common.utils.general.ParseRoute;
import ws.relationship.exception.AppConfigInitException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppConfig {
    public static class Key {
        public static final String Gm_Web_Server_Port = "gm-web-server.port";
        public static final String Gm_Web_Server_Connections_Count = "gm-web-server.connections-count";

        public static final String WS_Common_Config_use_net_ip_first = "WS-Common-Config.use-net-ip-first";

        public static final String WS_Common_Config_jmx_conf_jmx_server_host = "WS-Common-Config.jmx-conf.jmx-server-host";
        public static final String WS_Common_Config_jmx_conf_jmx_server_port = "WS-Common-Config.jmx-conf.jmx-server-port";
        public static final String WS_Common_Config_jmx_conf_jmx_server_enabled = "WS-Common-Config.jmx-conf.jmx-server-enabled";

        public static final String WS_Common_Config_redis_pwsd = "WS-Common-Config.redis.pwsd";
        public static final String WS_Common_Config_redis_maxTotal = "WS-Common-Config.redis.maxTotal";
        public static final String WS_Common_Config_redis_maxIdlel = "WS-Common-Config.redis.maxIdlel";
        public static final String WS_Common_Config_redis_maxWaitSeconds = "WS-Common-Config.redis.maxWaitSeconds";
        public static final String WS_Common_Config_redis_sentinelIpAndPorts = "WS-Common-Config.redis.sentinelIpAndPorts";
        public static final String WS_Common_Config_redis_masterNames = "WS-Common-Config.redis.masterNames";

        public static final String WS_Common_Config_mongodb_minConnectionsPerHost = "WS-Common-Config.mongodb.minConnectionsPerHost";
        public static final String WS_Common_Config_mongodb_connectionsPerHost = "WS-Common-Config.mongodb.connectionsPerHost";
        public static final String WS_Common_Config_mongodb_dbName = "WS-Common-Config.mongodb.dbName";
        public static final String WS_Common_Config_mongodb_host = "WS-Common-Config.mongodb.host";
        public static final String WS_Common_Config_mongodb_password = "WS-Common-Config.mongodb.password";
        public static final String WS_Common_Config_mongodb_port = "WS-Common-Config.mongodb.port";
        public static final String WS_Common_Config_mongodb_userName = "WS-Common-Config.mongodb.userName";

        public static final String WS_Common_Config_mongodb_1_minConnectionsPerHost = "WS-Co1mmon-Config.mongodb-1.minConnectionsPerHost";
        public static final String WS_Common_Config_mongodb_1_connectionsPerHost = "WS-Co1mmon-Config.mongodb-1.connectionsPerHost";
        public static final String WS_Common_Config_mongodb_1_dbName = "WS-Common-Config.mongodb-1.dbName";
        public static final String WS_Common_Config_mongodb_1_host = "WS-Common-Config.mongodb-1.host";
        public static final String WS_Common_Config_mongodb_1_password = "WS-Common-Config.mongodb-1.password";
        public static final String WS_Common_Config_mongodb_1_port = "WS-Common-Config.mongodb-1.port";
        public static final String WS_Common_Config_mongodb_1_userName = "WS-Common-Config.mongodb-1.userName";

    }

    private static Config config;

    public static void init() {
        try {
            config = ConfigFactory.load();
        } catch (Exception e) {
            throw new AppConfigInitException("AppConfig 初始化异常！", e);
        }
    }

    public static void merge(Config merge) {
        config = merge.withFallback(config);
    }

    public static void merge(Map<String, Object> map) {
        merge(ConfigFactory.parseMap(map));
    }

    public static void merge(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        merge(map);
    }

    public static List<String> getConfigLis(String key, List<String> lisDefault) {
        try {
            return config.getStringList(key);
        } catch (Exception e) {
            return lisDefault;
        }
    }

    public static List<String> getConfigLis(String key) {
        return getConfigLis(key, new ArrayList<>());
    }

    public static String getString(String key) {
        try {
            return config.getString(key);
        } catch (Exception e) {
            return null;
        }
    }

    public static int getInt(String key, int defaultValue) {
        String string = getString(key);
        try {
            return Integer.valueOf(string);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static boolean getBoolean(String key) {
        return config.getBoolean(key);
    }

    public static Config get() {
        return config;
    }

    public static String[] getRedisSentinelIpAndPorts() {
        return getString(AppConfig.Key.WS_Common_Config_redis_sentinelIpAndPorts).replaceAll(" ", "").split(",");
    }

    public static String[] getRedisMasterNames() {
        return getString(AppConfig.Key.WS_Common_Config_redis_masterNames).replaceAll(" ", "").split(",");
    }

    public static String getIp() {
        boolean useNetIpFirst = getBoolean(Key.WS_Common_Config_use_net_ip_first);
        String localIp = ParseRoute.getInstance().getLocalIPAddress();
        String netIp = ParseRoute.getInstance().getNetIp();
        if (useNetIpFirst) {
            if (!StringUtils.isEmpty(netIp)) {
                return netIp;
            }
        }
        if (!StringUtils.isEmpty(localIp)) {
            return localIp;
        }
        return "127.0.0.1";
    }

    public static void main(String[] args) {
        init();
        List<String> sl = new ArrayList<>();
        config.entrySet().forEach(entry -> {
            if (entry.getKey().startsWith("WS-")) {
                String key = entry.getKey();
                String keyGen = "public static final String " + key.replaceAll("\\.", "_").replaceAll("-", "_") + " = \"" + key + "\";";
                sl.add(keyGen);
            }
        });
        sl.sort(String::compareTo);
        sl.forEach(System.out::println);

        System.out.println(Arrays.toString(getRedisSentinelIpAndPorts()));
        System.out.println(Arrays.toString(getRedisMasterNames()));
    }
}
