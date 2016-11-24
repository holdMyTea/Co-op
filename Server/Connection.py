import Message
import multiprocessing


class Connection:

    def __init__(self, conn, server, key):
        self.conn = conn
        self.server = server
        self.key = key
        self.t = None

    def start(self):

        self.send(b's0:var('+bytes(str(self.key), encoding="utf-8")+b');')

        self.t = multiprocessing.Process(
            target=self.make_it_read,
            args=(self.conn, self.key, self.server)
        )
        # self.t.daemon = True
        self.t.start()

    @staticmethod
    def make_it_read(conn, key, server):
        try:

            while True:
                data = conn.recv(1024)
                print("received: "+str(data))
                if data == b'':
                    print("Job's done")
                    server.exit()
                data = Message.Message(data, key, server.params)
                server.spread_msg(data)

        except ConnectionResetError as error:
            print("Connection lost: "+str(error))
            server.exit()

    def send(self, msg):
        try:
            print("sending: " + str(msg))
            self.conn.sendall(msg)
        except ConnectionResetError as error:
            print("Sending error: " + str(error))

    def stop(self):
        self.t.terminate()