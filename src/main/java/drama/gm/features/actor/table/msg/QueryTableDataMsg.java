package drama.gm.features.actor.table.msg;

import drama.gm.system.httpServer.msg.HttpRequestMsg;

/**
 * Created by lee on 2021/9/16
 */
public class QueryTableDataMsg {
    public static class Request extends HttpRequestMsg {
        private String tableDataName;

        public String getTableDataName() {
            return tableDataName;
        }

        public void setTableDataName(String tableDataName) {
            this.tableDataName = tableDataName;
        }
    }
}
