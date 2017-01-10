package com.forsenboyz.rise42.coop.config;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.forsenboyz.rise42.coop.objects.Object;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


// TODO; temporary stuff, hardcode sometime
public class ConfigParser {

    public static ArrayList<Object> getBlocks() {
        String configFile = "";
        try {

            configFile = Files.lines(Paths.get("/home/rise42/Projects/Co-op/javaserv/src/main/resources/config.json"))
                    .reduce((s1, s2) -> s1 + s2)
                    .orElse("");

            System.out.println(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonObject staticObjects = new JsonParser()
                .parse(configFile)
                .getAsJsonObject()
                .get("static").getAsJsonObject();

        ArrayList<Object> wallBlocks = new ArrayList<>();

        for (JsonElement element : staticObjects.getAsJsonArray("blocks")) {
            int x = element.getAsJsonObject().get("x").getAsInt();
            int x2 = element.getAsJsonObject().get("x2").getAsInt();
            int y = element.getAsJsonObject().get("y").getAsInt();
            int y2 = element.getAsJsonObject().get("y2").getAsInt();
            wallBlocks.add(
                    new Object(
                            new TextureRegion(new Texture("block.png"), x2-x,y2-y),
                            x,
                            y
                    )
            );
        }
        return wallBlocks;
    }
}
