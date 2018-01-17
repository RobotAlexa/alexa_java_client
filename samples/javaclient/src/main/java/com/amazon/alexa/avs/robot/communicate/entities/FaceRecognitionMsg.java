package com.amazon.alexa.avs.robot.communicate.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qinicy on 2017/5/3.
 */

public class FaceRecognitionMsg extends SendMessage {
    private static final long serialVersionUID = 1297035429418008007L;

    @SerializedName("type")
    public String type;
    @SerializedName("para")
    public String para;
}
