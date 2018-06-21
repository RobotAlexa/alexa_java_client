package com.amazon.alexa.avs.robot.handler;

import com.amazon.alexa.avs.robot.bean.CardTitles;
import com.amazon.alexa.avs.robot.bean.SkillInformation;
import com.amazon.alexa.avs.robot.communicate.MsgSendManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmazonStopHandler extends RobotControlHandler {
    private static final Logger log = LoggerFactory.getLogger(AmazonStopHandler.class);

    @Override
    public boolean canHandle(SkillInformation skillInformation) {
        return canHandle(skillInformation, CardTitles.AMAZON_STOP_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.AMAZON_STOP_CARD, CardTitles.FACE_RECOGNITION_CARD);
    }

    @Override
    public void handle(SkillInformation skillInformation) {
        log.debug("{}.{} skill: {}", "ActionHandler", "handle", skillInformation);
        if (skillInformation != null &&
                skillInformation.mainTitle != null) {
            if (CardTitles.AMAZON_STOP_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                MsgSendManager.getInstance().stop();
            }
        }
    }
}
