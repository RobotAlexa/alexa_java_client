package com.amazon.alexa.avs.tts;

import com.amazon.alexa.avs.robot.communicate.entities.ReceiveMessage;
import com.google.gson.annotations.SerializedName;

public class TTSBeanACK extends ReceiveMessage {
    private static final long serialVersionUID = -6444816269799974402L;

    @SerializedName("type")
    public String type;
}
