package drama.gm.system.actor;

import akka.actor.ActorSystem;
import akka.actor.Props;
import dm.relationship.exception.DmActorSystemInitException;
import drama.gm.system.config.AppConfig;
import dm.relationship.base.cluster.ActorSystemPath;

public class DmActorSystem {
    private static final ActorSystem actorSystem = ActorSystem.create(ActorSystemPath.DM_Common_System, AppConfig.get());

    public static void init() {
        try {
            actorSystem.actorOf(Props.create(Dm_Gm_RootActor.class), DmGmActorSystemPath.DM_Common_GmRoot);
        } catch (Exception e) {
            throw new DmActorSystemInitException("ActorSystem init() 异常！", e);
        }
    }

    public static ActorSystem get() {
        return actorSystem;
    }
}
