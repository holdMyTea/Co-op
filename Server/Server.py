import socket
import sys
import Connection
import Params


class Server:
    def __init__(self):
        self.server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.connections = []
        self.params = Params

    def bind(self, host, port):
        try:
            self.server.bind((host, port))
            print("Bounded")
        except socket.error as msg:
            print("Didn't bind " + str(msg[1]))
            sys.exit()

    def listen(self):
        self.server.listen()

        while True:
            if True:
                print("Listening")
                conn, addr = self.server.accept()
                print("Connected with " + addr[0] + ":" + str(addr[1]))
                new_con = Connection.Connection(conn, self, len(self.connections))
                self.connections.append(new_con)
                print("Connections amount: " + str(len(self.connections)))
                new_con.start()

    def spread_msg(self, msg):
        print("spreading msg from " + str(msg.source_key) + ": " + str(msg.source_response))
        for con in self.connections:
            if con.key == msg.source_key:
                con.send(msg.source_response)
            else:
                con.send(msg.other_response)

    def exit(self):
        self.server.close()
        sys.exit()
