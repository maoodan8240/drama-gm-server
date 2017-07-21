package ws.gm.system.schedule;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import ws.gm.system.actor.WsActorSystem;
import ws.gm.system.schedule.msg.In_RefreshUpdatedPlayerDataToMongodbRequest;
import ws.gm.system.schedule.msg.In_RemoveOverTimePlayerDataFromRedisAndRefreshToMongodbRequest;
import ws.relationship.base.cluster.ActorSystemPath;
import ws.relationship.base.msg.In_DisplayActorSelfPath;

public class Cron4jUtils {

    public static void pojoRemover(String[] args) {
        ActorSelection selection = WsActorSystem.get().actorSelection(ActorSystemPath.WS_MongodbRedisServer_Selection_PojoRemover);
        selection.tell(new In_RemoveOverTimePlayerDataFromRedisAndRefreshToMongodbRequest(), ActorRef.noSender());
    }

    public static void pojoSaver(String[] args) {
        ActorSelection selection = WsActorSystem.get().actorSelection(ActorSystemPath.WS_MongodbRedisServer_Selection_PojoSaver);
        selection.tell(new In_RefreshUpdatedPlayerDataToMongodbRequest(), ActorRef.noSender());
    }

    public static void displayActorSelfPath(String[] args) {
        WsActorSystem.get().actorSelection(ActorSystemPath.WS_Common_Selection_WSRoot + "/*").tell(new In_DisplayActorSelfPath(), ActorRef.noSender());
    }

}
