package com.amazon.alexa.avs.robot.communicate.constants;

public interface PROCOTOL_CMDS {
    String DISCOVERY = "discovery";
    String DISCOVERY_ACK = "discovery_ack";
    String CONNECT = "connect";
    String CONNECT_ACK = "connect_ack";
    String HEARTBEAT = "heartbeat";
    String HEARTBEAT_ACK = "heartbeat_ack";
    String QUERY = "query";
    String QUERY_ACK = "query_ack";
    String ACTION = "action";
    String ACTION_ACK = "action_ack";
    String SERVO = "servo";
    String SERVO_ACK = "servo_ack";
    String SETTING = "set";
    String SETTING_ACK = "set_ack";
    String MONITOR = "monitor";
    String MONITOR_ACK = "monitor_ack";
    String TRANSMIT = "transmit";
    String TRANSMIT_ACK = "transmit_ack";
    String DOWNLOAD = "download";
    String LINK_WIFI = "link_wifi";
    String LINK_WIFI_ACK = "link_wifi_ack";
    String REPORT_ACTION_ACK = "report_action";
    String REPORT_BLOCKLY_ACK = "report_blockly";
    String VOICE = "voice";
    String VOICE_ACK = "voice_ack";
    String DISCONNECT = "disconnect";
    String DISCONNECT_ACK = "disconnect_ack";
    String QUERY_PHONE_POSE = "query_app";
    String QUERY_PHONE_POSE_ACK = "query_app_ack";
    String SERVO_OFFSET = "servo_offset";
    String SERVO_OFFSET_ACK = "servo_offset_ack";
    String REMOTE_INFO = "remote_info";
    /**
     * 奇葩的命令，在首次配网时使用
     */
    String WIFI = "wifi";
    String REPORT_INFO = "report_info";
    String SENSOR_CONFIG = "sensor_config";
    String SENSOR_CONFIG_ACK = "sensor_config_ack";

    String VISION = "vision";
    String VISION_ACK = "vision_ack";
    String UPGRADE = "upgrade";
}
