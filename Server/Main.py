import Server

HOST = ""
PORT = 1488

server = Server.Server()

server.bind(HOST,PORT)

server.listen()
