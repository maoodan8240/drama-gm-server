package drama.gm.features.actor.table;

import dm.relationship.base.actor.DmActor;
import drama.gm.features.actor.table.msg.QueryTableDataListMsg;
import drama.gm.features.actor.table.msg.QueryTableDataMsg;
import drama.gm.features.actor.table.msg.UploadTableDataMsg.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import drama.gm.features.actor.table.ctrl.TableDataCtrl;
import drama.gm.features.actor.table.ctrl._TableDataCtrl;
import drama.gm.features.actor.table.msg.UploadTableDataMsg;

/**
 * Created by lee on 2021/9/15
 */
public class TableDataActor extends DmActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableDataActor.class);

    private TableDataCtrl tableDataCtrl;

    public TableDataActor() {
        _init();
    }

    private void _init() {
        tableDataCtrl = new _TableDataCtrl();
    }

    @Override
    public void onRecv(Object msg) throws Exception {
        if (msg instanceof QueryTableDataListMsg.Request) {
            onQueryTableDateListMsg((QueryTableDataListMsg.Request) msg);
        } else if (msg instanceof QueryTableDataMsg.Request) {
            onQueryTableDataMsg((QueryTableDataMsg.Request) msg);
        } else if (msg instanceof UploadTableDataMsg.Request) {
            onUploadTableDataMsg((UploadTableDataMsg.Request) msg);
        }
    }

    private void onUploadTableDataMsg(UploadTableDataMsg.Request msg) throws Exception {
        tableDataCtrl.onUploadTableDataMsg(msg);
    }

    private void onQueryTableDataMsg(QueryTableDataMsg.Request msg) {
        tableDataCtrl.onQueryTableDataMsg(msg);
    }

    private void onQueryTableDateListMsg(QueryTableDataListMsg.Request msg) {
        tableDataCtrl.onQueryTableDateListMsg(msg);
    }


}
