KEY_GHOST = 0
KEY_VAMP = 1

PAUSE_MODE = b'1'
PLAY_MODE = b'2'
MOVE_CODE = b'3'

SPEED = 10

PARAMS_CODES = [MOVE_CODE]

ghost_position = {"x": 50, "y": 100}
vamp_position = {"x": 400, "y": 100}


def move_ghost_left():
    print("Before "+str(ghost_position["x"]))
    ghost_position["x"] += (-1 * SPEED)
    print("After " + str(ghost_position["x"]))


def move_ghost_right():
    ghost_position["x"] += SPEED


def move_vamp_left():
    vamp_position["x"] += (-1 * SPEED)


def move_vamp_right():
    vamp_position["x"] += SPEED
