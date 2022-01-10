package drama.gm.system.schedule;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import drama.gm.system.actor.DmActorSystem;
import drama.gm.system.schedule.msg.In_RefreshUpdatedPlayerDataToMongodbRequest;
import drama.gm.system.schedule.msg.In_RemoveOverTimePlayerDataFromRedisAndRefreshToMongodbRequest;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.In_DisplayActorSelfPath;

public class Cron4jUtils {

    public static void pojoRemover(String[] args) {
        ActorSelection selection = DmActorSystem.get().actorSelection(ActorSystemPath.DM_MongodbRedisServer_Selection_PojoRemover);
        selection.tell(new In_RemoveOverTimePlayerDataFromRedisAndRefreshToMongodbRequest(), ActorRef.noSender());
    }

    public static void pojoSaver(String[] args) {
        ActorSelection selection = DmActorSystem.get().actorSelection(ActorSystemPath.DM_MongodbRedisServer_Selection_PojoSaver);
        selection.tell(new In_RefreshUpdatedPlayerDataToMongodbRequest(), ActorRef.noSender());
    }

    public static void displayActorSelfPath(String[] args) {
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_Common_Selection_DMRoot + "/*").tell(new In_DisplayActorSelfPath(), ActorRef.noSender());
    }

}
