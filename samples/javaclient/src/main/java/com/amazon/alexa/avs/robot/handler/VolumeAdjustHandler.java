package com.amazon.alexa.avs.robot.handler;

import com.amazon.alexa.avs.robot.bean.CardTitles;
import com.amazon.alexa.avs.robot.bean.SkillInformation;
import com.amazon.alexa.avs.robot.communicate.WlanManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VolumeAdjustHandler extends RobotControlHandler {
    private static final Logger log = LoggerFactory.getLogger(VolumeAdjustHandler.class);

    @Override
    public boolean canHandle(SkillInformation skillInformation) {
        return canHandle(skillInformation, CardTitles.VOLUME_UP_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.VOLUME_DOWN_CARD, CardTitles.ROBOT_CONTROLLER_CARD);
    }

    @Override
    public void handle(SkillInformation skillInformation) {
        log.debug("{}.{} skill: {}", "ActionHandler", "handle", skillInformation);
        if (skillInformation != null &&
                skillInformation.mainTitle != null) {
            if (CardTitles.VOLUME_UP_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                WlanManager.getInstance().volumeUp();

            } else if (CardTitles.VOLUME_DOWN_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                WlanManager.getInstance().volumeDown();
            }
        }
    }
}
