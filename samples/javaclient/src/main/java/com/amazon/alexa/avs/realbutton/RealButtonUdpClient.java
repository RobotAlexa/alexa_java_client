package com.amazon.alexa.avs.realbutton;

import com.amazon.alexa.avs.realbutton.bean.ClickMsg;
import com.amazon.alexa.avs.robot.communicate.WlanManager;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class RealButtonUdpClient {
    private static final Logger log = LoggerFactory.getLogger(RealButtonUdpClient.class);
    private static final int RECEIVE_TIMEOUT = 1000 * 10;
    private static final int TRY_MAXNUM = 5;

    private boolean mListening = false;
    private OnRealButtonClickListener mClickListener;
    private DatagramSocket ds = null;

    private RealButtonUdpClient() {
    }

    private static class SingletonHolder {
        private final static RealButtonUdpClient instance = new RealButtonUdpClient();
    }

    public static RealButtonUdpClient getInstance() {
        return RealButtonUdpClient.SingletonHolder.instance;
    }

    // Listen real robot's button click event.
    private void initListen() {
        if (ds == null) {
            try {
                ds = new DatagramSocket(20200);
                Runtime.getRuntime().exec("sudo service ubtedu start");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        new Thread(() -> {
            while (mListening) {
                try {
                    if (ds == null) {
                        ds = new DatagramSocket(20200);
                        Runtime.getRuntime().exec("sudo service ubtedu start");
                    }
                    byte[] buf = new byte[1024];
                    DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
                    int tries = 0;                         // try count of receive data
                    boolean receivedResponse = false;

                    ds.setSoTimeout(RECEIVE_TIMEOUT);

                    while (mListening) {
                        while (!receivedResponse && tries < TRY_MAXNUM && mListening) {
                            try {
                                // Receive from server
                                ds.receive(dp_receive);
                                log.info("Data of receive from : " + dp_receive.getAddress());
                                receivedResponse = true;

                            } catch (Exception e) {
                                tries += 1;
                                log.info("Time out," + (TRY_MAXNUM - tries) + " more tries...");
                            }
                        }
                        if (receivedResponse) {
                            String str_receive = new String(dp_receive.getData(), 0, dp_receive.getLength());
                            log.info("Received data from button click event." + str_receive);
                            dp_receive.setLength(1024);
                            ClickMsg clickMsg = null;
                            try {
                                Gson gson = new Gson();
                                clickMsg = gson.fromJson(str_receive, ClickMsg.class);

                            } catch (Exception e) {
                                log.error("Error: " + e.getMessage());
                            }

                            if (clickMsg == null) {
                                if (mClickListener != null) {
                                    mClickListener.onClick(OnRealButtonClickListener.TYPE_SINGLE_CLICK);
                                }
                            } else {
                                if (ClickMsg.Cmd.voice.equals(clickMsg.cmd)) {
                                    if (ClickMsg.Type.button.equals(clickMsg.type)) {
                                        // Single click event
                                        if (mClickListener != null) {
                                            mClickListener.onClick(OnRealButtonClickListener.TYPE_SINGLE_CLICK);
                                        }

                                    } else {
                                        // Double click event
                                        if (mClickListener != null) {
                                            mClickListener.onClick(OnRealButtonClickListener.TYPE_DOUBLE_CLICK);
                                        }
                                    }

                                }
                            }

                        }
                        try {
                            Thread.sleep(100);
                            receivedResponse = false;
                            tries = 0;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                    ds.close();
                    ds = null;
                } catch (Exception e) {
                    log.error("Error: " + e.getMessage(), e);
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("...Restart listen process ...");
            }
            log.info("...Listen thread end...");
        }).start();
    }

    public void startListen() {
        if (!mListening) {
            log.info("Start listen real button click event.");
            mListening = true;
            initListen();
        }
    }

    public void stopListen() {
        log.info("Stop listen real button click event.");
        mListening = false;
    }

    public void setOnRealButtonClickListener(OnRealButtonClickListener listener) {
        mClickListener = listener;
    }
}


