package com.amazon.alexa.avs.robot.communicate.protocol;

import com.amazon.alexa.avs.robot.communicate.constants.PROCOTOL_CMDS;
import com.amazon.alexa.avs.robot.communicate.constants.SYSTEM_UPGRADE_TYPE;
import com.amazon.alexa.avs.robot.communicate.entities.*;

public class ProtocolMessageFactory {

    public static ActionControlMsg createActionControlMessage(String account, String type, String name, int repeat) {
        ActionControlMsg msg = new ActionControlMsg();
        msg.cmd = PROCOTOL_CMDS.ACTION;
        msg.account = account;
        msg.type = type;

        PlayActionParam param = new PlayActionParam();
        param.name = name.contains(".zip") ? name.substring(0, name.indexOf(".zip")) : name;
        param.repeat = repeat;

        msg.para = param;
        return msg;
    }

    public static SettingMsg createSettingMessage(String account, String type, int voice) {
        SettingMsg msg = new SettingMsg();
        msg.cmd = PROCOTOL_CMDS.SETTING;
        msg.account = account;
        msg.type = type;
        msg.volume = voice;
        return msg;
    }

    public static FaceRecognitionMsg createFaceRecognitionMessage(String account, String type, String para) {
        FaceRecognitionMsg msg = new FaceRecognitionMsg();
        msg.cmd = PROCOTOL_CMDS.VISION;
        msg.account = account;
        msg.type = type;
        msg.para = para;
        return msg;
    }

    public static RobotLedControlMsg createRobotLedControlMessage(String account, String type, String mode, String color) {
        RobotLedControlMsg msg = new RobotLedControlMsg();
        msg.cmd = PROCOTOL_CMDS.SETTING;
        msg.account = account;

        RobotLedControlMsg.Led led = new RobotLedControlMsg.Led();
        led.type = type;
        led.mode = mode;
        led.color = color;

        msg.led = led;
        return msg;
    }

    public static SystemUpgradeMsg createRobotUpgradeMessage(String account, String type) {
        SystemUpgradeMsg msg = new SystemUpgradeMsg();
        msg.account = account;
        msg.cmd = PROCOTOL_CMDS.UPGRADE;
        msg.type = type;
        return msg;
    }



}
