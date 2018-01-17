package com.amazon.alexa.avs.tts;

import com.amazon.alexa.avs.robot.communicate.entities.SendMessage;
import com.google.gson.annotations.SerializedName;

public class TTSBean extends SendMessage {

    private static final long serialVersionUID = -5790568899378009487L;

    @SerializedName("type")
    public String type;

    @SerializedName("data")
    public String text;

}
