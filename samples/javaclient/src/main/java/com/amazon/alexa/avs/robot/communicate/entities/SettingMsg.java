package com.amazon.alexa.avs.robot.communicate.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qinicy on 2017/5/3.
 */

public class SettingMsg extends SendMessage {
    private static final long serialVersionUID = 2284853761292511049L;
    @SerializedName("type")
    public String type;
    @SerializedName("volume")
    public int volume;
}
