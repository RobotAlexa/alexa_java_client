package com.amazon.alexa.avs.robot.communicate.entities;

import java.io.Serializable;

public class Moving extends Action implements Serializable {
	private static final long serialVersionUID = 396520633881569613L;

	public Moving(String name, int repeat) {
		super(name, repeat);
	}
}
