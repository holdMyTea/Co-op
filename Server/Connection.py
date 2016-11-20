import threading
import time
import sys


class Connection:

    def __init__(self, conn):
        self.conn = conn
        self.last_millis = 0
        t = threading.Thread(target=self.make_it_read()).start()

    def make_it_read(self):
        try:
            while True:
                data = self.conn.recv(1024)
                print("received: "+str(data))
                if data == b'':
                    print("Job's done")
                    sys.exit()
                elif data:
                    self.ping(data)
                    self.send(data)
                    time.sleep(0.1)
        except ConnectionResetError as error:
            print("Connection lost: "+str(error))
            sys.exit()

    def send(self, msg):
        try:
            m = msg.split(b'-')[0]
            print("sending: " + str(m))
            self.conn.sendall(b'r'+m)
        except ConnectionResetError as error:
            print("Sending error: " + str(error))

    def ping(self, msg):
        millis = abs(int(msg.split(b'-')[1].split(b';')[0]))
        print("ping: "+str(millis))
        self.last_millis = millis
