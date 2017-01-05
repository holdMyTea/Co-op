package com.forsenboyz.rise42.server.state;

public class Sizes {

    static final int BACKGROUND_WIDTH = 2048;
    static final int BACKGROUND_HEIGHT = 2048;

    static final int HERO_WIDTH = 64;
    static final int HERO_HEIGHT = 64;

    // don't i ever need this
    static final int HERO_BUFFER = (int) Math.sqrt(
            HERO_WIDTH * HERO_WIDTH + HERO_HEIGHT * HERO_HEIGHT
    ) - HERO_WIDTH;

    static final int HERO_MIN_X = 0 + HERO_BUFFER;
    static final int HERO_MAX_X = BACKGROUND_WIDTH - HERO_WIDTH - HERO_BUFFER;

    static final int HERO_MIN_Y = 0 + HERO_BUFFER;
    static final int HERO_MAX_Y = BACKGROUND_HEIGHT - HERO_HEIGHT - HERO_BUFFER;

}
