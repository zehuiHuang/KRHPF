package redis.demo.rpc.zk_netty.server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import redis.demo.rpc.zk_netty.MyDecoder;
import redis.demo.rpc.zk_netty.MyEncoder;
import redis.demo.rpc.zk_netty.RpcRequest;
import redis.demo.rpc.zk_netty.RpcResponse;

import java.net.InetSocketAddress;
/**
 * @author huangzehui
 * @date 18-11-1 下午2:32
 */

public class NettyServer {
    private int port;
    private int threadSize = 5;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public NettyServer(int port, int threadSize) {
        this.port = port;
        this.threadSize = threadSize;
    }

    public void start() {
        bossGroup = new NioEventLoopGroup(threadSize);
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap sbs = new ServerBootstrap().group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                            ch.pipeline().addLast(new MyDecoder(RpcRequest.class));
                            ch.pipeline().addLast(new MyEncoder(RpcResponse.class));
                            ch.pipeline().addLast(new MyServerHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 绑定端口，开始接收进来的连接
            ChannelFuture future = sbs.bind(port).sync();
            System.out.println("Rpc服务启动成功 " + port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void shutdown(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

//    public static void main(String[] args) throws Exception {
//        int port;
//        if (args.length > 0) {
//            port = Integer.parseInt(args[0]);
//        } else {
//            port = 8080;
//        }
//        new NettyServer(port,5).start();
//    }
}
