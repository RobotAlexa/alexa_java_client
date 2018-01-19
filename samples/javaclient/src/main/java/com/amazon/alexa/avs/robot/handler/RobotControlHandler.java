package com.amazon.alexa.avs.robot.handler;

import com.amazon.alexa.avs.robot.bean.SkillInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RobotControlHandler {

    public abstract boolean canHandle(final SkillInformation skillInformation);

    public abstract void handle(final SkillInformation skillInformation);

    boolean canHandle(final SkillInformation skillInformation, final String title, final String subTitle) {
        return skillInformation != null
                && skillInformation.mainTitle != null
                && !skillInformation.mainTitle.trim().equals("")
                && skillInformation.mainTitle.equals(title)
                && skillInformation.subTitle != null
                && !skillInformation.subTitle.trim().equals("")
                && skillInformation.subTitle.equals(subTitle);
    }
}

