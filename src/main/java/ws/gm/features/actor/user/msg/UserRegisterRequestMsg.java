package ws.gm.features.actor.user.msg;

import com.sun.net.httpserver.HttpExchange;
import ws.gm.system.httpServer.msg.HttpRequestMsg;

/**
 * Created by lee on 17-2-24.
 */
public class UserRegisterRequestMsg extends HttpRequestMsg {

    private String account;
    private String password;


    public UserRegisterRequestMsg() {
    }

    public UserRegisterRequestMsg(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public UserRegisterRequestMsg(HttpExchange exchange, String account, String password) {
        super(exchange);
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
