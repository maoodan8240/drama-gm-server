package ws.gm.system.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import org.apache.commons.lang3.StringUtils;
import ws.common.utils.di.GlobalInjector;
import ws.gm.features.actor.data.DataActor;
import ws.gm.features.actor.dayRegister.DayRegisterActor;
import ws.gm.features.actor.dayReport.DayReportActor;
import ws.gm.features.actor.gmCommond.SendGmCommandMsgActor;
import ws.gm.features.actor.mail.MailActor;
import ws.gm.features.actor.paymentReport.PaymentReport;
import ws.gm.features.actor.queryPlayerBasicInfo.QueryPlayerBasicInfoActor;
import ws.gm.features.actor.user.UserActor;
import ws.gm.features.actor.user.pojo.Permission;
import ws.gm.features.session.SessionManager;
import ws.gm.system.httpServer.msg.HttpRequestMsg;
import ws.gm.system.httpServer.msg.HttpResponseMsg;
import ws.gm.system.httpServer.msg.MailMsg;
import ws.gm.system.httpServer.msg.QueryPlayerBasicInfoMsg;
import ws.gm.system.httpServer.msg.SendGmCommandMsg;
import ws.gm.system.httpServer.msg.UserAuthRequestMsg;
import ws.gm.system.httpServer.msg.UserAuthResponseMsg;
import ws.gm.system.httpServer.msg.UserLoginRequestMsg;
import ws.gm.system.httpServer.msg.UserRegisterRequestMsg;
import ws.gm.system.httpServer.msg.UserRequestMsg;
import ws.gm.system.httpServer.utils.ResponseUtils;
import ws.relationship.base.actor.WsActor;
import ws.relationship.utils.ActorMsgSynchronizedExecutor;

public class WS_Gm_RootActor extends WsActor {
    private static final SessionManager SESSION_MANAGER = GlobalInjector.getInstance(SessionManager.class);

    public WS_Gm_RootActor() {
        _createChild();
    }

    /**
     * 创建子actor
     */
    private void _createChild() {
        ActorRef ref1 = context().actorOf(Props.create(UserActor.class), WsGmActorSystemPath.Ws_Gm_User);
        ActorRef ref2 = context().actorOf(Props.create(QueryPlayerBasicInfoActor.class), WsGmActorSystemPath.Ws_Gm_QueryPlayerBasicInfo);
        ActorRef ref4 = context().actorOf(Props.create(DayReportActor.class), WsGmActorSystemPath.Ws_Gm_DayReport);
        ActorRef ref5 = context().actorOf(Props.create(DayRegisterActor.class), WsGmActorSystemPath.Ws_Gm_DayRegister);
        ActorRef ref6 = context().actorOf(Props.create(PaymentReport.class), WsGmActorSystemPath.Ws_Gm_PaymentReport);
        ActorRef ref7 = context().actorOf(Props.create(MailActor.class), WsGmActorSystemPath.Ws_Gm_Mail);
        ActorRef ref8 = context().actorOf(Props.create(DataActor.class), WsGmActorSystemPath.Ws_Gm_Data);
        context().watch(ref1);
        context().watch(ref2);
        context().watch(ref4);
        context().watch(ref5);
        context().watch(ref6);
        context().watch(ref7);
        context().watch(ref8);

        context().watch(context().actorOf(Props.create(SendGmCommandMsgActor.class), WsGmActorSystemPath.Ws_Gm_SendGmCommandMsg));
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
            getContext().actorSelection(WsGmActorSystemPath.WS_Common_Selection_GmRoot + "/*").tell(msg, self());


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
            UserAuthResponseMsg responseMsg = ActorMsgSynchronizedExecutor.sendMsgToServer(getContext().actorSelection(WsGmActorSystemPath.Ws_Gm_Selection_User), request);
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
