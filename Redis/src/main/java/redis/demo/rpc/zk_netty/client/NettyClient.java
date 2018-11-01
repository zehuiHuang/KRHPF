package redis.demo.rpc.zk_netty.client;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import redis.demo.rpc.zk_netty.MyDecoder;
import redis.demo.rpc.zk_netty.MyEncoder;
import redis.demo.rpc.zk_netty.RpcRequest;
import redis.demo.rpc.zk_netty.RpcResponse;
/**
 * @author huangzehui
 * @date 18-11-1 下午3:05
 */


import java.net.InetSocketAddress;

public class NettyClient {
    private static Integer TIMEOUT = 1000;

    public static Object send(RpcRequest rpcRequest, InetSocketAddress inetSocketAddress) {
        MyClientHandler myClientHandler = new MyClientHandler();
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            //ChannelPipeline p = ch.pipeline();
                            ch.pipeline().addLast(new MyEncoder(RpcRequest.class));
                            ch.pipeline().addLast(new MyDecoder(RpcResponse.class));
                            ch.pipeline().addLast(myClientHandler);
                        }
                    });

            ChannelFuture future = b.connect(inetSocketAddress.getAddress(), inetSocketAddress.getPort()).sync();
            future.channel().writeAndFlush(rpcRequest);

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return myClientHandler.getResult();
    }


}