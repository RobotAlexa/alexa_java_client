package com.amazon.alexa.avs.robot.communicate.constants;

/**
 * 取值说明：
 * init：初始化；START：播放文件；pause：暂停；continue：继续；stop：停止
 */
public interface PLAY_CONTROL_TYPES {
    String INIT = "init";
    String START = "start";
    String PAUSE = "pause";
    String CONTINUE = "continue";
    String STOP = "stop";
    String RESET = "reset";
    String BLOCKLY_START = "blockly_start";
    String BLOCKLY_STOP = "blockly_stop";
}