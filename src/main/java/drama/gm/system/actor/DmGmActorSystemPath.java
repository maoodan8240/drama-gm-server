package drama.gm.system.actor;

/**
 * Created by lee on 17-2-24.
 */
public class DmGmActorSystemPath {
    /**
     * =GM后台Actor路径及名称=======================
     */
    public static final String DM_Common_GmRoot = "GmRoot";

    public static final String DM_Common_Selection_GmRoot = "/user/GmRoot";
    public static final String Dm_Gm_User = "user"; // Gm用户管理
    public static final String Dm_Gm_Selection_User = DM_Common_Selection_GmRoot + "/user";

    public static final String Dm_Gm_QueryPlayerBasicInfo = "queryPlayerBasicInfo"; //查询玩家
    public static final String Dm_Gm_Selection_Player = DM_Common_Selection_GmRoot + "/queryPlayerBasicInfo";

    public static final String Dm_Gm_SendGmCommandMsg = "sendGmCommandMsg"; //发送Gm命令
    public static final String Dm_Gm_Selection_SendGmCommandMsg = DM_Common_Selection_GmRoot + "/sendGmCommandMsg";


    public static final String Dm_Gm_Remain = "remain"; //留存
    public static final String Dm_Gm_Selection_Remain = DM_Common_Selection_GmRoot + "/remain";

    public static final String Dm_Gm_DayReport = "dayReport"; //日报（每日充值新增等等）
    public static final String Dm_Gm_Selection_DayReport = DM_Common_Selection_GmRoot + "/dayReport";

    public static final String Dm_Gm_DayRegister = "DayRegister"; //实时新增
    public static final String Dm_Gm_Selection_DayRegister = DM_Common_Selection_GmRoot + "/DayRegister";

    public static final String Dm_Gm_PaymentReport = "paymentReport";//充值记录
    public static final String Dm_Gm_Selection_PaymentReport = DM_Common_Selection_GmRoot + "/paymentReport";

    public static final String Dm_Gm_Mail = "mail";//邮件
    public static final String Dm_Gm_Selection_Mail = DM_Common_Selection_GmRoot + "/mail";

    public static final String Dm_Gm_Activity = "activity"; //活动
    public static final String Dm_Gm_Selection_Activity = DM_Common_Selection_GmRoot + "/activity";

    public static final String Dm_Gm_Data = "data"; //活动
    public static final String Dm_Gm_Selection_Data = DM_Common_Selection_GmRoot + "/data";

    public static final String Dm_Gm_WhiteBlackList = "whiteblacklist"; //黑白名单
    public static final String Dm_Gm_Selection_WhiteBlackList = DM_Common_Selection_GmRoot + "whiteblacklist";

    public static final String Dm_Gm_Room = "room"; //房间
    public static final String Dm_Gm_Selection_Room = DM_Common_Selection_GmRoot + "room";

    public static final String Dm_Gm_Table_Data = "tableData"; //配置
    public static final String Dm_Gm_Selection_Table_Data = DM_Common_Selection_GmRoot + "tableData";
}
