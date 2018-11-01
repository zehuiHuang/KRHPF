package redis.demo.rpc.zk_netty.server;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import redis.demo.rpc.zk_netty.RegisterServicesCenter;
import redis.demo.rpc.zk_netty.RpcException;
import redis.demo.rpc.zk_netty.RpcRequest;
import redis.demo.rpc.zk_netty.RpcResponse;

import java.lang.reflect.Method;

/**
 * @author huangzehui
 * @date 18-11-1 下午2:39
 */


public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest rpcRequest = (RpcRequest) msg;
        Class clazz = RegisterServicesCenter.getService(rpcRequest.getServiceName());
        if (clazz == null) {
            throw new RpcException("没有找到类 " + rpcRequest.getServiceName());
        }
        Method method = clazz.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
        if (method == null) {
            throw new RpcException("没有找到相应方法 " + rpcRequest.getMethodName());
        }
        //执行真正要调用的方法。对于实例都不是单利模式的，每次都clazz.newInstance()，有待改进
        Object result = method.invoke(clazz.newInstance(), rpcRequest.getArguments());
        //返回执行结果给客户端
        RpcResponse rpcResponse = new RpcResponse(result);
        ctx.writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE);
        /*System.out.println(msg);
        myRequest.setRequestTime(new Date());
        ctx.writeAndFlush(myRequest).addListener(ChannelFutureListener.CLOSE);*/
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}