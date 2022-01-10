package drama.gm.features.actor.queryPlayerBasicInfo.ctrl;

import com.sun.net.httpserver.HttpExchange;

public interface QueryPlayerBasicInfoCtrl {

    void query(int simpleId, HttpExchange exchange) throws Exception;
}
