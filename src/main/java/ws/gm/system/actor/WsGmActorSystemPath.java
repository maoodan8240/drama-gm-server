package ws.gm.system.actor;

/**
 * Created by lee on 17-2-24.
 */
public class WsGmActorSystemPath {
    /**
     * =GM后台Actor路径及名称=======================
     */
    public static final String WS_Common_GmRoot = "GmRoot";

    public static final String WS_Common_Selection_GmRoot = "/user/GmRoot";
    public static final String Ws_Gm_User = "user"; // Gm用户管理
    public static final String Ws_Gm_Selection_User = WS_Common_Selection_GmRoot + "/user";

    public static final String Ws_Gm_QueryPlayerBasicInfo = "queryPlayerBasicInfo"; //查询玩家
    public static final String Ws_Gm_Selection_Player = WS_Common_Selection_GmRoot + "/queryPlayerBasicInfo";

    public static final String Ws_Gm_SendGmCommandMsg = "sendGmCommandMsg"; //发送Gm命令
    public static final String Ws_Gm_Selection_SendGmCommandMsg = WS_Common_Selection_GmRoot + "/sendGmCommandMsg";


    public static final String Ws_Gm_Remain = "remain"; //留存
    public static final String Ws_Gm_Selection_Remain = WS_Common_Selection_GmRoot + "/remain";

    public static final String Ws_Gm_DayReport = "dayReport"; //日报（每日充值新增等等）
    public static final String Ws_Gm_Selection_DayReport = WS_Common_Selection_GmRoot + "/dayReport";

    public static final String Ws_Gm_DayRegister = "DayRegister"; //实时新增
    public static final String Ws_Gm_Selection_DayRegister = WS_Common_Selection_GmRoot + "/DayRegister";

    public static final String Ws_Gm_PaymentReport = "paymentReport";//充值记录
    public static final String Ws_Gm_Selection_PaymentReport = WS_Common_Selection_GmRoot + "/paymentReport";

    public static final String Ws_Gm_Mail = "mail";//邮件
    public static final String Ws_Gm_Selection_Mail = WS_Common_Selection_GmRoot + "/mail";

    public static final String Ws_Gm_Activity = "activity"; //活动
    public static final String Ws_Gm_Selection_Activity = WS_Common_Selection_GmRoot + "/activity";

    public static final String Ws_Gm_Data = "data"; //活动
    public static final String Ws_Gm_Selection_Data = WS_Common_Selection_GmRoot + "/data";
}
