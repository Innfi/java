package playground;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class MessageHandler extends ChannelInboundHandlerAdapter {
    protected CallbackInterface callback;

    public MessageHandler() {

    }

    public MessageHandler(CallbackInterface callback) {
        this.callback = callback;
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf)msg;
        String inputString = buffer.toString();
        System.out.println("inputString: " + inputString);

        //this.callback.read(inputString);
        context.write(msg);
    }

    @Override 
    public void channelReadComplete(ChannelHandlerContext context) throws Exception {
        System.out.println("channelReadComplete]");
        context.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    @Override 
    public void channelUnregistered(ChannelHandlerContext context) throws Exception {
        //if(this.callback != null) callback.afterClose(context);

        super.channelUnregistered(context);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
        cause.printStackTrace();
        context.close();
    }
}
