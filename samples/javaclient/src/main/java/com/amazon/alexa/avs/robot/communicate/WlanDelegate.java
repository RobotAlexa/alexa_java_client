package com.amazon.alexa.avs.robot.communicate;

import com.amazon.alexa.avs.robot.communicate.constants.*;
import com.amazon.alexa.avs.robot.communicate.core.UdpClient;
import com.amazon.alexa.avs.robot.communicate.core.UdpClientConfig;
import com.amazon.alexa.avs.robot.communicate.entities.*;
import com.amazon.alexa.avs.robot.communicate.listener.RobotActionControlListener;
import com.amazon.alexa.avs.robot.communicate.listener.UdpClientListener;
import com.amazon.alexa.avs.robot.communicate.protocol.ProtocolMessageFactory;
import com.amazon.alexa.avs.util.GsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WlanDelegate {
    private static final Logger log = LoggerFactory.getLogger(WlanDelegate.class);

    private static final int DEFAULT_PROTOCOL_PORT = 20001;
    private static final String LOCAL_HOST_IP = "127.0.0.1";
    private static final String DEFAULT_ACCOUNT = "127.0.0.1";

    private UdpClient mUdpClient;
    private RobotUdpClientListener mRobotUdpClientListener;
    private RobotActionControlListener mRobotActionControlListener;

    public WlanDelegate() {
        initUdpClient();
    }

    private void initUdpClient() {
        UdpClientConfig udpConfig = new UdpClientConfig.Builder()
                .receiveTimeout(2000)
                .create();

        mUdpClient = UdpClient.getUdpClient();
        mUdpClient.config(udpConfig);
        mUdpClient.startUdpServer();
        if (mRobotUdpClientListener == null) {
            mRobotUdpClientListener = new RobotUdpClientListener();
        }
        mUdpClient.addUdpClientListener(mRobotUdpClientListener);
    }

    /**
     * 动作控制
     *
     * @param type       类型，参看：{@link PLAY_CONTROL_TYPES}
     * @param actionName 基本动作名
     * @param repeat     0：一直重复;其它：重复次数
     */
    public void actionControl(String type, String actionName, int repeat) {
        TargetInfo targetInfo = new TargetInfo(LOCAL_HOST_IP, DEFAULT_PROTOCOL_PORT);
        ActionControlMsg msg = ProtocolMessageFactory.createActionControlMessage(DEFAULT_ACCOUNT, type, actionName, repeat);
        final UdpMsg message = new UdpMsg(msg.toJson(), targetInfo, TcpMsg.MsgType.SendMsg);
        message.setType(MESSAGE_TYPE.ACTION);

        mUdpClient.sendMsg(message, true);
    }

    /**
     * 音量加
     */
    public void volumeUp() {
        voiceSetting(SETTING_TYPE.VOLUME_UP, 0);
    }

    /**
     * 音量减
     */
    public void volumeDown() {
        voiceSetting(SETTING_TYPE.VOLUME_DOWN, 0);
    }

    /**
     * 音量设置
     *
     * @param type  类型 SETTING_TYPE.VOLUME_UP_CARD, SETTING_TYPE.VOLUME_DOWN_CARD, SETTING_TYPE.VOLUME
     * @param voice 音量值
     */
    public void voiceSetting(String type, int voice) {
        TargetInfo targetInfo = new TargetInfo(LOCAL_HOST_IP, DEFAULT_PROTOCOL_PORT);
        SettingMsg msg = ProtocolMessageFactory.createSettingMessage(DEFAULT_ACCOUNT, type, voice);
        UdpMsg message = new UdpMsg(msg.toJson(), targetInfo, TcpMsg.MsgType.SendMsg);
        message.setType(MESSAGE_TYPE.SETTING);
        mUdpClient.sendMsg(message, true);
    }

    /**
     * 人脸识别、手势识别、人脸跟踪等功能
     *
     * @param type 类型，see {@link FACE_RECOGNITION_TYPE}
     * @param para 参数，see {@link FACE_RECOGNITION_PARA}
     */
    public void faceTracking(String type, String para) {
        TargetInfo targetInfo = new TargetInfo(LOCAL_HOST_IP, DEFAULT_PROTOCOL_PORT);
        FaceRecognitionMsg msg = ProtocolMessageFactory.createFaceRecognitionMessage(DEFAULT_ACCOUNT, type, para);
        UdpMsg message = new UdpMsg(msg.toJson(), targetInfo, TcpMsg.MsgType.SendMsg);
        message.setType(MESSAGE_TYPE.VISION);
        mUdpClient.sendMsg(message, true);
    }

    /**
     * 设置机器人led
     *
     * @param type  see {@link LED_TYPE}
     * @param mode  see {@link LED_MODE}
     * @param color see {@link LED_COLOR}
     */
    public void setRobotLed(String type, String mode, String color) {
        TargetInfo targetInfo = new TargetInfo(LOCAL_HOST_IP, DEFAULT_PROTOCOL_PORT);
        RobotLedControlMsg msg = ProtocolMessageFactory.createRobotLedControlMessage(DEFAULT_ACCOUNT, type, mode, color);
        UdpMsg message = new UdpMsg(msg.toJson(), targetInfo, TcpMsg.MsgType.SendMsg);
        message.setType(MESSAGE_TYPE.LED);
        mUdpClient.sendMsg(message, true);
    }

    /**
     * 让机器人升级
     */
    public void upgradeRobotSystem() {
        TargetInfo targetInfo = new TargetInfo(LOCAL_HOST_IP, DEFAULT_PROTOCOL_PORT);
        SystemUpgradeMsg msg = ProtocolMessageFactory.createRobotUpgradeMessage(DEFAULT_ACCOUNT, SYSTEM_UPGRADE_TYPE.raspberry);
        UdpMsg message = new UdpMsg(msg.toJson(), targetInfo, TcpMsg.MsgType.SendMsg);
        mUdpClient.sendMsg(message, true);
    }

    public void setRobotActionControlListener(RobotActionControlListener robotActionControlListener) {
        mRobotActionControlListener = robotActionControlListener;
    }

    private void notifyActionControlSuccess() {
        if (mRobotActionControlListener != null) {
            mRobotActionControlListener.onActionControlSuccess();
        }
    }

    private void notifyActionControlFail(String errorState) {
        if (mRobotActionControlListener != null) {
            RobotStateException exception = generateErrorMessage(errorState);
            mRobotActionControlListener.onActionControlFail(exception);
        }
    }

    private RobotStateException generateErrorMessage(String errorState) {
        int ecode = 0;
        String emsg = errorState;
        if (ACK_STATUS.BUSY.equals(errorState)) {
            ecode = RobotStateException.ERROR_CODE_ROBOT_BUSY;
            emsg = "AlphaInfo is busy! ";
        } else if (ACK_STATUS.LOWBATTERY.equals(errorState)) {
            ecode = RobotStateException.ERROR_CODE_ROBOT_LOW_BATTERY;
            emsg = "AlphaInfo is in low battery!";
        } else if (ACK_STATUS.DISCONNECT.equals(errorState)) {
            ecode = RobotStateException.ERROR_CODE_ROBOT_DISCONNECT;
            emsg = "AlphaInfo is disconnect,please connect it first!";
        } else if (ACK_STATUS.FAIL.equals(errorState)) {
            ecode = RobotStateException.ERROR_CODE_ROBOT_FAIL;
            emsg = "Alpha he say fail!";
        } else if (ACK_STATUS.INTERNAL_ERROR.equals(errorState)) {
            ecode = RobotStateException.ERROR_CODE_SEND_FAIL;
            emsg = ACK_STATUS.INTERNAL_ERROR;
        } else if (ACK_STATUS.CONNECT_INTERRUPT.equals(errorState)) {
            ecode = RobotStateException.ERROR_CODE_SEND_FAIL;
            emsg = ACK_STATUS.CONNECT_INTERRUPT;
        } else if (ACK_STATUS.CONNECT_TIMEOUT.equals(errorState)) {
            ecode = RobotStateException.ERROR_CODE_SEND_FAIL;
            emsg = ACK_STATUS.CONNECT_TIMEOUT;
        }
        return new RobotStateException(ecode, emsg);

    }

    /**
     * udp status listener
     */
    private class RobotUdpClientListener implements UdpClientListener {
        final String TAG = RobotUdpClientListener.class.getSimpleName();

        @Override
        public void onStart(UdpClient client) {

        }

        @Override
        public void onStop(UdpClient client) {

        }

        @Override
        public void onSend(UdpClient client, UdpMsg udpMsg) {
            log.debug("{}.{} message: {}", TAG, "onSend", udpMsg.toString());
        }

        @Override
        public void onReceive(UdpClient client, UdpMsg udpMsg) {
            log.debug("{}.{} message: {}", TAG, "onReceive", udpMsg.getSourceDataString());
            String json = udpMsg.getSourceDataString();
            ProtocolMessage msg = GsonUtil.get().toObject(json, ProtocolMessage.class);

            //动作控制
            if (msg != null && PROCOTOL_CMDS.ACTION_ACK.equalsIgnoreCase(msg.cmd)) {
                ActionControlMsgACK actionControlMsgACK = GsonUtil.get().toObject(json, ActionControlMsgACK.class);
                if (ACK_STATUS.OK.equals(actionControlMsgACK.status)) {
                    notifyActionControlSuccess();
                } else {
                    notifyActionControlFail(actionControlMsgACK.status);
                }
            }
        }

        @Override
        public void onError(UdpClient client, UdpMsg sourceMsg, String msg, Exception e) {

        }
    }
}
