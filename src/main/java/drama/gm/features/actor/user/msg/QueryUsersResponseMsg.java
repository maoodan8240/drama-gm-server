package drama.gm.features.actor.user.msg;

import drama.gm.features.actor.user.pojo.User;
import ws.common.utils.message.interfaces.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * author: lilin
 * 2017/4/19
 */
public class QueryUsersResponseMsg implements Message {
    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
