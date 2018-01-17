package com.amazon.alexa.avs.robot.communicate.listener;

import com.amazon.alexa.avs.robot.communicate.entities.RobotStateException;

public interface RobotActionControlListener {
    void onActionControlSuccess();
    void onActionControlFail(RobotStateException e);
}
