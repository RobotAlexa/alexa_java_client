# coding: utf-8
import json
import socket

ip = '127.0.0.1'
ip_port = (ip, 29599)
server = socket.socket()
server.bind(ip_port)


def is_json(json_str):
    try:
        json.loads(json_str)
    except ValueError:
        print ("error json format")
        return False
    return True


def start():
    print ("Yanshee control server starting.")

    server.listen(5)
    while True:
        print ("Yanshee control server listening.")
        conn, addr = server.accept()
        print ("accept: %s, %s" % (conn, addr))
        try:
            client_data = conn.recv(4096)
            str_data = str(client_data).strip()
            print ("receive message: %s" % str_data)
            if is_json(str_data):
                request = json.loads(str_data)
                print(request)
                # server.sendall("ok")
            # else:
            #     server.sendall("invalid json format.")

            conn.close()

        except Exception, e:
            print ("Error: %s" % e)
            conn.close()

    print ("Socket close.")


if __name__ == "__main__":
    start()
