package com.amazon.alexa.avs.realbutton.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClickMsg implements Serializable{

    private static final long serialVersionUID = 8227380987645550418L;


    public interface Cmd {
        String voice = "voice";
    }

    public interface Type {
        String button = "button";
        String continues = "continue";
    }

    @SerializedName("cmd")
    public String cmd;

    @SerializedName("type")
    public String type;

    @SerializedName("port")
    public int port;

    @SerializedName("data")
    public String data;

}
