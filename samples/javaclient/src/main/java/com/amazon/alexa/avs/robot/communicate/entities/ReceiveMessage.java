package com.amazon.alexa.avs.robot.communicate.entities;

import com.google.gson.annotations.SerializedName;

public class ReceiveMessage extends ProtocolMessage {
    private static final long serialVersionUID = -18959820739737910L;
    @SerializedName("status")
    public String status;
}
