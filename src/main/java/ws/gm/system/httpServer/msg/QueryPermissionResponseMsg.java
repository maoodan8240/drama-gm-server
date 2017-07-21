package ws.gm.system.httpServer.msg;

import ws.common.utils.message.interfaces.Message;
import ws.gm.features.actor.user.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * author: lilin
 * 2017/4/19
 */
public class QueryPermissionResponseMsg implements Message {
    private List<String> permissions = new ArrayList<>();

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
