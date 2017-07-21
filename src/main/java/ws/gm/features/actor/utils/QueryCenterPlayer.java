package ws.gm.features.actor.utils;

import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.utils.di.GlobalInjector;
import ws.relationship.base.MagicWords_Mongodb;
import ws.relationship.daos.DaoContainer;
import ws.relationship.daos.centerPlayer.CenterPlayerDao;
import ws.relationship.topLevelPojos.centerPlayer.CenterPlayer;

public class QueryCenterPlayer {
    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);
    private static final CenterPlayerDao CENTER_PLAYER_DAO = DaoContainer.getDao(CenterPlayer.class);

    static {
        CENTER_PLAYER_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Common);
    }

    public static CenterPlayer query(int simpleId) {
        CenterPlayer centerPlayer = CENTER_PLAYER_DAO.findCenterPlayer(simpleId);
        if (centerPlayer == null) {
            String errMsg = String.format("查询CenterPlayer失败，不存在的simpleId=%s !", simpleId);
            throw new RuntimeException(errMsg);
        }
        return centerPlayer;
    }
}
