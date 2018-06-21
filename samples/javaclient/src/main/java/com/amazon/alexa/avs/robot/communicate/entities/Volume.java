package com.amazon.alexa.avs.robot.communicate.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Volume implements Serializable {
	private static final long serialVersionUID = -6029181332485704824L;

	public static final String UP = "up";
	public static final String DOWN = "down";

	@SerializedName(MsgCommon.KEY_DIRECTION)
	private String direction;

	public Volume(String direction) {
		this.direction = direction;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
