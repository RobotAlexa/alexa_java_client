package com.amazon.alexa.avs.robot.communicate.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class TcpMsg implements Serializable {
    private static final long serialVersionUID = 3477600961189987071L;

    public enum MsgType {
        SendMsg, Receive,SendFile
    }

    private static final AtomicInteger IDAtomic = new AtomicInteger();
    private int id;
    private byte[] sourceDataBytes;//数据源
    private String sourceDataString;//数据源

    private TargetInfo target;
    private long time;//发送、接受消息的时间戳
    /**
     * 消息的类型，从收发的角度定义的。
     */
    private MsgType mMsgType = MsgType.SendMsg;
    private byte[][] endDecodeData;
    /**
     * 消息的类型，指某一类型的消息，用于外部标识
     */
    private int mType;

    public Error getError() {
        return mError;
    }

    public void setError(Error error) {
        mError = error;
    }

    private Error mError;

    public String getFilePath() {
        return mFilePath;
    }

    public void setFilePath(String filePath) {
        mFilePath = filePath;
    }

    private String mFilePath;

    public double getSendProgress() {
        return mSendProgress;
    }

    public double getFileTotalBytes() {
        return mFileTotalBytes;
    }

    public void setFileTotalBytes(double fileTotalBytes) {
        mFileTotalBytes = fileTotalBytes;
    }

    public double getFileSendBytes() {
        return mFileSendBytes;
    }

    public void setFileSendBytes(double fileSendBytes) {
        mFileSendBytes = fileSendBytes;
    }

    public void setSendProgress(double sendProgress) {
        mSendProgress = sendProgress;
    }

    private double mSendProgress;
    private double mFileTotalBytes;
    private double mFileSendBytes;



    public TcpMsg(){}
    public TcpMsg(Error error){
        this.mError = error;
    }
    public TcpMsg(int id) {
        this.id = id;
    }

    public TcpMsg(byte[] data, TargetInfo target, MsgType type) {
        this.sourceDataBytes = data;
        this.target = target;
        this.mMsgType = type;
        init();
    }

    public TcpMsg(String data, TargetInfo target, MsgType type) {
        this.target = target;
        this.sourceDataString = data;
        this.mMsgType = type;
        init();
    }

    public void setTime() {
        time = System.currentTimeMillis();
    }

    private void init() {
        id = IDAtomic.getAndIncrement();
    }

    public long getTime() {
        return time;
    }

    public byte[][] getEndDecodeData() {
        return endDecodeData;
    }

    public void setEndDecodeData(byte[][] endDecodeData) {
        this.endDecodeData = endDecodeData;
    }

    public MsgType getMsgType() {
        return mMsgType;
    }

    public void setMsgType(MsgType msgType) {
        mMsgType = msgType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TcpMsg tcpMsg = (TcpMsg) o;
        return id == tcpMsg.id;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (endDecodeData != null) {
            for (byte[] bs : endDecodeData) {
                sb.append(Arrays.toString(bs));
            }
        }
        return this.getClass().getSimpleName()+"{" +
                "sourceDataBytes=" + Arrays.toString(sourceDataBytes) +
                ", id=" + id +
                ", sourceDataString='" + sourceDataString + '\'' +
                ", target=" + target +
                ", time=" + time +
                ", msgType=" + mMsgType +
                ", enddecode=" + sb.toString() +
                '}';
    }

    @Override
    public int hashCode() {
        return id;
    }


    public byte[] getSourceDataBytes() {
        return sourceDataBytes;
    }

    public void setSourceDataBytes(byte[] sourceDataBytes) {
        this.sourceDataBytes = sourceDataBytes;
    }

    public String getSourceDataString() {
        return sourceDataString;
    }

    public void setSourceDataString(String sourceDataString) {
        this.sourceDataString = sourceDataString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public static AtomicInteger getIDAtomic() {
        return IDAtomic;
    }

    public TargetInfo getTarget() {
        return target;
    }

    public void setTarget(TargetInfo target) {
        this.target = target;
    }
}