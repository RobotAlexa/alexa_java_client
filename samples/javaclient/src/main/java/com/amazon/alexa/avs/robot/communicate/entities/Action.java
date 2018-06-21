package com.amazon.alexa.avs.robot.communicate.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Action implements Serializable {

	private static final long serialVersionUID = 7649045772687668247L;

	@SerializedName(MsgCommon.KEY_NAME)
	private String name = "";

	@SerializedName(MsgCommon.KEY_REPEAT)
	private int repeat = 1;

	public Action(String name, int repeat) {
		this.name = name;
		this.repeat = repeat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}
}
