package com.amazon.alexa.avs.robot.communicate.entities;

import com.google.gson.annotations.SerializedName;

public class ActionControlMsg extends SendMessage{
    private static final long serialVersionUID = -3734117098721321230L;

    @SerializedName("type")
    public String type;

    @SerializedName("para")
    public PlayActionParam para; // FIXME: 2017/7/19 文档中这部分是array
}
