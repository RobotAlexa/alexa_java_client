package com.amazon.alexa.avs.robot.communicate.constants;

/**
 * Created by qinicy on 2017/5/23.
 */

public interface MESSAGE_TYPE {
   int DISCOVERY = 0x01;
   int CONNECT = 0x02;
   int HEARD_BEAT = 0x03;
   int QUERY = 0x04;
   int ACTION = 0x05;
   int STEERING = 0x06;
   int SETTING = 0x07;
   int MONITOR = 0x08;
   int TRANSMIT = 0x09;
   int ROBOT_VOICE = 0x0a;
   int DISCONNECT = 0x0b;
   int TRANSMIT_ACK = 0x0c;
   int PHONE_POSE_ACK = 0x0d;
   int SERVO_OFFSET = 0x0E;
   int SENSOR_CONFIG = 0x0f;
   int VISION = 0x10;
   int LED = 0x11;
}
