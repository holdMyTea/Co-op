package com.forsenboyz.rise42.server.parser;

import com.forsenboyz.rise42.server.objects.Hero;
import com.forsenboyz.rise42.server.objects.WallBlock;
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

                System.out.println(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return configFile;
    }


    public static int getBorderThick() {
        return new JsonParser().parse(readFile()).getAsJsonObject()
                .get("static").getAsJsonObject().get("border").getAsInt();
    }

    public static ArrayList<WallBlock> getWallBlocks() {

        JsonObject staticObjects = new JsonParser()
                .parse(readFile())
                .getAsJsonObject()
                .get("static").getAsJsonObject();

        ArrayList<WallBlock> wallBlocks = new ArrayList<>();

        //Gson gson = new Gson();
        for (JsonElement element : staticObjects.getAsJsonArray("blocks")) {
            wallBlocks.add(
                    new WallBlock(
                            element.getAsJsonObject().get("x").getAsInt(),
                            element.getAsJsonObject().get("x2").getAsInt(),
                            element.getAsJsonObject().get("y").getAsInt(),
                            element.getAsJsonObject().get("y2").getAsInt()
                    )
            );
        }
        return wallBlocks;
    }

    public static Hero getMage() {

        return new Gson().fromJson(
                new JsonParser()
                        .parse(readFile())
                        .getAsJsonObject()
                        .get("heroes")
                        .getAsJsonObject()
                        .get("mage")
                        .getAsJsonObject(),
                Hero.class
        );
    }

    public static Hero getWar() {

        return new Gson().fromJson(
                new JsonParser()
                        .parse(readFile())
                        .getAsJsonObject()
                        .get("heroes")
                        .getAsJsonObject()
                        .get("war")
                        .getAsJsonObject(),
                Hero.class
        );
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
