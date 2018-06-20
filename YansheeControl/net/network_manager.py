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
    server.listen(5)
    need_exit = False
    while True:
        print ("Yanshee control server start.")
        conn, addr = server.accept()
        try:
            while 1:
                client_data = conn.recv(4096)
                str_data = str(client_data)
                if is_json(str_data):
                    request = json.loads(str_data)

                    if cmd_cmd in request and request[cmd_cmd].strip() == cmd_exit:
                        need_exit = True
                    else:
                        cmd_parser(request)

        except Exception, e:
            conn.close()

        if need_exit:
            break
    print ("Socket close.")


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
        if cmd_action in request:
            # action
            action = request[cmd_action]
            repeat = 1
            if key_name in action:
                if key_repeat in action:
                    repeat = action[key_repeat]
                controller.do_action(action[key_name], repeat)

        elif cmd_dance in request:
            # dance
            dance = request[cmd_dance]
            repeat = 1
            if key_name in dance:
                if key_repeat in dance:
                    repeat = dance[key_repeat]
                controller.do_dance(dance[key_name], repeat)

        elif cmd_moving in request:
            # moving
            moving = request[cmd_moving]
            repeat = 1
            if key_name in moving:
                if key_repeat in moving:
                    repeat = moving[key_repeat]
                controller.do_moving(moving[key_name], repeat)

        elif cmd_volume in request:
            # set volume
            volume = request[cmd_volume]
            if key_direction in volume:
                direction = volume[key_direction]
                if direction == direction_up:
                    controller.do_volume_up()
                else:
                    controller.do_volume_down()

        elif cmd_stop in request:
            controller.do_stop()

