package com.amazon.alexa.avs.robot.communicate;

import com.amazon.alexa.avs.robot.communicate.entities.*;

public class MsgSendManager {

	private static MsgSendManager mInstance;
	private static SocketClient client;

	private MsgSendManager() {
	}

	public static synchronized MsgSendManager getInstance() {
		if (mInstance == null) {
			synchronized (MsgSendManager.class) {
				if (mInstance == null) {
					mInstance = new MsgSendManager();
					client = SocketClient.getClient();
					return mInstance;
				}
			}
		}
		return mInstance;
	}

	public void startAction(String name, int repeat) {
		SocketMsg msg = new SocketMsg();
		msg.setCmd(MsgCommon.CMD_ACTION);
		msg.setData(new Action(name, repeat));
		client.sendMsg(msg);
	}

	public void startDance(String name, int repeat) {
		SocketMsg msg = new SocketMsg();
		msg.setCmd(MsgCommon.CMD_DANCE);
		msg.setData(new Dance(name, repeat));
		client.sendMsg(msg);
	}

	public void startMoving(String name, int repeat) {
		SocketMsg msg = new SocketMsg();
		msg.setCmd(MsgCommon.CMD_MOVING);
		msg.setData(new Moving(name, repeat));
		client.sendMsg(msg);
	}

	public void volumeUp() {
		SocketMsg msg = new SocketMsg();
		msg.setCmd(MsgCommon.CMD_VOLUME);
		msg.setData(new Volume(MsgCommon.DIRECTION_UP));
		client.sendMsg(msg);
	}

	public void volumeDown() {
		SocketMsg msg = new SocketMsg();
		msg.setCmd(MsgCommon.CMD_VOLUME);
		msg.setData(new Volume(MsgCommon.DIRECTION_DOWN));
		client.sendMsg(msg);
	}

	public void stop() {
		SocketMsg msg = new SocketMsg();
		msg.setCmd(MsgCommon.CMD_STOP);
		client.sendMsg(msg);
	}

}
