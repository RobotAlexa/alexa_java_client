package com.amazon.alexa.avs;

import com.amazon.alexa.avs.auth.AuthSetup;
import com.amazon.alexa.avs.auth.companionservice.RegCodeDisplayHandler;
import com.amazon.alexa.avs.config.DeviceConfig;
import com.amazon.alexa.avs.listener.RequestListener;
import com.amazon.alexa.avs.realbutton.OnRealButtonClickListener;
import com.amazon.alexa.avs.realbutton.RealButtonUdpClient;
import com.amazon.alexa.avs.robot.communicate.WlanManager;
import com.amazon.alexa.avs.robot.communicate.constants.LED_COLOR;
import com.amazon.alexa.avs.robot.communicate.constants.LED_MODE;
import com.amazon.alexa.avs.robot.communicate.constants.LED_TYPE;
import com.amazon.alexa.avs.ui.*;
import com.amazon.alexa.avs.ui.controllers.ListenViewController;
import com.amazon.alexa.avs.ui.headless.*;

import java.util.Scanner;
import java.util.concurrent.*;

import static com.amazon.alexa.avs.ui.controllers.DeviceNameViewController.DEVICE_LABEL;
import static com.amazon.alexa.avs.ui.controllers.DeviceNameViewController.DSN_LABEL;

public class AlexaClient implements ListenUIHandler {

    private RealButtonUdpClient realButtonUdpClient = RealButtonUdpClient.getInstance();
    private AuthSetup authSetup;
    private AVSController controller;
    private DeviceConfig config;
    private ListenViewController listenViewController;

    private LocaleUIHandler localeView;
    private CardUIHandler cardView;
    private AccessTokenUIHandler bearerTokenView;
    private NotificationsUIHandler notificationsView;
    private ListenUIHandler listenView;
    private PlaybackControlsUIHandler playbackControlsView;
    private UserSpeechVisualizerUIHandler visualizerView;
    private LoginLogoutUIHandler loginLogoutView;
    private BlockingQueue<String> userInputs;
    private RegCodeDisplayHandler regCodeDisplayHandler;

    public AlexaClient(AVSController ctrl, AuthSetup as, DeviceConfig cfg) {
        controller = ctrl;
        authSetup = as;
        config = cfg;

        initUserInputs();
        initHandlers();
        addListeners();

        listenViewController =
                new ListenViewController(visualizerView, controller, new SpeechRequestListener());

        realButtonUdpClient.setOnRealButtonClickListener(type -> {
            switch (type) {
                case OnRealButtonClickListener.TYPE_SINGLE_CLICK:
                    // 单击
                    listenButtonPressed();
                    break;

                case OnRealButtonClickListener.TYPE_DOUBLE_CLICK:
                    // 双击
                    break;

                case OnRealButtonClickListener.TYPE_LONG_CLICK:
                    // 长按（关机了）
                    break;
            }
        });

    }

    private void initUserInputs() {
        Executor userEventExecutor = Executors.newFixedThreadPool(1);
        userEventExecutor.execute(this::readUserInput);
    }

    private void readUserInput() {
        Scanner scanner = new Scanner(System.in);
        String readString = scanner.nextLine();
        while (readString != null) {
            if (readString.isEmpty()) {
                // Enter key was pressed
                userInputs.add("enter");
            } else {
                userInputs.add(readString);
            }

            if (scanner.hasNextLine()) {
                readString = scanner.nextLine();
            } else {
                readString = null;
            }
        }
    }

    private void initHandlers() {
        bearerTokenView = new HeadlessAccessTokenView();
        notificationsView = new HeadlessNotificationsView();
        visualizerView = new HeadlessUserSpeechVisualizerView();
        localeView = new HeadlessLocaleView(config, controller);
        listenView = new HeadlessListenView(visualizerView, controller);
        playbackControlsView = new HeadlessPlaybackControlsView(controller);

        userInputs = new LinkedBlockingQueue<>();
        regCodeDisplayHandler =
                new RegCodeDisplayHandler(new HeadlessDialogFactory(userInputs), config);
        loginLogoutView = new HeadlessLoginLogoutView(config, controller, authSetup,
                regCodeDisplayHandler);

        System.out.println(DEVICE_LABEL + config.getProductId() + ", " + DSN_LABEL + config.getDsn());
    }

    private void addListeners() {
        listenView.addSpeechStateChangeListener(playbackControlsView);
        listenView.addSpeechStateChangeListener(visualizerView);
        listenView.addSpeechStateChangeListener(cardView);
        authSetup.addAccessTokenListener(listenView);
        authSetup.addAccessTokenListener(playbackControlsView);
        authSetup.addAccessTokenListener(loginLogoutView);
        authSetup.addAccessTokenListener(bearerTokenView);
        authSetup.addAccessTokenListener(controller);
    }

    public void startAuthentication() {
        authSetup.addAccessTokenListener(this);
        authSetup.startProvisioningThread(regCodeDisplayHandler);
    }

    @Override
    public void onAccessTokenReceived(String accessToken) {
        realButtonUdpClient.startListen();
    }

    @Override
    public void onAccessTokenRevoked() {
        realButtonUdpClient.stopListen();
    }

    @Override
    public void onProcessing() {
        WlanManager.getInstance().setRobotLed(LED_TYPE.BUTTON, LED_MODE.OFF, LED_COLOR.GREEN);
        WlanManager.getInstance().setRobotLed(LED_TYPE.MIC, LED_MODE.OFF, LED_COLOR.GREEN);
        listenViewController.onProcessing();
    }

    @Override
    public void onListening() {
        listenViewController.onListening();
    }

    @Override
    public void onProcessingFinished() {
        listenViewController.onProcessingFinished();
    }

    @Override
    public void listenButtonPressed() {
        listenViewController.listenButtonPressed();
    }

    @Override
    public void addSpeechStateChangeListener(SpeechStateChangeListener listener) {
        listenViewController.addSpeechStateChangeListener(listener);
    }

    @Override
    public void onExpectSpeechDirective() {
        listenViewController.onExpectSpeechDirective();
    }

    @Override
    public void onStopCaptureDirective() {
        listenViewController.onStopCaptureDirective();
    }

    @Override
    public void onWakeWordDetected() {
        listenViewController.onWakeWordDetected();
    }

    private static class SpeechRequestListener extends RequestListener {

        @Override
        public void onRequestFinished() {
        }

        @Override
        public void onRequestError(Throwable e) {
            WlanManager.getInstance().setRobotLed(LED_TYPE.BUTTON, LED_MODE.OFF, LED_COLOR.GREEN);
            WlanManager.getInstance().setRobotLed(LED_TYPE.MIC, LED_MODE.OFF, LED_COLOR.GREEN);
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

}
