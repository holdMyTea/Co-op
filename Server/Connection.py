import threading
import time
import sys


class Connection:

    def __init__(self, conn):
        self.conn = conn
        t = threading.Thread(target=self.make_it_read()).start()

    def make_it_read(self):
        try:
            while True:
                data = self.conn.recv(1024)
                print("received: "+str(data))
                if data:
                    print("sending: "+str(data))
                    self.conn.sendall(b'a'+data)
                time.sleep(0.1)
        except ConnectionResetError as error:
            print("Connection lost: "+str(error))
            sys.exit()

    def send(self, msg):
        try:
            self.conn.sendall(b'm'+msg)
        except ConnectionResetError as error:
            print("Sending error: " + str(error))
