package com.amazon.alexa.avs.robot.communicate.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlayActionParam implements Serializable {
    private static final long serialVersionUID = -8810248475133431357L;

    /**
     * 动作文件名
     */
    @SerializedName("name")
    public String name;
    /**
     * 重复次数，取值说明：0：一直重复;其它：重复次数
     */
    @SerializedName("repeat")
    public int repeat;
}
