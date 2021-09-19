package playground;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.timeout.ReadTimeoutHandler;
//import io.netty.handler.timeout.WriteTimeoutHandler;


public class SocketServer {
    public void initNetty() {
        System.out.println("before starting netty server");

        // MessageHandler handler = new MessageHandler(new CallbackInterface() {
        //     public void read(String readBuffer) {
        //         System.out.println("messageHandle.read] " + readBuffer);
        //     }
        //     public String write(String writeBuffer) {
        //         System.out.println("messageHandle.write] " + writeBuffer);
        //         return writeBuffer;
        //     }
        //     public void afterClose(ChannelHandlerContext context) {
        //         System.out.println("afterClose" + context.name());
        //     }
        // });
        // System.out.println("App.initNetty] handler created");

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(group);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.localAddress(new InetSocketAddress("localhost", 1330));
            
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>(){
                protected void initChannel(SocketChannel channel) throws Exception {
                    // channel.pipeline().addLast("ReadTimeoutHandler", new ReadTimeoutHandler());
                    // channel.pipeline().addLast("WriteTimeoutHandler", new WriteTimeoutHandler());
                    channel.pipeline().addLast("messageHandler", new MessageHandler());
                }
            })
            .option(ChannelOption.SO_BACKLOG, 2)
            .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = bootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
            System.out.println("App.initNetty] after bind");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("after initNetty()");
        }
    }

    public String name() { return "nettyTester"; }
}
