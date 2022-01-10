package drama.gm.system.shutdownHook;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import drama.gm.system.shutdownHook.msg.ServerBeginToShutDown;

public class _ShutdownHook extends Thread implements ShutdownHook {
    private static final Logger logger = LoggerFactory.getLogger(_ShutdownHook.class);

    @Override
    public void run() {
        logger.warn("注意：服务器将要停止！开始集体保存所有在线玩家的数据！");
        ActorSelection selection_user_world_player_online_player = null;// GsActorSystem.get().actorSelection(GsActorSystemPath.SELECTION_USER_WORLD_PLAYER_ONLINE_PLAYER);
        selection_user_world_player_online_player.tell(new ServerBeginToShutDown(), ActorRef.noSender());
        try {
            int time = 75;
            for (int i = 0; i < time; i++) {
                logger.warn("注意：服务器正在停止！ {} ...", (time - i));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            logger.error("服务器 ShutdownHook error ... !", e);
        }
        logger.warn("注意：服务器已经停止！");
    }
}
