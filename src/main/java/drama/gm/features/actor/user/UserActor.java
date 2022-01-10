package drama.gm.features.actor.user;

import akka.actor.ActorRef;
import com.sun.net.httpserver.HttpExchange;
import dm.relationship.base.actor.DmActor;
import drama.gm.system.httpServer.msg.HttpRequestMsg;
import drama.gm.system.httpServer.msg.HttpResponseMsg;
import drama.gm.system.httpServer.utils.ResponseUtils;
import ws.common.utils.di.GlobalInjector;
import ws.common.utils.message.interfaces.Message;
import drama.gm.features.actor.user.ctrl.UserCtrl;
import drama.gm.features.actor.user.pojo.User;
import drama.gm.features.actor.user.msg.UserAuthRequestMsg;
import drama.gm.features.actor.user.msg.UserAuthResponseMsg;
import drama.gm.features.actor.user.msg.UserLoginRequestMsg;
import drama.gm.features.actor.user.msg.UserRegisterRequestMsg;
import drama.gm.features.actor.user.msg.UserRequestMsg;


/**
 * Created by lee on 17-2-23.
 */
public class UserActor extends DmActor {

    private UserCtrl userCtrl;

    public UserActor() {
        this.userCtrl = GlobalInjector.getInstance(UserCtrl.class);
    }

    @Override
    public void onRecv(Object msg) throws Exception {
        if (msg instanceof HttpRequestMsg) {
            HttpExchange exchange = ((HttpRequestMsg) msg).getExchange();
            userCtrl.setHttpExchange(exchange);
            try {
                if (msg instanceof UserLoginRequestMsg) {
                    on_UserLoginMessage((UserLoginRequestMsg) msg);
                } else if (msg instanceof UserRegisterRequestMsg) {
                    on_UserRegisterMessage((UserRegisterRequestMsg) msg);
                } else if (msg instanceof UserRequestMsg.AddUserMsg) {
                    addUser((UserRequestMsg.AddUserMsg) msg);
                } else if (msg instanceof UserRequestMsg.DeleteUserMsg) {
                    deleteUser((UserRequestMsg.DeleteUserMsg) msg);
                } else if (msg instanceof UserRequestMsg.ModifyUserMsg) {
                    modifyUser((UserRequestMsg.ModifyUserMsg) msg);
                } else if (msg instanceof UserRequestMsg.RequestAllUserMsg) {
                    getAllUser((UserRequestMsg.RequestAllUserMsg) msg);
                } else if (msg instanceof UserRequestMsg.RequestPermissionMsg) {
                    requestPermission((UserRequestMsg.RequestPermissionMsg) msg);
                }
            } catch (Exception e) {
                HttpResponseMsg responseMsg = HttpResponseMsg.createErrorResponse(e.getMessage());
                ResponseUtils.send(responseMsg, exchange);
                throw e;
            }
        }
        if (msg instanceof Message) {
            if (msg instanceof UserAuthRequestMsg) {
                on_UserAuthMsg((UserAuthRequestMsg) msg);
            }
        }

    }

    private void requestPermission(UserRequestMsg.RequestPermissionMsg msg) {
        userCtrl.requestPermission();
    }

    private void on_UserAuthMsg(UserAuthRequestMsg msg) {
        User user = userCtrl.auth(msg.getSessionId());
        UserAuthResponseMsg response = new UserAuthResponseMsg(msg, user);
        getSender().tell(response, ActorRef.noSender());
    }

    private void on_UserRegisterMessage(UserRegisterRequestMsg msg) {
        userCtrl.register(msg.getAccount(), msg.getPassword());
    }

    private void on_UserLoginMessage(UserLoginRequestMsg msg) throws Exception {
        userCtrl.login(msg.getAccount(), msg.getPassword());
    }

    private void addUser(UserRequestMsg.AddUserMsg msg) {
        userCtrl.addUser(msg.getAccount(), msg.getPassword(), msg.getPermission());
    }

    private void getAllUser(UserRequestMsg.RequestAllUserMsg msg) {
        userCtrl.getAllUser();
    }

    private void modifyUser(UserRequestMsg.ModifyUserMsg msg) {
        userCtrl.modifyUser(msg.getId(), msg.getAccount(), msg.getPassword(), msg.getPermission());
    }

    private void deleteUser(UserRequestMsg.DeleteUserMsg msg) {
        userCtrl.deleteUser(msg.getId());
    }
}
