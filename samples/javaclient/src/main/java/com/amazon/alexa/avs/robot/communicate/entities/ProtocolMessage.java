package com.amazon.alexa.avs.robot.communicate.entities;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProtocolMessage implements Serializable {

    private static final long serialVersionUID = -7590019896470461308L;

    @SerializedName("cmd")
    public String cmd;

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
