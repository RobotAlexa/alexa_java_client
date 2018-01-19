package com.amazon.alexa.avs.tts;

import com.amazon.alexa.avs.robot.communicate.constants.PROCOTOL_CMDS;
import com.amazon.alexa.avs.robot.communicate.constants.VOICE_STATUS;
import com.amazon.alexa.avs.robot.communicate.constants.VOICE_TYPE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;

public class TTSUdpServer {
    private static final Logger log = LoggerFactory.getLogger(TTSUdpServer.class);

    private boolean mListening = false;

    private static final int RECEIVE_TIMEOUT = 1000 * 20;
    private static final int TRY_MAXNUM = Integer.MAX_VALUE;
    private final int MAX_LENGTH = 2048;  // 最大接收字节长度
    private final int PORT_NUM = 20100;   // tts port号
    private byte[] receMsgs = new byte[MAX_LENGTH];
    private DatagramSocket datagramSocket;
    private OnUdpReceivedListener mListener;

    private TTSUdpServer() {

    }

    private static class SingletonHolder {
        private final static TTSUdpServer instance = new TTSUdpServer();
    }

    public static TTSUdpServer getInstance() {
        return TTSUdpServer.SingletonHolder.instance;
    }

    private void initListener() {
        new Thread(() -> {
            byte[] buf;
            DatagramPacket datagramPacket;
            String str_receive;
            while (mListening) {
                try {
                    if (datagramSocket == null) {
                        datagramSocket = new DatagramSocket(PORT_NUM);
                        datagramSocket.setSoTimeout(RECEIVE_TIMEOUT);
                    }
                    buf = new byte[MAX_LENGTH];
                    datagramPacket = new DatagramPacket(buf, MAX_LENGTH);
                    // blocking here
                    datagramSocket.receive(datagramPacket);
                    log.info("Data of receive from : " + datagramPacket.getAddress());
                    str_receive = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                    log.info("Received data for tts." + str_receive);
//                    datagramPacket.setLength(MAX_LENGTH);

                    sendAck(datagramSocket, datagramPacket.getAddress(), datagramPacket.getPort());

                    if (mListener != null) {
                        mListener.onReceived(datagramSocket, datagramPacket, str_receive);
                    }
                } catch (Exception e) {
                    if (!(e instanceof SocketTimeoutException)) {
                        log.error("Error: " + e.getMessage(), e);
                        if (datagramSocket != null) {
                            datagramSocket.close();
                            datagramSocket = null;
                        }
                        startListen();
                        return;
                    }
                }
            }
            datagramSocket.close();
            datagramSocket = null;
            log.info("...TTS listen thread end...");
        }).start();
    }

    public void startListen() {
        if (!mListening) {
            log.info("Start tts server.");
            mListening = true;
            initListener();
        }
    }

    public void stopListen() {
        log.info("Stop tts server.");
        mListening = false;
    }

    public void setOnUdpReceivedListener(OnUdpReceivedListener listener) {
        mListener = listener;
    }

    public void sendAck(DatagramSocket socket, InetAddress addr, int port) throws IOException {
        TTSBeanACK ack = new TTSBeanACK();
        ack.cmd = PROCOTOL_CMDS.VOICE_ACK;
        ack.type = VOICE_TYPE.TTS;
        ack.status = VOICE_STATUS.OK;

        String data = ack.toJson();
	    System.out.println("ack: " + data + ", addr: " + addr + ", port: " + port);
        byte[] backbuf = data.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(backbuf, backbuf.length, addr, port);
        socket.send(sendPacket);
    }

    public interface OnUdpReceivedListener {
        void onReceived(DatagramSocket socket, DatagramPacket packet, String jsonText);
    }
}

