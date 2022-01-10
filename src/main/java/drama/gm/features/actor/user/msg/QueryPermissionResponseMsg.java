package drama.gm.features.actor.user.msg;

import ws.common.utils.message.interfaces.Message;

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
