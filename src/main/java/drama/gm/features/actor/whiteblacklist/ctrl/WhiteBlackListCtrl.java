package drama.gm.features.actor.whiteblacklist.ctrl;

import com.sun.net.httpserver.HttpExchange;
import ws.common.utils.mc.controler.Controler;
import dm.relationship.topLevelPojos.centerPlayer.CenterPlayer;

/**
 * Created by lee on 17-2-23.
 */
public interface WhiteBlackListCtrl extends Controler<CenterPlayer> {
    /**
     * 添加黑名单
     *
     * @param simpleId
     */
    void addBlackList(int simpleId);

    /**
     * 添加白名单
     *
     * @param simpleId
     */
    void addWhiteList(int simpleId);

    /**
     * 移出黑名单
     *
     * @param simpleId
     */
    void removeFromBlackList(int simpleId);

    /**
     * 移出白名单
     *
     * @param simpleId
     */
    void removeFromWhiteList(int simpleId);

    /**
     * 查询所有白名单
     */
    void queryAllWhiteList();

    /**
     * 查询所有黑名单
     */
    void queryAllBlackList();

    /**
     * 踢出
     *
     * @param simpleId
     * @param time
     */
    void lockPlayer(int simpleId, int time);



    void setHttpExchange(HttpExchange exchange);

    HttpExchange getHttpExchange();

    void queryAllLockPlayer();
}
