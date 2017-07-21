package ws.gm.system.di;

import com.google.inject.Binder;
import com.google.inject.Scopes;
import ws.common.mongoDB.implement._MongoDBClient;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.redis.RedisOpration;
import ws.common.redis._RedisOpration;
import ws.gm.features.session.SessionManager;
import ws.gm.features.session._SessionManager;
import ws.gm.system.di.dbClients.GameCommonDBClient;
import ws.gm.system.di.dbClients._GameCommonDBClient;

/**
 * 绑定所有Bean
 */
public class BindAllBeans {
    public static void bind(Binder binder) throws RuntimeException {
        binder.bind(RedisOpration.class).to(_RedisOpration.class).in(Scopes.SINGLETON);
        binder.bind(MongoDBClient.class).to(_MongoDBClient.class).in(Scopes.SINGLETON);
        binder.bind(GameCommonDBClient.class).to(_GameCommonDBClient.class).in(Scopes.SINGLETON);
        binder.bind(SessionManager.class).to(_SessionManager.class).in(Scopes.SINGLETON);
    }
}
