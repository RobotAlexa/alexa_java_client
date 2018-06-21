# -*- coding: utf-8 -*-

from net import network_manager
import signal
import os


def bye_bye(signum, frame):
    print("\nYanshee control server exit.\n")
    exit()


def main():
    network_manager.start()


if __name__ == "__main__":
    signal.signal(signal.SIGINT, bye_bye)
    signal.signal(signal.SIGTERM, bye_bye)
    signal.signal(signal.SIGABRT, bye_bye)
    signal.signal(signal.SIGILL, bye_bye)
    main()
