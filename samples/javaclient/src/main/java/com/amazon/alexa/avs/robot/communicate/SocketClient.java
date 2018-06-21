package com.amazon.alexa.avs.robot.communicate;

import com.amazon.alexa.avs.robot.communicate.entities.SocketMsg;
import com.amazon.alexa.avs.robot.communicate.listener.SocketSendListener;
import com.amazon.alexa.avs.util.GsonUtil;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SocketClient {

	static final String HOST = "127.0.0.1";
	public static final int PORT = 29599;

	private List<SocketSendListener> socketSendListeners = new ArrayList<>();
	private SendThread sendThread;
	private Socket mSocket;
	private final Object lock;

	private SocketClient() {
		lock = new Object();
	}

	public static SocketClient getClient() {
		return new SocketClient();
	}

	private Socket getSocket() {
		if (mSocket != null) {
			return mSocket;
		}

		synchronized (lock) {
			try {
				mSocket = new Socket(HOST, PORT);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mSocket;
	}

	public void addSocketSendListener(SocketSendListener listener) {
		socketSendListeners.add(listener);
	}

	public void removeSocketSendListener(SocketSendListener listener) {
		if (socketSendListeners != null && listener != null & socketSendListeners.contains(listener)) {
			socketSendListeners.remove(listener);
		}
	}

	public void notifySocketSend(String msg) {
		for (SocketSendListener listener : socketSendListeners) {
			listener.onSended(msg);
		}
	}

	private SendThread getSendThread() {
		if (sendThread == null || !sendThread.isAlive()) {
			sendThread = new SendThread();
		}
		return sendThread;
	}

	public void sendMsg(SocketMsg msg) {
		if (!getSendThread().isAlive()) {
			//开启发送线程
			getSendThread().start();
		}
		getSendThread().enqueueMsg(msg);
	}

	private class SendThread extends Thread {
		private LinkedBlockingQueue<SocketMsg> msgQueue;
		private SocketMsg sendingMsg;

		protected LinkedBlockingQueue<SocketMsg> getMsgQueue() {
			if (msgQueue == null) {
				msgQueue = new LinkedBlockingQueue<>();
			}
			return msgQueue;
		}

		protected SendThread setSendingMsg(SocketMsg sendingMsg) {
			this.sendingMsg = sendingMsg;
			return this;
		}

		public SocketMsg getSendingMsg() {
			return this.sendingMsg;
		}

		public boolean enqueueMsg(final SocketMsg msg) {
			if (msg == null || getSendingMsg() == msg || getMsgQueue().contains(msg)) {
				return false;
			}
			try {
				System.out.println("put");
				getMsgQueue().put(msg);
				System.out.println(GsonUtil.get().toJson(getMsgQueue()));
				return true;
			} catch (InterruptedException e) {
			}
			return false;
		}

		public boolean cancel(SocketMsg msg) {
			return getMsgQueue().remove(msg);
		}

		@Override
		public void run() {
			SocketMsg msg;
			try {
				while (!Thread.interrupted()) {
					msg = getMsgQueue().poll(5_000, TimeUnit.MILLISECONDS);
					if (msg == null) {
						continue;
					}
					//设置正在发送的
					setSendingMsg(msg);

					mSocket = new Socket(HOST, PORT);
					if (!mSocket.isClosed()) {
						mSocket.setSoTimeout(3_000);
//						DataInputStream inputStream = new DataInputStream(socket.getInputStream());
						DataOutputStream outputStream = new DataOutputStream(mSocket.getOutputStream());

						// 向服务器发数据
						outputStream.writeUTF(msg.getJsonData());

						// 接收服务器数据
//						String receiveMsg = inputStream.readUTF();
//						notifySocketSend(receiveMsg);

						outputStream.close();
//						inputStream.close();
						mSocket.close();
						mSocket = null;
					} else {
						break;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
