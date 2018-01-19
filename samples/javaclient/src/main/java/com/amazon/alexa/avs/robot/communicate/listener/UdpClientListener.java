package com.amazon.alexa.avs.robot.communicate.listener;

import com.amazon.alexa.avs.robot.communicate.entities.UdpMsg;
import com.amazon.alexa.avs.robot.communicate.core.UdpClient;

public interface UdpClientListener {

    void onStart(UdpClient client);

    void onStop(UdpClient client);

    void onSend(UdpClient client, UdpMsg udpMsg);

    void onReceive(UdpClient client, UdpMsg udpMsg);

    void onError(UdpClient client, UdpMsg sourceMsg,String msg, Exception e);

    class SimpleUdpClientListener implements UdpClientListener {

        @Override
        public void onStart(UdpClient client) {

        }

        @Override
        public void onStop(UdpClient client) {

        }

        @Override
        public void onSend(UdpClient XUdp, UdpMsg udpMsg) {

        }

        @Override
        public void onReceive(UdpClient client, UdpMsg msg) {

        }

        @Override
        public void onError(UdpClient client, UdpMsg sourceMsg, String msg, Exception e) {

        }
    }
}
