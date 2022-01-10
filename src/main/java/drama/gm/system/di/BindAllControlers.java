package drama.gm.system.di;

import com.google.inject.Binder;
import drama.gm.features.actor.data.ctrl.DataCtrl;
import drama.gm.features.actor.data.ctrl._DataCtrl;
import drama.gm.features.actor.user.ctrl.UserCtrl;
import drama.gm.features.actor.user.ctrl._UserCtrl;
import drama.gm.features.actor.whiteblacklist.ctrl.WhiteBlackListCtrl;
import drama.gm.features.actor.whiteblacklist.ctrl._WhiteBlackListCtrl;

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
