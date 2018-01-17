package com.amazon.alexa.avs.robot.communicate.core;

import com.amazon.alexa.avs.log.ConsoleLogger;
import com.amazon.alexa.avs.robot.communicate.entities.TargetInfo;
import com.amazon.alexa.avs.robot.communicate.entities.TcpMsg;
import com.amazon.alexa.avs.robot.communicate.entities.UdpMsg;
import com.amazon.alexa.avs.robot.communicate.listener.UdpClientListener;
import com.amazon.alexa.avs.robot.communicate.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class UdpClient {
    private static final Logger log = LoggerFactory.getLogger(UdpClient.class);
    protected UdpClientConfig mUdpClientConfig;
    protected List<UdpClientListener> mUdpClientListeners;
    private DatagramSocket datagramSocket;
    private SendThread sendThread;
    private ReceiveThread receiverThread;
    protected Object lock;

    private UdpClient() {
        lock = new Object();
    }

    public static UdpClient getUdpClient() {
        UdpClient client = new UdpClient();
        client.init();
        return client;
    }

    public int getLocalPort() {
        DatagramSocket ds = getDatagramSocket();
        return ds == null ? 0 : ds.getLocalPort();
    }

    public String getLocalIP() {
        DatagramSocket ds = getDatagramSocket();
        InetAddress address = ds.getLocalAddress();
        InetAddress address2 = ds.getInetAddress();
        SocketAddress address1 = ds.getLocalSocketAddress();

        if (address != null) {
            return address.getHostAddress();
        }
        return "";
    }

    private void init() {
        mUdpClientListeners = new ArrayList<>();
        mUdpClientConfig = new UdpClientConfig.Builder().create();
    }

    public void closeSocket() {
        if (datagramSocket != null && datagramSocket.isConnected()) {
            datagramSocket.disconnect();
            datagramSocket = null;
        }
    }

    public void startUdpServer() {
        if (!getReceiveThread().isAlive()) {
            getReceiveThread().start();
            log.debug("startUdpServer(), udp server started");
        }
    }

    public void stopUdpServer() {
        getReceiveThread().interrupt();
        notifyStopListener();
    }

    public boolean isUdpServerRuning() {
        return getReceiveThread().isAlive();
    }

    public void sendMsg(UdpMsg msg, boolean isReply) {
        if (!getSendThread().isAlive()) {//开启发送线程
            getSendThread().start();
        }
        getSendThread().enqueueUdpMsg(msg);
        if (isReply) {//根据是否需要回复，开启接受线程
            startUdpServer();
        }
    }

    public void sendMsg(UdpMsg msg) {
        sendMsg(msg, false);
    }


    private SendThread getSendThread() {
        if (sendThread == null || !sendThread.isAlive()) {
            sendThread = new SendThread();
        }
        return sendThread;
    }

    private ReceiveThread getReceiveThread() {
        if (receiverThread == null || !receiverThread.isAlive()) {
            receiverThread = new ReceiveThread();
        }
        return receiverThread;
    }

    private DatagramSocket getDatagramSocket() {
        if (datagramSocket != null) {
            return datagramSocket;
        }
        synchronized (lock) {
            if (datagramSocket != null) {
                return datagramSocket;
            }
            int localPort = mUdpClientConfig.getLocalPort();
            try {
                if (localPort > 0) {
                    datagramSocket = UdpSocketManager.getUdpSocket(localPort);
                    if (datagramSocket == null) {
                        datagramSocket = new DatagramSocket(localPort);
                        UdpSocketManager.putUdpSocket(datagramSocket);
                    }
                } else {
                    datagramSocket = new DatagramSocket();
                }
                datagramSocket.setSoTimeout((int) mUdpClientConfig.getReceiveTimeout());
            } catch (SocketException e) {
//                e.printStackTrace();
                notifyErrorListener(new UdpMsg(0), "udp create socket error", e);
                datagramSocket = null;
            }
            return datagramSocket;
        }
    }


    private class SendThread extends Thread {
        private LinkedBlockingQueue<UdpMsg> msgQueue;
        private UdpMsg sendingMsg;

        protected LinkedBlockingQueue<UdpMsg> getMsgQueue() {
            if (msgQueue == null) {
                msgQueue = new LinkedBlockingQueue<>();
            }
            return msgQueue;
        }

        protected SendThread setSendingMsg(UdpMsg sendingMsg) {
            this.sendingMsg = sendingMsg;
            return this;
        }

        public UdpMsg getSendingMsg() {
            return this.sendingMsg;
        }

        public boolean enqueueUdpMsg(final UdpMsg tcpMsg) {
            if (tcpMsg == null || getSendingMsg() == tcpMsg
                    || getMsgQueue().contains(tcpMsg)) {
                return false;
            }
            try {
                getMsgQueue().put(tcpMsg);
                return true;
            } catch (InterruptedException e) {
            }
            return false;
        }

        public boolean cancel(UdpMsg packet) {
            return getMsgQueue().remove(packet);
        }

        public boolean cancel(int tcpMsgID) {
            return getMsgQueue().remove(new UdpMsg(tcpMsgID));
        }

        @Override
        public void run() {
            UdpMsg msg;
            if (getDatagramSocket() == null) {
                return;
            }
            try {
                while (!Thread.interrupted()
                        && (msg = getMsgQueue().take()) != null) {
                    setSendingMsg(msg);//设置正在发送的
                    log.debug("SendThread.run(), udp send msg=" + msg);
                    byte[] data = msg.getSourceDataBytes();
                    if (data == null) {//根据编码转换消息
                        data = CharsetUtil.stringToData(msg.getSourceDataString(), mUdpClientConfig.getCharsetName());
//                        data = msg.getSourceDataString().getBytes();
                    }
                    if (data != null && data.length > 0) {
                        if (msg.getBroadcastType() == UdpMsg.BroadcastType.BROADCAST_TYPE_NORMAL) {
                            TargetInfo mTargetInfo = msg.getTarget();
                            DatagramPacket packet = new DatagramPacket(data, data.length,
                                    new InetSocketAddress(mTargetInfo.getIp(), mTargetInfo.getPort()));
                            try {
                                msg.setTime();
                                datagramSocket.send(packet);
                                notifySendedListener(msg);
                            } catch (IOException e) {
                                e.printStackTrace();
                                notifyErrorListener(msg, "SendMsg message fail!", e);
                            }

                        } else {
                            TargetInfo mTargetInfo = msg.getTarget();
                            try {
                                MulticastSocket multicastSocket = new MulticastSocket();
                                InetAddress serverAddress = InetAddress.getByName(mTargetInfo.getIp());
                                multicastSocket.setTimeToLive(1);
                                multicastSocket.joinGroup(serverAddress);
                                DatagramPacket packet = new DatagramPacket(data, data.length,
                                        new InetSocketAddress(mTargetInfo.getIp(), mTargetInfo.getPort()));
                                multicastSocket.send(packet);
                                notifySendedListener(msg);
                                multicastSocket.close();
                                log.debug("SendThread.run(), Multicast done!");
                            } catch (IOException e) {
                                notifyErrorListener(msg, "SendMsg message fail!", e);
                                e.printStackTrace();
                            } finally {

                            }
                        }

                    }
                }
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        }
    }

    private class ReceiveThread extends Thread {
        @Override
        public void run() {
            if (getDatagramSocket() == null) {
                return;
            }
            byte[] buff = new byte[1024];
            DatagramPacket pack = new DatagramPacket(buff, buff.length);
            notifyStartListener();
            while (!Thread.interrupted()) {
                try {
                    getDatagramSocket().receive(pack);
                    byte[] res = Arrays.copyOf(buff, pack.getLength());
                    log.debug("ReceiveThread.run(), udp receive byte=" + Arrays.toString(res));
                    UdpMsg udpMsg = new UdpMsg(res, new TargetInfo(pack.getAddress().getHostAddress(), pack.getPort()),
                            TcpMsg.MsgType.Receive);
                    udpMsg.setTime();
                    String msgstr = CharsetUtil.dataToString(res, mUdpClientConfig.getCharsetName());
                    udpMsg.setSourceDataString(msgstr);
                    log.debug("ReceiveThread.run(), udp receive msg=" + udpMsg);
                    notifyReceiveListener(udpMsg);
                } catch (IOException e) {
                    if (!(e instanceof SocketTimeoutException)) {//不是超时报错
                        notifyErrorListener(new UdpMsg(0), e.getMessage(), e);
                        notifyStopListener();
                    }
                }
            }
        }
    }

    public void config(UdpClientConfig udpClientConfig) {
        mUdpClientConfig = udpClientConfig;
    }

    public void addUdpClientListener(UdpClientListener listener) {
        if (mUdpClientListeners.contains(listener)) {
            return;
        }
        this.mUdpClientListeners.add(listener);
    }

    public void removeUdpClientListener(UdpClientListener listener) {
        this.mUdpClientListeners.remove(listener);
    }

    private void notifyReceiveListener(final UdpMsg msg) {
        for (UdpClientListener l : mUdpClientListeners) {
            final UdpClientListener listener = l;
            if (listener != null) {
                listener.onReceive(UdpClient.this, msg);
            }
        }
    }

    private void notifyStartListener() {
        for (UdpClientListener l : mUdpClientListeners) {
            final UdpClientListener listener = l;
            if (listener != null) {
                listener.onStart(UdpClient.this);
            }
        }
    }

    private void notifyStopListener() {
        for (UdpClientListener l : mUdpClientListeners) {
            final UdpClientListener listener = l;
            if (listener != null) {
                listener.onStop(UdpClient.this);
            }
        }
    }

    private void notifySendedListener(final UdpMsg msg) {
        for (UdpClientListener l : mUdpClientListeners) {
            final UdpClientListener listener = l;
            if (listener != null) {
                listener.onSend(UdpClient.this, msg);
            }
        }
    }

    private void notifyErrorListener(final UdpMsg source, final String msg, final Exception e) {
        for (UdpClientListener l : mUdpClientListeners) {
            final UdpClientListener listener = l;
            if (listener != null) {
                listener.onError(UdpClient.this, source, msg, e);
            }
        }
    }

    @Override
    public String toString() {
        return "UdpClient{" +
                "datagramSocket=" + datagramSocket +
                '}';
    }
}
