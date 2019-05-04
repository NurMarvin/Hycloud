package de.nurmarvin.hycloud.master.template;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Template {
    private final List<Integer> availableIds = new ArrayList<>();
    private String name;
    private File file;
    private String path;
    private int maxRam, minRam;
    private ServerType serverType;
    private int id = 0;

    public Template(String name, String path, int maxRam, int minRam, ServerType serverType) {
        this.name = name;
        this.file = new File(path);
        this.path = path;
        this.maxRam = maxRam;
        this.minRam = minRam;
        this.serverType = serverType;
    }

    public int id() {
        if (!availableIds.isEmpty()) {
            int toReturn = availableIds.get(0);
            availableIds.remove(0);
            return toReturn;
        }
        id++;
        return id;
    }

    public void remove(int id) {
        availableIds.add(id);
    }

    public String name() {
        return name;
    }

    public File file() {
        return file;
    }

    public String path() {
        return path;
    }

    public int maxRam() {
        return maxRam;
    }

    public int minRam() {
        return minRam;
    }

    public ServerType serverType() {
        return serverType;
    }

    @Override
    public String toString() {
        return "Template(name=" + name + ", directory=" + this.file.getPath() + ")";
    }
}
