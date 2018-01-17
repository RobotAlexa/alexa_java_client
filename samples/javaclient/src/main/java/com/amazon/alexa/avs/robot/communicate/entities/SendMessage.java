package com.amazon.alexa.avs.robot.communicate.entities;

import com.google.gson.annotations.SerializedName;

public class SendMessage extends ProtocolMessage{

    private static final long serialVersionUID = -5200461292374355317L;

    @SerializedName("version")
    public int version;
    @SerializedName("account")
    public String account;
}
