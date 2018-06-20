# coding: utf-8
import socket
import json
from core import controller
from json_type import *

ip = '127.0.0.1'
ip_port = (ip, 29599)
server = socket.socket()
server.bind(ip_port)


def is_json(json_str):
    try:
        json.loads(json_str)
    except ValueError:
        return False
    return True


def start():
    print ("Yanshee control server starting.")
    # connect SDK
    controller.connect(ip)

    server.listen(5)
    while True:
        print ("Yanshee control server listening.")
        conn, addr = server.accept()
        try:
            client_data = conn.recv(4096)
            str_data = str(client_data)
            print ("receive message: %s" % str_data)
            if is_json(str_data):
                request = json.loads(str_data)

                if cmd_cmd in request and request[cmd_cmd].strip() == cmd_exit:
                    break
                else:
                    cmd_parser(request)
            conn.close()

        except Exception, e:
            print ("Error: %s" % e)
            conn.close()

    print ("Socket close.")

    # disconnect SDK
    controller.disconnect(ip)

    print ("Yanshee control server stop.")


def cmd_parser(request):
    '''
        {
            'cmd': '', # exit action dance moving stop volume
            'action': {
                'name':'',
                'repeat':''
            },
            'dance': {
                'name':'',
                'repeat':''
            },
            'moving': {
                'name':'',
                'repeat':''
            },
            'volume': {
                'direction':''
            }
        }
    '''
    if cmd_cmd in request:
        if request[cmd_cmd] == cmd_action and obj_action in request:
            # action
            action = request[obj_action]
            repeat = 1
            if key_name in action:
                if key_repeat in action:
                    repeat = action[key_repeat]
                controller.do_action(action[key_name], repeat)

        elif request[cmd_cmd] == cmd_dance and obj_dance in request:
            # dance
            dance = request[obj_dance]
            repeat = 1
            if key_name in dance:
                if key_repeat in dance:
                    repeat = dance[key_repeat]
                controller.do_dance(dance[key_name], repeat)

        elif request[cmd_cmd] == cmd_moving and obj_moving in request:
            # moving
            moving = request[obj_moving]
            repeat = 1
            if key_name in moving:
                if key_repeat in moving:
                    repeat = moving[key_repeat]
                controller.do_moving(moving[key_name], repeat)

        elif request[cmd_cmd] == cmd_volume and obj_volume in request:
            # set volume
            volume = request[obj_volume]
            if key_direction in volume:
                direction = volume[key_direction]
                if direction == direction_up:
                    controller.do_volume_up()
                else:
                    controller.do_volume_down()

        elif request[cmd_cmd] == cmd_stop:
            controller.do_stop()
