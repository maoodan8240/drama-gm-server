package ws.gm.features.actor.example;

import ws.common.utils.di.GlobalInjector;
import ws.gm.features.actor.example.ctrl.RemainCtrl;
import ws.gm.system.httpServer.msg.RemainRequestMsg;
import ws.relationship.base.actor.WsActor;

/**
 * Created by lee on 17-2-23.
 */
public class RemainActor extends WsActor {
    private RemainCtrl exampleCtrl;

    public RemainActor() {
        this.enableWsActorLogger = false;
        RemainCtrl exampleCtrl = GlobalInjector.getInstance(RemainCtrl.class);
        this.exampleCtrl = exampleCtrl;
    }

    @Override
    public void onRecv(Object msg) throws Exception {

    }

    private void on_RemainRequestMsg(RemainRequestMsg msg) {
    }
}
