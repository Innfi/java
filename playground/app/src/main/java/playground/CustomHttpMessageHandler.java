package playground;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;


public class CustomHttpMessageHandler extends SimpleChannelInboundHandler<Object> {
    //private HttpRequest httpRequest;
    StringBuilder responseData = new StringBuilder();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpRequest) { }
        
        if(msg instanceof HttpContent) { }

        initialResponse(ctx);
    }
    
    protected void initialResponse(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK);

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, 
            "text/plain; charset=UTF-8");

        ctx.write(response);
    }
}
