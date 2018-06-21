package com.amazon.alexa.avs.robot.handler;

import com.amazon.alexa.avs.robot.bean.CardTitles;
import com.amazon.alexa.avs.robot.bean.SkillInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemUpgradeHandler extends RobotControlHandler {
    private static final Logger log = LoggerFactory.getLogger(SystemUpgradeHandler.class);

    @Override
    public boolean canHandle(SkillInformation skillInformation) {
        return canHandle(skillInformation, CardTitles.SYSTEM_UPGRADE_CARD, CardTitles.SYSTEM_MANAGER_CARD);
    }

    @Override
    public void handle(SkillInformation skillInformation) {
        log.debug("{}.{} skill: {}", "SystemUpgradeHandler", "handle", skillInformation);
        if (skillInformation != null &&
                skillInformation.mainTitle != null) {
            if (CardTitles.SYSTEM_UPGRADE_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                System.out.println("upgrade robot software...");
            }
        }
    }
}
