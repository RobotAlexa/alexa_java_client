# coding:utf-8

import socket
import json

move = {}
move["cmd"] = "action"

action = {}
action["name"] = "Forward"
action["repeat"] = 1

move["action"] = action

if __name__ == "__main__":
    ip_port = ('127.0.0.1', 29599)
    sk = socket.socket()
    sk.connect(ip_port)
    sk.sendall(bytes(json.dumps(move)))
    sk.close()
