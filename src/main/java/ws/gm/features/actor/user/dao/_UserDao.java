package ws.gm.features.actor.user.dao;

import org.bson.Document;
import ws.common.mongoDB.implement.AbstractBaseDao;
import ws.gm.features.actor.user.pojo.User;

/**
 * Created by lee on 17-2-24.
 */
public class _UserDao extends AbstractBaseDao<User> implements UserDao {
    public _UserDao() {
        super(User.class);
    }

    @Override
    public User query(String account) {
        return findOne(new Document("account", account));
    }
}
