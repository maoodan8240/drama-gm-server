package drama.gm.features.actor.user.dao;

import drama.gm.features.actor.user.pojo.User;
import ws.common.mongoDB.interfaces.BaseDao;

/**
 * Created by lee on 17-2-24.
 */
public interface UserDao extends BaseDao<User> {
    User query(String account);
}
