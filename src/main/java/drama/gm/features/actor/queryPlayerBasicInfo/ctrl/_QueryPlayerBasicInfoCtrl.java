package drama.gm.features.actor.queryPlayerBasicInfo.ctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.net.httpserver.HttpExchange;
import drama.gm.system.httpServer.msg.HttpResponseMsg;
import drama.gm.system.httpServer.utils.ResponseUtils;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import drama.gm.features.actor.utils.QueryCenterPlayer;
import dm.relationship.base.PojoUtils;
import dm.relationship.topLevelPojos.centerPlayer.CenterPlayer;
import dm.relationship.topLevelPojos.player.Player;
import dm.relationship.topLevelPojos.resourcePoint.ResourcePoint;

public class _QueryPlayerBasicInfoCtrl implements QueryPlayerBasicInfoCtrl {


    @Override
    public void query(int simpleId, HttpExchange exchange) throws Exception {
        CenterPlayer centerPlayer = QueryCenterPlayer.query(simpleId);
        int outRealmId = centerPlayer.getOuterRealmId();
        String gameId = centerPlayer.getGameId();

        String playerJsonStr = _query_TopLevelJsonStr(outRealmId, gameId, Player.class);
        String resourcePointJsonStr = _query_TopLevelJsonStr(outRealmId, gameId, ResourcePoint.class);
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse("xxxxx", playerJsonStr + "&&**&&" + resourcePointJsonStr);
        ResponseUtils.send(responseMsg, exchange);
    }

    private <T extends TopLevelPojo> String _query_TopLevelJsonStr(int outRealmId, String gameId, Class<T> clzz) throws Exception {
        T t = PojoUtils.getGamePojoFromHashesMongodb(clzz, gameId, outRealmId);
        return JSON.toJSONString(t, SerializerFeature.WriteNonStringKeyAsString);
    }
}
