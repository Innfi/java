package playground;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class MessageHandler extends ChannelInboundHandlerAdapter {
    protected CallbackInterface callback;

    public MessageHandler(CallbackInterface callback) {
        this.callback = callback;
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf)msg;
        String inputString = buffer.toString();

        this.callback.read(inputString);
        //context.write(msg);
    }

    @Override 
    public void channelReadComplete(ChannelHandlerContext context) throws Exception {

    }

    @Override 
    public void channelUnregistered(ChannelHandlerContext context) throws Exception {
        if(this.callback != null) callback.afterClose(context);

        super.channelUnregistered(context);
    }
}
