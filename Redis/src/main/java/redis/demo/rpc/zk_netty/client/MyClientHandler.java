package redis.demo.rpc.zk_netty.client;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
/**
 * @author huangzehui
 * @date 18-11-1 下午2:36
 */

public class MyClientHandler extends ChannelInboundHandlerAdapter {

    private Object result;



    public Object getResult() {
        return result;
    }
    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//        System.out.println("MyClientHandler.channelActive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        System.out.println("read Message:"+msg);
        result = msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}