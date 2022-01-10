package drama.gm.features.actor.table.msg;

import com.alibaba.fastjson.JSONArray;
import drama.gm.system.httpServer.msg.HttpRequestMsg;

import java.io.File;
import java.util.List;

/**
 * Created by lee on 2021/9/17
 */
public class UploadTableDataMsg {
    public static class Request extends HttpRequestMsg {
        private String filesArrStr;

        public String getFilesArrStr() {
            return filesArrStr;
        }

        public void setFilesArrStr(String filesArrStr) {
            this.filesArrStr = filesArrStr;
        }
    }
}
