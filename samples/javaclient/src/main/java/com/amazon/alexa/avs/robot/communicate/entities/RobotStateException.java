package com.amazon.alexa.avs.robot.communicate.entities;

public class RobotStateException extends Throwable {
    /**
     * 忙，表示被其它设备占用
     */
    public static final int ERROR_CODE_ROBOT_BUSY = 0x01;
    /**
     * 电量低
     */
    public static final int ERROR_CODE_ROBOT_LOW_BATTERY = 0x02;
    /**
     * 断开，表示需要重新连接
     */
    public static final int ERROR_CODE_ROBOT_DISCONNECT = 0x03;

    /**
     * 发送命令执行失败
     */
    public static final int ERROR_CODE_ROBOT_FAIL = 0x04;
    /**
     * 发送或接收消息时异常
     */
    public static final int ERROR_CODE_SEND_FAIL = 0x05;


    public int errorCode;
    public RobotStateException(int errorCode, String errorMessage) {
        super(errorMessage, new Throwable(errorMessage));
        this.errorCode = errorCode;
    }

    public RobotStateException(int errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
    }
}
