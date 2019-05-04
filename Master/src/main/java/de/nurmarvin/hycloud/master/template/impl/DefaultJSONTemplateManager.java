package de.nurmarvin.hycloud.master.template.impl;

import com.google.gson.*;
import de.nurmarvin.hycloud.master.Master;
import de.nurmarvin.hycloud.master.template.ServerType;
import de.nurmarvin.hycloud.master.template.Template;
import de.nurmarvin.hycloud.master.template.TemplateManager;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultJSONTemplateManager implements TemplateManager {

    private final Set<Template> templates = new HashSet<>();
    private final File file = new File("configs/templates.json");
    private Master master;
    private JsonObject jsonObject;

    public DefaultJSONTemplateManager(Master master) {
        this.master = master;
        load();
    }

    public void addTemplate(Template template) {
        templates.add(template);
        if (template.file().mkdirs()) {
            master.logger().info("Saved template {}", template);
        }
        save();
    }

    public void removeTemplate(Template template) {
        templates.remove(template);
        if (template.file().delete()) {
            master.logger().info("Removed template {}", template);
        }
        save();
    }

    public Template getTemplateByName(String name) {
        for (Template template : templates)
            if (template.name().equalsIgnoreCase(name))
                return template;
        return null;
    }

    public Set<Template> templates() {
        return templates;
    }

    public void save() {
        try {
            if (!file.exists()) {
                if (!file.getParentFile().mkdirs()) {
                    master.logger().error("Failed to create templates folder in {}", file.getParentFile().getPath());
                }
                if (!file.createNewFile()) {
                    master.logger().error("Failed to create template file in {}", file.getPath());
                }
                try (Writer writer = new FileWriter(file)) {
                    Gson gson = new GsonBuilder().create();
                    gson.toJson(jsonObject, writer);
                }
            }

            jsonObject = new JsonObject();

            JsonArray templateArray = new JsonArray();

            for (Template template : templates) {
                JsonObject templateObject = new JsonObject();

                templateObject.addProperty("name", template.name());
                templateObject.addProperty("minRam", template.minRam());
                templateObject.addProperty("maxRam", template.maxRam());
                templateObject.addProperty("directory", template.file().getPath());
                templateObject.addProperty("serverType", template.serverType().name());

                templateArray.add(templateObject);
            }

            jsonObject.add("templates", templateArray);

            try (Writer writer = new FileWriter(file)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonObject, writer);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void load() {
        if (file.exists()) {
            master.logger().info("Loading Templates. This may take a while...");
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = null;
            try {
                jsonObject = parser.parse(new FileReader(file)).getAsJsonObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.jsonObject = jsonObject;
            JsonArray templatesArray = (JsonArray) jsonObject.get("templates");
            for (JsonElement jsonElement : templatesArray) {
                JsonObject object = jsonElement.getAsJsonObject();
                String name = object.get("name").getAsString();
                int minRam = object.get("minRam").getAsInt();
                int maxRam = object.get("maxRam").getAsInt();
                String path = object.get("directory").getAsString();
                ServerType serverType = ServerType.valueOf(object.get("serverType").getAsString());
                Template template = new Template(name, path, maxRam, minRam, serverType);

                master.logger().info("Adding new template {}", template);

                templates.add(template);
            }
        } else {
            jsonObject = new JsonObject();
            jsonObject.add("templates", new JsonArray());
        }
    }
}
