package ws.gm.features.actor.user.msg;

import ws.common.utils.message.interfaces.Message;

/**
 * Created by lee on 17-2-26.
 */
public class UserAuthRequestMsg implements Message {
    private String sessionId;

    public UserAuthRequestMsg(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
