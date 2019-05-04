package de.nurmarvin.hycloud.master.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.nurmarvin.hycloud.master.Master;

import java.io.*;

/**
 * @author NurMarvin
 */
public class GeneralConfig {

    private final File file = new File("configs/general.json");
    private Master master;
    private String keyToConnect = "";
    private int port = 1337;

    public GeneralConfig(Master master) {
        this.master = master;
        this.setup();
    }

    private void setup() {
        if (!file.exists()) {
            keyToConnect = this.getString("Which key should daemons use to connect to the Master?");
            port = this.getInteger("Which port should the network server run on?");

            //File save
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("keyToConnect", keyToConnect);
            jsonObject.addProperty("port", port);

            if(!file.getParentFile().mkdirs()) {
                master.logger().error("Failed to create configs folder in {}", file.getParentFile().getPath());
            }
            try {
                if(!file.createNewFile()) {
                    master.logger().error("Failed to create config in {}", file.getPath());
                }
                try (Writer writer = new FileWriter(file)) {
                    Gson gson = new GsonBuilder().create();
                    gson.toJson(jsonObject, writer);
                }
                master.logger().info("Successfully created the general config!");
            } catch (IOException ex) {
                master.logger().error("An error occurred while creating the general config", ex);
            }
        } else {
            master.logger().info("Loading general config...");
            try {
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(new FileReader(file)).getAsJsonObject();
                keyToConnect = jsonObject.get("keyToConnect").getAsString();
                port = jsonObject.get("port").getAsInt();
                master.logger().info("Successfully loaded the general config!");
            } catch (IOException ex) {
                master.logger().error("An error occurred while loading the general config", ex);
            }
        }
    }

    private int getInteger(String question) {
        master.logger().info(question + " (Integer/Number)");
        try {
            String line = master.consoleReader().readLine();
            try {
                return Integer.valueOf(line);
            } catch (NumberFormatException exb) {
                return getInteger(question);
            }
        } catch (IOException ex) {
            return -1;
        }
    }

    private String getString(String question) {

        master.logger().info(question + " (String)");
        try {
            return master.consoleReader().readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public String keyToConnect() {
        return keyToConnect;
    }

    public int port() {
        return port;
    }
}
