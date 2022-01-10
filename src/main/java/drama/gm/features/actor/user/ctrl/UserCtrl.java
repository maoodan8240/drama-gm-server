package drama.gm.features.actor.user.ctrl;

import com.sun.net.httpserver.HttpExchange;
import ws.common.utils.mc.controler.Controler;
import drama.gm.features.actor.user.pojo.User;

import java.util.List;

/**
 * Created by lee on 17-2-23.
 */
public interface UserCtrl extends Controler<User> {
    HttpExchange getHttpExchange();

    void setHttpExchange(HttpExchange httpExchange);

    /**
     * 注册
     * @param account
     * @param password
     */
    void register(String account, String password);

    /**
     * 登陆
     * @param account
     * @param password
     */
    void login(String account, String password);

    /**
     * 验证
     * @param sessionId
     */
    User auth(String sessionId);

    void addUser(String account, String password, List<Integer> permission);

    void modifyUser(String id, String account, String password, List<Integer> permission);

    void deleteUser(String id);

    void getAllUser();

    void requestPermission();
}
