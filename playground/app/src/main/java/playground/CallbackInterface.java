package playground;

import io.netty.channel.ChannelHandlerContext;


public interface CallbackInterface {
    public void read(String msg);
    public String write(String msg);
    public void afterClose(ChannelHandlerContext context);
}