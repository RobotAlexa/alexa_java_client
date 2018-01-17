package com.amazon.alexa.avs.robot.communicate.constants;

/**
 * Created by qinicy on 2017/5/3.
 */

public interface ACK_STATUS {
    String IDLE = "idle";
    String OK = "ok";

    String EXIST = "exist";

    String BUSY = "busy";
    String LOWBATTERY = "lowbattery";
    String DISCONNECT = "disconnect";
    String FAIL = "fail";

    String START = "start";
    String RUN = "run";
    String PAUSE = "pause";
    String FINISH = "finish";
    /**
     * 额外加的，用来说明消息发送或者接收时发生了异常。
     */
    String INTERNAL_ERROR = "send message exception";
    String FILE_NOT_FOUND = "file not found";
    String CONNECT_TIMEOUT = "connect time out";
    String THREAD_INTERRUPT = "thread interrupt";
    String IO_EXCEPTION = "io exception";
    String CONNECT_INTERRUPT = "socket connect state is interrupted!";

}
