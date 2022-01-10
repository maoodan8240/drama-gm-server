package drama.gm.features.actor.table.ctrl;

import drama.gm.features.actor.table.msg.QueryTableDataListMsg;
import drama.gm.features.actor.table.msg.QueryTableDataMsg;
import drama.gm.features.actor.table.msg.UploadTableDataMsg;
import drama.gm.features.actor.table.msg.UploadTableDataMsg.Request;

/**
 * Created by lee on 2021/9/15
 */
public interface TableDataCtrl {
    void onQueryTableDateListMsg(QueryTableDataListMsg.Request msg);

    void onQueryTableDataMsg(QueryTableDataMsg.Request msg);

    void onUploadTableDataMsg(UploadTableDataMsg.Request msg) throws Exception;
}
