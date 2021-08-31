package playground;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

public class App {
    public static void main(String[] args) {
        System.out.println("before starting netty server");

        initNetty();
    }

    public static void initNetty() {
        MessageHandler handler = new MessageHandler(new CallbackInterface() {
            public void read(String read) {

            }
            public String write(String write) {
                return "write";
            }
            public void afterClose(ChannelHandlerContext context) {

            }
        });

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(group);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.localAddress(new InetSocketAddress("localhost", 1330));
            
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>(){
                protected void initChannel(SocketChannel channel) throws Exception {
                    channel.pipeline().addLast("ReadTimeoutHandler", new ReadTimeoutHandler(3));
                    channel.pipeline().addLast("WriteTimeoutHandler", new WriteTimeoutHandler(3));
                    channel.pipeline().addLast("messageHandler", handler);
                }
            })
            .option(ChannelOption.SO_BACKLOG, 2)
            .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = bootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("after initNetty()");
        }
    }

    public String name() { return "nettyTester"; }
}
