package ws.gm.features.actor.queryPlayerBasicInfo.ctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.net.httpserver.HttpExchange;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.gm.features.actor.utils.QueryCenterPlayer;
import ws.gm.system.httpServer.msg.HttpResponseMsg;
import ws.gm.system.httpServer.utils.ResponseUtils;
import ws.relationship.base.PojoUtils;
import ws.relationship.topLevelPojos.centerPlayer.CenterPlayer;
import ws.relationship.topLevelPojos.heros.Heros;
import ws.relationship.topLevelPojos.itemBag.ItemBag;
import ws.relationship.topLevelPojos.player.Player;
import ws.relationship.topLevelPojos.resourcePoint.ResourcePoint;

public class _QueryPlayerBasicInfoCtrl implements QueryPlayerBasicInfoCtrl {


    @Override
    public void query(int simpleId, HttpExchange exchange) throws Exception {
        CenterPlayer centerPlayer = QueryCenterPlayer.query(simpleId);
        int outRealmId = centerPlayer.getOuterRealmId();
        String gameId = centerPlayer.getGameId();

        String playerJsonStr = _query_TopLevelJsonStr(outRealmId, gameId, Player.class);
        String itemBagJsonStr = _query_TopLevelJsonStr(outRealmId, gameId, ItemBag.class);
        String resourcePointJsonStr = _query_TopLevelJsonStr(outRealmId, gameId, ResourcePoint.class);
        String herosJsonStr = _query_TopLevelJsonStr(outRealmId, gameId, Heros.class);
        HttpResponseMsg responseMsg = HttpResponseMsg.createResponse("xxxxx", playerJsonStr + "&&**&&" + itemBagJsonStr + "&&**&&" + resourcePointJsonStr + "&&**&&" + herosJsonStr);
        ResponseUtils.send(responseMsg, exchange);
    }

    private <T extends TopLevelPojo> String _query_TopLevelJsonStr(int outRealmId, String gameId, Class<T> clzz) throws Exception {
        T t = PojoUtils.getGamePojoFromHashesMongodb(clzz, gameId, outRealmId);
        return JSON.toJSONString(t, SerializerFeature.WriteNonStringKeyAsString);
    }
}
