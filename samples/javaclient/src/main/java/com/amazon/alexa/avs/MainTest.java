package com.amazon.alexa.avs;

import com.amazon.alexa.avs.robot.communicate.SocketClient;
import com.amazon.alexa.avs.robot.communicate.entities.MsgCommon;
import com.amazon.alexa.avs.robot.communicate.entities.SocketMsg;
import com.amazon.alexa.avs.robot.communicate.entities.Volume;

public class MainTest {

	public static void main(String[] args) {
		SocketClient client = SocketClient.getClient();
		client.addSocketSendListener(msg -> System.out.println("receive: " + msg));

		SocketMsg msg = new SocketMsg();
		msg.setCmd(MsgCommon.CMD_VOLUME);
		msg.setData(new Volume(MsgCommon.DIRECTION_UP));

		client.sendMsg(msg);

		new Thread(() -> {
			try {
				Thread.sleep(5_000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			SocketMsg msg1 = new SocketMsg();
			msg1.setCmd(MsgCommon.CMD_VOLUME);
			msg1.setData(new Volume(MsgCommon.DIRECTION_DOWN));

			client.sendMsg(msg1);
		}).start();
	}
}
