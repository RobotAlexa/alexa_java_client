package com.amazon.alexa.avs.robot.communicate.entities;

import com.google.gson.annotations.SerializedName;

public class SystemUpgradeMsg extends SendMessage {

    private static final long serialVersionUID = -5781411149222245677L;

    @SerializedName("type")
    public String type;
}
