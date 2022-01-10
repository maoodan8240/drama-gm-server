package drama.gm.features.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.utils.cooldown.implement.AutoClearCdList;
import drama.gm.features.actor.user.pojo.User;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class _SessionManager implements SessionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(_SessionManager.class);
    private static final int DEFAULT_SESSION_MAX_TIME_TO_LIVE_IN_MINUTES = 1;
    private AutoClearCdList cdList = new AutoClearCdList();
    private Map<String, User> map = new HashMap<String, User>();
    private final int sessionMaxTimeToLiveInMinutes;

    public _SessionManager() {
        this(DEFAULT_SESSION_MAX_TIME_TO_LIVE_IN_MINUTES);
    }

    public _SessionManager(int sessionMaxTimeToLiveInMinutes) {
        this.sessionMaxTimeToLiveInMinutes = sessionMaxTimeToLiveInMinutes;
        _init();
    }

    private void _init() {
        cdList.setCallbackOnExpire(sessionId -> {
            // LOGGER.debug("SessionId Expire, {}", sessionId);
            // remove(sessionId);
        });
    }

    @Override
    public String newSessionId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public synchronized void put(String sessionId, User user) {
        map.put(sessionId, user);
        cdList.add(sessionId, sessionMaxTimeToLiveInMinutes, Calendar.HOUR);
    }

    @Override
    public synchronized void put(String sessionId, User user, int minutes) {
        map.put(sessionId, user);
        cdList.add(sessionId, minutes, Calendar.MINUTE);
    }

    @Override
    public synchronized void remove(String sessionId) {
        map.remove(sessionId);
        cdList.clear(sessionId);
    }

    @Override
    public synchronized int size() {
        return map.size();
    }

    @Override
    public synchronized User queryBySessionId(String sessionId) {
        User user = map.get(sessionId);
        LOGGER.info(map.toString());
        return user;
    }

}
