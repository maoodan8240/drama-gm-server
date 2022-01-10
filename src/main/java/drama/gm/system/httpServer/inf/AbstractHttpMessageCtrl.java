package drama.gm.system.httpServer.inf;

import com.sun.net.httpserver.HttpExchange;
import ws.common.utils.mc.controler.AbstractControler;


/**
 * Created by lee on 17-2-24.
 */
public abstract class AbstractHttpMessageCtrl<T> extends AbstractControler<T> {
    protected HttpExchange httpExchange;


}
