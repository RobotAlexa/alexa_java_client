package com.amazon.alexa.avs.robot.handler;

import com.amazon.alexa.avs.robot.bean.BuiltInActionNames;
import com.amazon.alexa.avs.robot.bean.CardTitles;
import com.amazon.alexa.avs.robot.bean.SkillInformation;
import com.amazon.alexa.avs.robot.communicate.MsgSendManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MovingHandler extends RobotControlHandler {
    private static final Logger log = LoggerFactory.getLogger(MovingHandler.class);

    @Override
    public boolean canHandle(SkillInformation skillInformation) {
        return canHandle(skillInformation, CardTitles.MOVING_FORWARD_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.MOVING_BACKWARD_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.MOVING_TURN_LEFT_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.MOVING_TURN_RIGHT_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.MOVING_STOP_CARD, CardTitles.ROBOT_CONTROLLER_CARD);
    }

    @Override
    public void handle(SkillInformation skillInformation) {
        log.debug("{}.{} skill: {}", "ActionHandler", "handle", skillInformation);
        if (skillInformation != null &&
                skillInformation.mainTitle != null) {
            if (CardTitles.MOVING_FORWARD_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                MsgSendManager.getInstance().startAction(BuiltInActionNames.FORWARD, 1);

            } else if (CardTitles.MOVING_BACKWARD_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                MsgSendManager.getInstance().startAction(BuiltInActionNames.BACKWARD, 1);

            } else if (CardTitles.MOVING_TURN_LEFT_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                MsgSendManager.getInstance().startAction(BuiltInActionNames.TURN_LEFT, 1);

            } else if (CardTitles.MOVING_TURN_RIGHT_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                MsgSendManager.getInstance().startAction(BuiltInActionNames.TURN_RIGHT, 1);

            } else if (CardTitles.MOVING_STOP_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                MsgSendManager.getInstance().stop();
            }
        }
    }
}
