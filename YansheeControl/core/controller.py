# -*- coding: utf-8 -*-

import RobotApi
import random

dance_list = ["Waka_waka", "Happy_Birthday", "Sweet_and_sour", "we_are_taking_off"]
move_list = ["Forward", "Backward", "Leftward", "Rightward"]

max_volume = 100


def connect(ip):
    RobotApi.ubtRobotInitialize()
    return RobotApi.ubtRobotConnect("SDK", "1", ip)


def disconnect(ip):
    RobotApi.ubtRobotDisconnect("SDK", "1", ip)
    RobotApi.ubtRobotDeinitialize()


def do_action(ac_name, repeat):
    ac_name = str(ac_name)
    return RobotApi.ubtStartRobotAction(ac_name, repeat)


def do_dance(ac_name="", repeat=1):
    ac_name = str(ac_name)
    if ac_name == "":
        ac_name = dance_list[random.randint(0, len(dance_list) - 1)]

    if ac_name in dance_list:
        return RobotApi.ubtStartRobotAction(ac_name, repeat)
    else:
        print ("Can't found the dance.")
        return -1


def do_moving(ac_name, repeat=1):
    ac_name = str(ac_name)
    if ac_name in move_list:
        return RobotApi.ubtStartRobotAction(ac_name, repeat)
    else:
        print ("Can't moving.")
        return -1


def do_stop():
    return RobotApi.ubtStopRobotAction()


def do_volume_up():
    status_type = RobotApi.UBTEDU_ROBOT_STATUS_TYPE_VOLUME
    status_info = RobotApi.UBTEDU_ROBOTINFRARED_SENSOR_T()
    ret = RobotApi.ubtGetRobotStatus(status_type, status_info)
    if ret != 0:
        print ("Can't get volume.")
        return ret
    print "volume: %s"%status_info.iValue
    value = status_info.iValue + max_volume / 5
    if value > max_volume:
        value = max_volume
    print "value: %s"%value
    return RobotApi.ubtSetRobotVolume(value)


def do_volume_down():
    status_type = RobotApi.UBTEDU_ROBOT_STATUS_TYPE_VOLUME
    status_info = RobotApi.UBTEDU_ROBOTINFRARED_SENSOR_T()
    ret = RobotApi.ubtGetRobotStatus(status_type, status_info)
    if ret != 0:
        print ("Can't get volume.")
        return ret
    print "volume: %s"%status_info.iValue

    value = status_info.iValue - max_volume / 5
    if value < 0:
        value = 0
    print "value: %s"%value
    return RobotApi.ubtSetRobotVolume(value)


def do_face_track(isStart):
    # TODO
    pass


def do_face_analyze():
    # TODO
    pass


def do_gesture_analyze():
    # TODO
    pass


def do_object_analyze():
    # TODO
    pass


