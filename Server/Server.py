import socket
import sys
import Connection

HOST = ""
PORT = 1488

connections = []

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
    server.bind((HOST,PORT))
    print("Bounded")
except socket.error as msg:
    print("Didn't bind "+str(msg[1]))
    sys.exit()

server.listen()
print("Listening")

while True:
    conn, addr = server.accept()
    print("Connected with "+addr[0]+":"+str(addr[1]))
    connections.append(Connection.Connection(conn))

server.close()
