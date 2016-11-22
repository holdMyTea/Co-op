class Message:

    def __init__(self, msg, source_key):
        self.source_key = source_key
        self.msg = msg
        self.body, self.sent = msg.split(b'-')
        self.code = bytes(chr(self.body[1]), encoding="utf-8")
