import socket
import sys
import Connection


class Server:

    def __init__(self):
        self.server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.connections = []

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
                print("Connections amount: "+str(len(self.connections)))
                new_con.start()
        self.server.close()

    def spread_msg(self, msg, others_msg=None):
        print("spreading msg from "+str(msg.source_key)+": "+str(msg.msg))
        if others_msg is not None:
            pass
        else:
            # print("Right branch "+str(len(self.connections)))
            for con in self.connections:
                if con.key != msg.source_key:
                    con.send(b's' + msg.code + b';')
                else:
                    con.send(b'r' + msg.code + b';')
