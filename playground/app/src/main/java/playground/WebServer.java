package playground;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseDecoder;


public class WebServer {
    protected int threadCount;
    protected EventLoopGroup group;
    protected EventLoopGroup workerGroup;

    public WebServer() {
        threadCount = 1;
        group = new NioEventLoopGroup(threadCount);
        workerGroup = new NioEventLoopGroup();
    }

    public void start() throws Exception {

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group, workerGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<SocketChannel>(){
           @Override
           protected void initChannel(SocketChannel channel) throws Exception {
               ChannelPipeline pipeline = channel.pipeline();
               pipeline.addLast(new HttpRequestDecoder());
               pipeline.addLast(new HttpResponseDecoder());
               pipeline.addLast(new CustomHttpMessageHandler());
           }
        });

        ChannelFuture future = bootstrap.bind(8080).sync();
        future.channel().closeFuture().sync();
    }

    public void stop() throws Exception {
        group.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
