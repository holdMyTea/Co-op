class Message:

    def __init__(self, msg, source_key, params):
        self.source_key = source_key
        self.msg = msg
        self.params = params

        # default responses
        self.source_response = b'r1;'
        self.other_response = b's1;'

        self.parse()

    def parse(self):
        body, sent = self.msg.split(b'#')
        code = Message.digit_to_byte(body[1])
        if code == self.params.MOVE_CODE:
            self.parse_move()
        elif code == self.params.PAUSE_MODE:
            self.parse_pause()
        elif code == self.params.PLAY_MODE:
            self.parse_play()

    def parse_pause(self):
        self.source_response = b'r1;'
        self.other_response = b's1;'

    def parse_play(self):
        self.source_response = b'r2;'
        self.other_response = b's2;'

    def parse_move(self):
        if self.source_key == self.params.KEY_GHOST:

            if bytes(chr(self.msg[5]), encoding="utf-8") == b'-':
                self.params.move_ghost_left()
            elif bytes(chr(self.msg[5]), encoding="utf-8") == b'+':
                self.params.move_ghost_right()

            self.source_response = b'r3:pos(' \
                                   + Message.int_to_bytes(self.params.ghost_position["x"]) + b');'
            self.other_response = b's3:pos(' \
                                  + Message.int_to_bytes(self.params.ghost_position["x"]) + b');'

        elif self.source_key == self.params.KEY_VAMP:

            if self.msg[self.msg.find(b'(') + 1] == b'-':
                self.params.move_vamp_left()
            elif self.msg[self.msg.find(b'(') + 1] == b'+':
                self.params.move_vamp_right()

            self.source_response = b'r3:pos(' \
                                   + Message.int_to_bytes(self.params.vamp_position["x"]) + b');'
            self.other_response = b's3:pos(' \
                                  + Message.int_to_bytes(self.params.vamp_position["x"]) + b');'

    @staticmethod
    def digit_to_byte(integer):
        return bytes(chr(integer), encoding="utf-8")

    @staticmethod
    def int_to_bytes(int):
        return bytes(str(int), encoding="utf-8")
