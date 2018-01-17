package com.amazon.alexa.avs.robot.communicate.entities;

import com.google.gson.annotations.SerializedName;

public class ActionControlMsgACK extends ReceiveMessage {
    private static final long serialVersionUID = 6918475377735622129L;
    @SerializedName("type")
    public String action;
}
