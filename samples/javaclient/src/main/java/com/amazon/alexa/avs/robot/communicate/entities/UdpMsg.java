package com.amazon.alexa.avs.robot.communicate.entities;

public class UdpMsg extends TcpMsg {

    private BroadcastType mBroadcastType = BroadcastType.BROADCAST_TYPE_NORMAL;

    public UdpMsg(byte[] data, TargetInfo target, MsgType type) {
        super(data, target, type);
    }

    public UdpMsg(String data, TargetInfo target, MsgType type) {
        super(data, target, type);
    }

    public UdpMsg(int id) {
        super(id);
    }

    public BroadcastType getBroadcastType() {
        return mBroadcastType;
    }

    public void setBroadcastType(BroadcastType broadcastType) {
        mBroadcastType = broadcastType;
    }

    public enum BroadcastType {
        /**
         * 单播或广播
         */
        BROADCAST_TYPE_NORMAL,

        /**
         * 组播
         */
        BROADCAST_TYPE_MULTICAST
    }

}
