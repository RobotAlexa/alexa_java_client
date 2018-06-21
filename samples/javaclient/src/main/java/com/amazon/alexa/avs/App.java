/**
 * Copyright 2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Amazon Software License (the "License"). You may not use this file
 * except in compliance with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/asl/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.amazon.alexa.avs;

import com.amazon.alexa.avs.auth.AuthSetup;
import com.amazon.alexa.avs.config.DeviceConfig;
import com.amazon.alexa.avs.config.DeviceConfigUtils;
import com.amazon.alexa.avs.http.AVSClientFactory;
import com.amazon.alexa.avs.robot.communicate.SocketClient;
import com.amazon.alexa.avs.ui.graphical.GraphicalUI;
import com.amazon.alexa.avs.ui.headless.HeadlessUI;
import com.amazon.alexa.avs.ui.BaseUI;
import com.amazon.alexa.avs.wakeword.WakeWordIPCFactory;
import org.apache.http.util.TextUtils;

public class App {

    private AVSController controller;
    private AuthSetup authSetup;
    private BaseUI appUI;
    private AlexaClient alexaClient;
    public static boolean isBooted = false;

    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            new App(args[0]);
        } else {
            new App();
        }

        try {
            Runtime.getRuntime().exec("sudo service ubtedu stop");

            String serverPath = System.getenv("YANSHEE_CONTROL");
            System.out.println("env: " + serverPath);
            if (!TextUtils.isEmpty(serverPath)) {
            	String[] cmd = { "/bin/bash", "-c", "cd $YANSHEE_CONTROL; bash run.sh" }; 
                Runtime.getRuntime().exec(cmd);
                System.out.println(cmd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public App() throws Exception {
        this(DeviceConfigUtils.readConfigFile());
    }

    public App(String configName) throws Exception {
        this(DeviceConfigUtils.readConfigFile(configName));
    }

    public App(DeviceConfig config) throws Exception {
        // Start tts server
//        AWSPollyTTS awsPollyTTS = new AWSPollyTTS();
//        TTSUdpServer ttsUdpServer = TTSUdpServer.getInstance();
//        ttsUdpServer.setOnUdpReceivedListener((socket, packet, jsonText) -> {
//            // receive tts text
//            try {
//                TTSBean tts = GsonUtil.get().toObject(jsonText, TTSBean.class);
//                awsPollyTTS.synthesize(tts.text);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        ttsUdpServer.startListen();

        authSetup = new AuthSetup(config);
        controller =
                new AVSController(new AVSAudioPlayerFactory(), new AlertManagerFactory(),
                        getAVSClientFactory(config), DialogRequestIdAuthority.getInstance(),
                        new WakeWordIPCFactory(), config);

        if (config.getUiEnabled()) {
            if (config.getHeadlessModeEnabled()) {
                // 非GUI界面模式，需要在config.json中配置
                appUI = new HeadlessUI(controller, authSetup, config);

            } else {
                // GUI界面模式
                appUI = new GraphicalUI(controller, authSetup, config);
            }

        } else {
            alexaClient = new AlexaClient(controller, authSetup, config);
            alexaClient.startAuthentication();
        }
        config.setApp(this);
    }

    protected AVSClientFactory getAVSClientFactory(DeviceConfig config) {
        return new AVSClientFactory(config);
    }

    protected AVSController getController() {
        return controller;
    }

    public void replaceAVSController(DeviceConfig config) throws Exception {
        controller =
                new AVSController(new AVSAudioPlayerFactory(), new AlertManagerFactory(),
                        getAVSClientFactory(config), DialogRequestIdAuthority.getInstance(),
                        new WakeWordIPCFactory(), config);
        appUI.replaceController(controller);
    }
}
