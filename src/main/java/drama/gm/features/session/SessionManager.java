package drama.gm.features.session;

import drama.gm.features.actor.user.pojo.User;

public interface SessionManager {
    String newSessionId();

    void put(String sessionId, User user);

    void put(String sessionId, User user, int minutes);

    void remove(String sessionId);

    int size();

    User queryBySessionId(String sessionId);
}
