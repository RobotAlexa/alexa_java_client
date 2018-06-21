package com.amazon.alexa.avs.robot.handler;

import com.amazon.alexa.avs.robot.bean.CardTitles;
import com.amazon.alexa.avs.robot.bean.SkillInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FaceFunctionHandler extends RobotControlHandler {
    private static final Logger log = LoggerFactory.getLogger(FaceFunctionHandler.class);
    public static boolean isTrackingFace = false;

    @Override
    public boolean canHandle(SkillInformation skillInformation) {
        return canHandle(skillInformation, CardTitles.FACE_TRACK_CARD, CardTitles.FACE_RECOGNITION_CARD) ||
                canHandle(skillInformation, CardTitles.FACE_TRACK_STOP_CARD, CardTitles.FACE_RECOGNITION_CARD) ||
                canHandle(skillInformation, CardTitles.FACE_ANALYZE_FACE_CARD, CardTitles.FACE_RECOGNITION_CARD) ||
                canHandle(skillInformation, CardTitles.FACE_ANALYZE_GESTURE_CARD, CardTitles.FACE_RECOGNITION_CARD) ||
                canHandle(skillInformation, CardTitles.FACE_ANALYZE_OBJECT_CARD, CardTitles.FACE_RECOGNITION_CARD);
    }

    @Override
    public void handle(SkillInformation skillInformation) {
        log.debug("{}.{} skill: {}", "ActionHandler", "handle", skillInformation);
        if (skillInformation != null &&
                skillInformation.mainTitle != null) {
            if (CardTitles.FACE_TRACK_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                isTrackingFace = true;

            } else if (CardTitles.FACE_TRACK_STOP_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {
                isTrackingFace = false;

            } else if (CardTitles.FACE_ANALYZE_FACE_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {

            } else if (CardTitles.FACE_ANALYZE_GESTURE_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {

            } else if (CardTitles.FACE_ANALYZE_OBJECT_CARD.equalsIgnoreCase(skillInformation.mainTitle.trim())) {

            }
        }
    }
}
