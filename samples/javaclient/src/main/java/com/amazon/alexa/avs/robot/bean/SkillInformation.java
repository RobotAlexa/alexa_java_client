package com.amazon.alexa.avs.robot.bean;

public class SkillInformation {

    /**
     * main title of RenderTemplate
     */
    public String mainTitle;

    /**
     * sub title of RenderTemplate
     */
    public String subTitle;

    @Override
    public String toString() {
        return "SkillInformation:{\"mainTitle\": \"" + mainTitle + "\", \"subTitle\": \"" + subTitle + "\"}";
    }
}

