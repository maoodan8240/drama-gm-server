package drama.gm.system.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import dm.relationship.base.actor.DmActor;
import drama.gm.features.actor.data.DataActor;
import drama.gm.features.actor.mail.MailActor;
import drama.gm.features.actor.mail.msg.MailMsg;
import drama.gm.features.actor.room.RoomActor;
import drama.gm.features.actor.table.TableDataActor;
import drama.gm.features.session.SessionManager;
import drama.gm.system.httpServer.msg.HttpRequestMsg;
import drama.gm.system.httpServer.msg.HttpResponseMsg;
import drama.gm.system.httpServer.utils.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import ws.common.utils.di.GlobalInjector;
import drama.gm.features.actor.dayRegister.DayRegisterActor;
import drama.gm.features.actor.dayReport.DayReportActor;
import drama.gm.features.actor.gmCommond.SendGmCommandMsgActor;
import drama.gm.features.actor.gmCommond.msg.SendGmCommandMsg;
import drama.gm.features.actor.paymentReport.PaymentReport;
import drama.gm.features.actor.queryPlayerBasicInfo.QueryPlayerBasicInfoActor;
import drama.gm.features.actor.queryPlayerBasicInfo.msg.QueryPlayerBasicInfoMsg;
import drama.gm.features.actor.user.UserActor;
import drama.gm.features.actor.user.msg.UserAuthRequestMsg;
import drama.gm.features.actor.user.msg.UserAuthResponseMsg;
import drama.gm.features.actor.user.msg.UserLoginRequestMsg;
import drama.gm.features.actor.user.msg.UserRegisterRequestMsg;
import drama.gm.features.actor.user.msg.UserRequestMsg;
import drama.gm.features.actor.user.pojo.Permission;
import drama.gm.features.actor.whiteblacklist.WhiteBlackListActor;
import dm.relationship.utils.ActorMsgSynchronizedExecutor;

public class Dm_Gm_RootActor extends DmActor {
    private static final SessionManager SESSION_MANAGER = GlobalInjector.getInstance(SessionManager.class);

    public Dm_Gm_RootActor() {
        _createChild();
    }

    /**
     * 创建子actor
     */
    private void _createChild() {
        context().watch(context().actorOf(Props.create(UserActor.class), DmGmActorSystemPath.Dm_Gm_User));
        context().watch(context().actorOf(Props.create(QueryPlayerBasicInfoActor.class), DmGmActorSystemPath.Dm_Gm_QueryPlayerBasicInfo));
        context().watch(context().actorOf(Props.create(DayReportActor.class), DmGmActorSystemPath.Dm_Gm_DayReport));
        context().watch(context().actorOf(Props.create(DayRegisterActor.class), DmGmActorSystemPath.Dm_Gm_DayRegister));
        context().watch(context().actorOf(Props.create(MailActor.class), DmGmActorSystemPath.Dm_Gm_Mail));
        context().watch(context().actorOf(Props.create(DataActor.class), DmGmActorSystemPath.Dm_Gm_Data));
        context().watch(context().actorOf(Props.create(WhiteBlackListActor.class), DmGmActorSystemPath.Dm_Gm_WhiteBlackList));
        context().watch(context().actorOf(Props.create(RoomActor.class), DmGmActorSystemPath.Dm_Gm_Room));
        context().watch(context().actorOf(Props.create(TableDataActor.class), DmGmActorSystemPath.Dm_Gm_Table_Data));


        context().watch(context().actorOf(Props.create(SendGmCommandMsgActor.class), DmGmActorSystemPath.Dm_Gm_SendGmCommandMsg));
    }

    @Override
    public void onRecv(Object msg) throws Exception {
        // need see msg is login or register
        HttpRequestMsg requestMsg = ((HttpRequestMsg) msg);
        if (_authSessionId((HttpRequestMsg) msg)) {
            if (msg instanceof UserRequestMsg.AddUserMsg || msg instanceof UserRequestMsg.DeleteUserMsg
                    || msg instanceof UserRequestMsg.ModifyUserMsg || msg instanceof UserRequestMsg.RequestAllUserMsg) {
                if (!checkPermission(requestMsg, Permission.GmUser)) {
                    return;
                }
            } else if (msg instanceof MailMsg.SendMailToPlayers || msg instanceof MailMsg.SendMailToAll) {
                if (!checkPermission(requestMsg, Permission.Mail)) {
                    return;
                }
            } else if (msg instanceof SendGmCommandMsg.Request) {
                if (!checkPermission(requestMsg, Permission.SendGmCommand)) {
                    return;
                }
            } else if (msg instanceof QueryPlayerBasicInfoMsg.Request) {
                if (!checkPermission(requestMsg, Permission.QueryPlayerBasic)) {
                    return;
                }
            }
            getContext().actorSelection(DmGmActorSystemPath.DM_Common_Selection_GmRoot + "/*").tell(msg, self());


        }
    }

    /**
     * 如果不是登录或者注册这两个消息,就要进这个方法验证session
     *
     * @param msg
     * @return
     */
    private boolean _authSessionId(HttpRequestMsg msg) {
        if (!(msg instanceof UserLoginRequestMsg) && !(msg instanceof UserRegisterRequestMsg)) {
            //if in there, need auth
            String sessionId = msg.getSessionId();
            if (StringUtils.isEmpty(sessionId)) {
                String errorMsg = "there is no sessionId";
                HttpResponseMsg response = HttpResponseMsg.createErrorResponse(errorMsg);
                ResponseUtils.send(response, msg.getExchange());
                return false;
            }
            UserAuthRequestMsg request = new UserAuthRequestMsg(sessionId);
            //getContext().actorSelection(WsGmActorSystemPath.Ws_Gm_Selection_User).tell(request, getSelf());
            UserAuthResponseMsg responseMsg = ActorMsgSynchronizedExecutor.sendMsgToServer(getContext().actorSelection(DmGmActorSystemPath.Dm_Gm_Selection_User), request);
            if (responseMsg.getUser() == null) {
                String errorMsg = String.format("can not find session by this sessionId :%s", sessionId);
                HttpResponseMsg response = HttpResponseMsg.createErrorResponse(errorMsg);
                ResponseUtils.send(response, msg.getExchange());
                return false;
            }
        }
        return true;
    }


    private boolean checkPermission(HttpRequestMsg requestMsg, Permission permission) {
        if (!SESSION_MANAGER.queryBySessionId(requestMsg.getSessionId()).getPermission().contains(permission.ordinal())) {
            HttpResponseMsg response = HttpResponseMsg.createErrorResponse("权限不足");
            ResponseUtils.send(response, requestMsg.getExchange());
            return false;
        }
        return true;
    }

}
