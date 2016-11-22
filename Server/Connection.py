import time
import sys
import Message
import multiprocessing


class Connection:

    def __init__(self, conn, server, key):
        self.conn = conn
        self.server = server
        self.key = key

    def start(self):
        t = multiprocessing.Process(
            target=self.make_it_read,
            args=(self.conn, self.key, self.server)
        )
        t.start()

    @staticmethod
    def make_it_read(conn, key, server):
        try:

            while True:
                data = conn.recv(1024)
                print("received: "+str(data))
                if data == b'':
                    print("Job's done")
                    sys.exit()
                data = Message.Message(data, key)
                server.spread_msg(data)
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
