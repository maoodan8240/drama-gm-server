package drama.gm.features.actor.user.msg;

import drama.gm.features.actor.user.pojo.User;
import ws.common.utils.message.interfaces.Message;

/**
 * Created by lee on 17-2-26.
 */
public class UserAuthResponseMsg implements Message {
    private UserAuthRequestMsg request;
    private User user;

    public UserAuthResponseMsg(UserAuthRequestMsg request, User user) {
        this.request = request;
        this.user = user;
    }

    public UserAuthRequestMsg getRequest() {
        return request;
    }

    public User getUser() {
        return user;
    }
}
