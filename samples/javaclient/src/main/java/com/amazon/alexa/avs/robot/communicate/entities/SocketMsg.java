package com.amazon.alexa.avs.robot.communicate.entities;

import com.amazon.alexa.avs.util.GsonUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SocketMsg implements Serializable {

	private static final long serialVersionUID = 166220954950047722L;

	@Expose(serialize = false)
	private long id;

	@SerializedName(MsgCommon.KEY_CMD)
	private String cmd;

	@SerializedName(MsgCommon.KEY_ACTION)
	private Action action;

	@SerializedName(MsgCommon.KEY_DANCE)
	private Action dance;

	@SerializedName(MsgCommon.KEY_MOVING)
	private Action moving;

	@SerializedName(MsgCommon.KEY_VOLUME)
	private Volume volume;

	public SocketMsg() {
		this.id = System.currentTimeMillis();
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public void setData(Object object) {
		if (object instanceof Dance) {
			this.dance = (Dance) object;

		} else if (object instanceof Moving) {
			this.moving = (Moving) object;

		} else if (object instanceof Volume) {
			this.volume = (Volume) object;

		} else if (object instanceof Action) {
			this.action = (Action) object;
		}
	}

	public String getJsonData() {
		return GsonUtil.get().toJson(this);
	}
}
