package ws.gm.features.actor.user.dao;

import ws.common.mongoDB.interfaces.BaseDao;
import ws.gm.features.actor.user.pojo.User;

/**
 * Created by lee on 17-2-24.
 */
public interface UserDao extends BaseDao<User> {
    User query(String account);
}
