package drama.gm.system.httpServer.utils;

import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.lang3.StringUtils;
import ws.common.utils.classProcess.ClassFinder;
import ws.common.utils.message.interfaces.Message;
import drama.gm.features.actor.GameServerPackageHolder;
import drama.gm.system.httpServer.RequestJson;
import drama.gm.system.httpServer.msg.HttpRequestMsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lee on 17-2-23.
 */
public class ParseJsonToMessageUtils {
    private static List<Class<? extends Message>> allRequest = null;

    private static Map<String, Class<? extends Message>> allClassNameToMessage = new HashMap<>();

    public static final String SCREEN_PATH = "/home/lee/project/ws-gm-server/src/main/java/ws/gm/features/actor";

    public static Message parse(RequestJson requestJson, HttpExchange exchange) throws Exception {
        String msgType = requestJson.getMsgType();
        String data = requestJson.getData();
        String packageName = allClassNameToMessage.get(msgType).getName();
        Class<?> clz = Class.forName(packageName);
        HttpRequestMsg msg = (HttpRequestMsg) JSON.parseObject(data, clz);
        msg.setExchange(exchange);
        if (!StringUtils.isEmpty(requestJson.getSessionId())) {
            msg.setSessionId(requestJson.getSessionId());
        }
        return msg;
    }


    public static void init() {
        allClassNameToMessage.clear();
        for (Class<? extends Message> clazz : allRequest) {
            String packagename = clazz.getPackage().getName();
            String className = clazz.getName().replaceAll(packagename + ".", "");
            allClassNameToMessage.put(className, clazz);
            System.out.println("packagename : " + clazz.getName());
            System.out.println("className : " + className);
        }

    }

    static {
        allRequest = ClassFinder.getAllAssignedClass(Message.class, GameServerPackageHolder.class);
        init();
    }

    public static void main(String[] args) {
        init();
    }

}
