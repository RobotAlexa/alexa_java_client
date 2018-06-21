package com.amazon.alexa.avs.robot.communicate.entities;

import java.io.Serializable;

public class Dance extends Action implements Serializable {

	private static final long serialVersionUID = 7649045772687668247L;

	public Dance(String name, int repeat) {
		super(name, repeat);
	}
}
