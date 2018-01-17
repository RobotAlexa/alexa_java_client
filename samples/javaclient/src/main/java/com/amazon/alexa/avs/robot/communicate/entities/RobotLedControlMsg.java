package com.amazon.alexa.avs.robot.communicate.entities;

import com.amazon.alexa.avs.robot.communicate.constants.SETTING_TYPE;
import com.google.gson.annotations.SerializedName;

/**
 * Created by qinicy on 2017/5/3.
 */

public class RobotLedControlMsg extends SendMessage {
    private static final long serialVersionUID = 1297035429418008007L;

    @SerializedName("type")
    public final String type = SETTING_TYPE.LED;

    @SerializedName("para")
    public Led led;

    public static class Led {

        @SerializedName("type")
        public String type;

        @SerializedName("mode")
        public String mode;

        @SerializedName("color")
        public String color;
    }


}
