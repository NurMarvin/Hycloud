package de.nurmarvin.hycloud.master.server;

import de.nurmarvin.hycloud.master.daemon.Daemon;
import de.nurmarvin.hycloud.master.template.Template;
import io.netty.channel.ChannelHandlerContext;

public class Server {
    private Template template;
    private int id;
    private Daemon daemon;
    private ChannelHandlerContext ctx = null;
    private Screen screen;

    public Server(Template template, int id, Daemon daemon) {
        this.template = template;
        this.id = id;
        this.daemon = daemon;
    }

    public boolean connected() {
        return ctx != null;
    }

    public String name() {
        return this.template.name() + "-" + this.id;
    }

    public Daemon daemon() {
        return daemon;
    }

    public Template template() {
        return template;
    }

    public int id() {
        return id;
    }

    public ChannelHandlerContext ctx() {
        return ctx;
    }

    public Server ctx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        return this;
    }

    public Screen screen() {
        return screen;
    }

    public Server screen(Screen screen) {
        this.screen = screen;
        return this;
    }

    @Override
    public String toString() {
        return "Server(name=" + name() + ", template=" + template.name() + ", id=" + id + ", connected=" + connected() + ", daemon=" + daemon.name() + ")";
    }
}
