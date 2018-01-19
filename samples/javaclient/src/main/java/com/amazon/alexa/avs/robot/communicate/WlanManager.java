package com.amazon.alexa.avs.robot.communicate;

import com.amazon.alexa.avs.log.ConsoleLogger;
import com.amazon.alexa.avs.robot.communicate.constants.*;
import com.amazon.alexa.avs.robot.communicate.entities.RobotStateException;
import com.amazon.alexa.avs.robot.communicate.listener.RobotActionControlListener;
import com.amazon.alexa.avs.robot.communicate.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

public class WlanManager {
    private WlanDelegate mWlanDelegate;
    private List<RobotActionControlListener> mRobotActionControlListeners;
    private RobotListener mRobotListener;

    private WlanManager() {
        if (mWlanDelegate == null) {
            mWlanDelegate = new WlanDelegate();
        }
        initListeners();
    }

    private static class SingletonHolder {
        private final static WlanManager instance = new WlanManager();
    }

    public static WlanManager getInstance() {
        return SingletonHolder.instance;
    }

    private void initListeners() {
        mRobotActionControlListeners = new ArrayList<>();

        mRobotListener = new RobotListener();
        mWlanDelegate.setRobotActionControlListener(mRobotListener);
    }

    /**
     * 动作控制
     *
     * @param type       类型，参看：{@link PLAY_CONTROL_TYPES}
     * @param actionName 基本动作名
     * @param repeat     0：一直重复;其它：重复次数
     */
    public void actionControl(String type, String actionName, int repeat) {
        mWlanDelegate.actionControl(type, actionName, repeat);
    }

    public void volumeUp() {
        mWlanDelegate.volumeUp();
    }

    public void volumeDown() {
        mWlanDelegate.volumeDown();
    }

    public void volumeSet(String type, int volume) {
        mWlanDelegate.voiceSetting(type, volume);
    }

    /**
     * 人脸识别、手势识别、人脸跟踪等功能
     *
     * @param type 类型，see {@link FACE_RECOGNITION_TYPE}
     * @param para 参数，see {@link FACE_RECOGNITION_PARA}
     */
    public void faceTracking(String type, String para) {
        mWlanDelegate.faceTracking(type, para);
    }


    /**
     * 设置机器人led
     *
     * @param type  see {@link LED_TYPE}
     * @param mode  see {@link LED_MODE}
     * @param color see {@link LED_COLOR}
     */
    public void setRobotLed(String type, String mode, String color) {
        ConsoleLogger.print("WlanManager", type + ", " + mode + ", " + color);
        mWlanDelegate.setRobotLed(type, mode, color);
    }

    /**
     * 让机器人升级系统软件
     */
    public void upgradeRobotSystem() {
        mWlanDelegate.upgradeRobotSystem();
    }

    public void addRobotActionControlListener(RobotActionControlListener listener) {
        addListener(mRobotActionControlListeners, listener);
    }

    public void removeRobotActionControlListener(RobotActionControlListener listener) {
        removeListener(mRobotActionControlListeners, listener);
    }

    private <T> void addListener(List<T> listeners, T listener) {
        ListUtil.add(listeners, listener);
    }

    private <T> void removeListener(List<T> listeners, T listener) {
        ListUtil.remove(listeners, listener);
    }

    private class RobotListener implements RobotActionControlListener {

        @Override
        public void onActionControlSuccess() {
            if (mRobotActionControlListeners != null) {
                for (RobotActionControlListener mRobotActionControlListener : mRobotActionControlListeners) {
                    mRobotActionControlListener.onActionControlSuccess();
                }
            }
        }

        @Override
        public void onActionControlFail(RobotStateException e) {
            if (mRobotActionControlListeners != null) {
                for (RobotActionControlListener mRobotActionControlListener : mRobotActionControlListeners) {
                    mRobotActionControlListener.onActionControlFail(e);
                }
            }
        }
    }
}
