package de.nurmarvin.hycloud.master.networking;

import de.nurmarvin.hycloud.master.Master;
import de.nurmarvin.hycloud.master.networking.handler.NetworkHandler;
import de.nurmarvin.hycloud.master.networking.packet.PacketDecoder;
import de.nurmarvin.hycloud.master.networking.packet.PacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.io.File;

/**
 * The network server handling the connection between the Master and
 * the {@link de.nurmarvin.hycloud.master.daemon.Daemon}s and {@link de.nurmarvin.hycloud.master.server.Server}s
 * @since 1.0
 * @author NurMarvin
 */
public class NetworkServer {
    private static final boolean EPOLL = Epoll.isAvailable();
    private EventLoopGroup eventLoopGroup;
    private Master master;
    private int port;

    public NetworkServer(Master master) {
        this.master = master;
        try {
            port = Master.instance().generalConfig().port();
            startServer();
        } catch (Exception ex) {
            Master.instance().logger().error("An error occurred while starting the network server", ex);
        }
    }

    private void startServer() throws Exception {
        master.logger().info("Starting network server. This might take a while...");
        master.logger().info("Creating self-signed SSL certificate");
        SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
        SslContext sslContext =
                SslContextBuilder.forServer(selfSignedCertificate.key(), selfSignedCertificate.cert()).build();

        master.logger().info("Created self-signed SSL certificate");

        eventLoopGroup = EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

        new ServerBootstrap()
                .group(eventLoopGroup)
                .channel(EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {

                    @Override
                    protected void initChannel(Channel channel) {

                        channel.pipeline()
                               .addLast(new SslHandler(sslContext.newEngine(channel.alloc())))
                               .addLast(new ProtobufVarint32FrameDecoder())
                               .addLast(new PacketDecoder())
                               .addLast(new ProtobufVarint32LengthFieldPrepender())
                               .addLast(new PacketEncoder());

                    }
                }).bind(port).await().channel();
        master.logger().info("Successfully started network server {}!", this);
    }

    @Override
    public String toString() {
        return "NetworkServer(port=" + port + ")";
    }

    public EventLoopGroup eventLoopGroup() {
        return eventLoopGroup;
    }
}
