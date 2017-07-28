package ws.gm.system.di;

import com.google.inject.Binder;
import ws.gm.features.actor.data.ctrl.DataCtrl;
import ws.gm.features.actor.data.ctrl._DataCtrl;
import ws.gm.features.actor.user.ctrl.UserCtrl;
import ws.gm.features.actor.user.ctrl._UserCtrl;
import ws.gm.features.actor.whiteblacklist.ctrl.WhiteBlackListCtrl;
import ws.gm.features.actor.whiteblacklist.ctrl._WhiteBlackListCtrl;

/**
 * 绑定所有Control
 */
public class BindAllControlers {
    public static void bind(Binder binder) {
        binder.bind(UserCtrl.class).to(_UserCtrl.class);
        binder.bind(DataCtrl.class).to(_DataCtrl.class);
        binder.bind(WhiteBlackListCtrl.class).to(_WhiteBlackListCtrl.class);
    }
}
