package com.amazon.alexa.avs.robot.handler;

import com.amazon.alexa.avs.log.ConsoleLogger;
import com.amazon.alexa.avs.robot.bean.CardTitles;
import com.amazon.alexa.avs.robot.communicate.constants.PLAY_CONTROL_TYPES;
import com.amazon.alexa.avs.robot.bean.SkillInformation;
import com.amazon.alexa.avs.robot.communicate.WlanManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DanceHandler extends RobotControlHandler {
    private static final Logger log = LoggerFactory.getLogger(DanceHandler.class);

    private static String mLastDanceName;

    private static final String[] BuiltInDances = {
            "Waka_waka",
            "Happy_Birthday",
            "Sweet_and_sour",
            "we_are_taking_off",
    };

    @Override
    public boolean canHandle(SkillInformation skillInformation) {
        return canHandle(skillInformation, CardTitles.DANCE_CARD, CardTitles.ROBOT_CONTROLLER_CARD) ||
                canHandle(skillInformation, CardTitles.DANCE_STOP_CARD, CardTitles.ROBOT_CONTROLLER_CARD);
    }

    @Override
    public void handle(SkillInformation skillInformation) {
        log.debug("{}.{} skill: {}", "ActionHandler", "handle", skillInformation);
        if (skillInformation != null &&
                skillInformation.mainTitle != null) {
            if (CardTitles.DANCE_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                // Start to dance
                log.info("Start to dance...........................................................");
                int index = (int) (Math.random() * BuiltInDances.length);
                String dance = BuiltInDances[index];
                mLastDanceName = dance;
                WlanManager.getInstance().actionControl(PLAY_CONTROL_TYPES.START, dance, 1);

                log.info("Dance name: {}.", dance);
                ConsoleLogger.print("DanceHandler", "Start to dance. Dance name: " + dance);

            } else if (CardTitles.DANCE_STOP_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                log.info("Stop dancing...........................................................");
                ConsoleLogger.print("DanceHandler", "Stop dancing....");
                WlanManager.getInstance().actionControl(PLAY_CONTROL_TYPES.STOP, mLastDanceName, 1);
            }

        }


    }
}

