package com.forsenboyz.rise42.server.parser;

import com.forsenboyz.rise42.server.objects.characters.Character;
import com.forsenboyz.rise42.server.objects.Object;
import com.forsenboyz.rise42.server.objects.Type;
import com.forsenboyz.rise42.server.objects.characters.Hero;
import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ConfigParser {

    private static String configFile;

    private static String readFile() {
        return readFile("/home/rise42/Projects/Co-op/javaserv/src/main/resources/config.json");
    }

    private static String readFile(String configPath) {
        if (configFile == null) {
            configFile = "";
            try {

                configFile = Files.lines(Paths.get(configPath))
                        .reduce((s1, s2) -> s1 + s2)
                        .orElse("");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return configFile;
    }

    public static ArrayList<Object> getWallBlocks() {

        JsonObject staticObjects = new JsonParser()
                .parse(readFile())
                .getAsJsonObject()
                .get("static").getAsJsonObject();

        ArrayList<Object> wallBlocks = new ArrayList<>();

        for (JsonElement element : staticObjects.getAsJsonArray("blocks")) {
            wallBlocks.add(
                    new Object(
                            Type.Wall,
                            element.getAsJsonObject().get("x").getAsFloat(),
                            element.getAsJsonObject().get("x2").getAsFloat(),
                            element.getAsJsonObject().get("y").getAsFloat(),
                            element.getAsJsonObject().get("y2").getAsFloat()
                    )
            );
        }

        // sorting by x for easier pathmaking
        wallBlocks.sort(
                (block1, block2) ->
                        (int) (block1.getX() - block2.getX())
        );

        //wallBlocks.forEach(System.out::println);

        return wallBlocks;
    }

    public static Object[] getBorderBlocks() {
        JsonObject staticObjects = new JsonParser()
                .parse(readFile())
                .getAsJsonObject()
                .get("static").getAsJsonObject();

        Object[] borderBlocks = new Object[4];
        JsonArray jsonArray = staticObjects.getAsJsonArray("border");

        for (int i = 0; i < 4; i++) {
            borderBlocks[i] = new Object(
                    Type.Wall,
                    jsonArray.get(i).getAsJsonObject().get("x").getAsFloat(),
                    jsonArray.get(i).getAsJsonObject().get("x2").getAsFloat(),
                    jsonArray.get(i).getAsJsonObject().get("y").getAsFloat(),
                    jsonArray.get(i).getAsJsonObject().get("y2").getAsFloat()
            );
        }

        return borderBlocks;
    }

    public static Hero getMage() {
        JsonObject object = new JsonParser()
                .parse(readFile())
                .getAsJsonObject()
                .get("heroes")
                .getAsJsonObject()
                .get("mage")
                .getAsJsonObject();

        return new Hero(
                object.get("moveSpeed").getAsInt(),
                object.get("maxHP").getAsInt(),
                object.get("x").getAsFloat(),
                object.get("y").getAsFloat(),
                object.get("angle").getAsInt(),
                object.get("width").getAsInt(),
                object.get("height").getAsInt()
        );
    }

    public static Hero getWar() {
        JsonObject object = new JsonParser()
                .parse(readFile())
                .getAsJsonObject()
                .get("heroes")
                .getAsJsonObject()
                .get("war")
                .getAsJsonObject();

        return new Hero(
                object.get("moveSpeed").getAsInt(),
                object.get("maxHP").getAsInt(),
                object.get("x").getAsFloat(),
                object.get("y").getAsFloat(),
                object.get("angle").getAsInt(),
                object.get("width").getAsInt(),
                object.get("height").getAsInt()
        );
    }

    public static Coordinates[] getSpawns() {
        JsonArray spawns = new JsonParser()
                .parse(readFile())
                .getAsJsonObject()
                .get("spawns")
                .getAsJsonArray();

        Coordinates[] coordinates = new Coordinates[spawns.size()];
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = new Coordinates(
                    spawns.get(i).getAsJsonObject().get("x").getAsInt(),
                    spawns.get(i).getAsJsonObject().get("y").getAsInt()
            );
        }
        return coordinates;
    }

    public static int getBackgroundSize() {

        return new JsonParser()
                .parse(readFile())
                .getAsJsonObject()
                .get("back")
                .getAsJsonObject()
                .get("w")
                .getAsInt();
    }

}
