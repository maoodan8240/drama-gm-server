package drama.gm.system.di;

import com.google.inject.Binder;
import com.google.inject.Scopes;
import drama.gm.system.di.dbClients._GameCommonDBClient;
import ws.common.mongoDB.implement._MongoDBClient;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.redis.RedisOpration;
import ws.common.redis._RedisOpration;
import drama.gm.features.session.SessionManager;
import drama.gm.features.session._SessionManager;
import drama.gm.system.di.dbClients.GameCommonDBClient;

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
