package com.amazon.alexa.avs.robot.handler;

import com.amazon.alexa.avs.robot.bean.BuiltInActionNames;
import com.amazon.alexa.avs.robot.bean.CardTitles;
import com.amazon.alexa.avs.robot.communicate.constants.PLAY_CONTROL_TYPES;
import com.amazon.alexa.avs.robot.bean.SkillInformation;
import com.amazon.alexa.avs.robot.communicate.WlanManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionHandler extends RobotControlHandler {
    private static final Logger log = LoggerFactory.getLogger(ActionHandler.class);

    @Override
    public boolean canHandle(SkillInformation skillInformation) {
        return canHandle(skillInformation, CardTitles.ACTION_GO_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.ACTION_GOOD_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.ACTION_KICK_LEFT_FOOT_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.ACTION_KICK_RIGHT_FOOT_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.ACTION_LEFT_HIT_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.ACTION_RIGHT_HIT_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.ACTION_PUSH_UP_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.ACTION_SHOOT_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.ACTION_SHOOT_LEFT_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.ACTION_SHOOT_RIGHT_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.ACTION_STOP_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ;
    }

    @Override
    public void handle(SkillInformation skillInformation) {
        log.debug("{}.{} skill: {}", "ActionHandler", "handle", skillInformation);
        if (skillInformation != null &&
                skillInformation.mainTitle != null) {
            if (CardTitles.ACTION_GO_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                WlanManager.getInstance().actionControl(PLAY_CONTROL_TYPES.START, BuiltInActionNames.GO, 1);

            } else if (CardTitles.ACTION_GOOD_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                WlanManager.getInstance().actionControl(PLAY_CONTROL_TYPES.START, BuiltInActionNames.GOOD, 1);

            } else if (CardTitles.ACTION_KICK_LEFT_FOOT_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                WlanManager.getInstance().actionControl(PLAY_CONTROL_TYPES.START, BuiltInActionNames.LEFT_KICK, 1);

            } else if (CardTitles.ACTION_KICK_RIGHT_FOOT_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                WlanManager.getInstance().actionControl(PLAY_CONTROL_TYPES.START, BuiltInActionNames.RIGHT_KICK, 1);

            } else if (CardTitles.ACTION_LEFT_HIT_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                WlanManager.getInstance().actionControl(PLAY_CONTROL_TYPES.START, BuiltInActionNames.LEFT_HIT, 1);

            } else if (CardTitles.ACTION_RIGHT_HIT_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                WlanManager.getInstance().actionControl(PLAY_CONTROL_TYPES.START, BuiltInActionNames.RIGHT_HIT, 1);

            } else if (CardTitles.ACTION_PUSH_UP_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                WlanManager.getInstance().actionControl(PLAY_CONTROL_TYPES.START, BuiltInActionNames.PUSH_UP, 1);

            } else if (CardTitles.ACTION_SHOOT_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                WlanManager.getInstance().actionControl(PLAY_CONTROL_TYPES.START, BuiltInActionNames.SHOOT, 1);

            } else if (CardTitles.ACTION_SHOOT_LEFT_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                WlanManager.getInstance().actionControl(PLAY_CONTROL_TYPES.START, BuiltInActionNames.SHOOT_LEFT, 1);

            } else if (CardTitles.ACTION_SHOOT_RIGHT_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                WlanManager.getInstance().actionControl(PLAY_CONTROL_TYPES.START, BuiltInActionNames.SHOOT_RIGHT, 1);

            } else if (CardTitles.ACTION_STOP_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                WlanManager.getInstance().actionControl(PLAY_CONTROL_TYPES.RESET, BuiltInActionNames.STOP, 1);
            }
        }


    }
}
