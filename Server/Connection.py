import threading
import time
import sys
import Message


class Connection:

    def __init__(self, conn, server, key):
        self.conn = conn
        self.server = server
        self._key = key

    def start(self):
        t = threading.Thread(target=self.make_it_read()).start()

    def make_it_read(self):
        try:

            while True:
                data = self.conn.recv(1024)
                print("received: "+str(data))
                if data == b'':
                    print("Job's done")
                    sys.exit()
                data = Message.Message(data, self._key)
                self.server.spread_msg(data)
                time.sleep(0.1)

        except ConnectionResetError as error:
            print("Connection lost: "+str(error))
            sys.exit()

    def send(self, msg):
        try:
            print("sending: " + str(msg))
            self.conn.sendall(msg)
        except ConnectionResetError as error:
            print("Sending error: " + str(error))

    def get_key(self):
        return self._key
