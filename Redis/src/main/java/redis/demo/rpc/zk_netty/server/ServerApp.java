package redis.demo.rpc.zk_netty.server;

/**
 * 启动RPC服务
 * @author huangzehui
 * @date 18-11-1 下午3:44
 */

import redis.demo.rpc.v1.*;

public class ServerApp {
    public static void main(String[] args) throws Exception {
        //zookeeperHost多个用逗号分开
        RpcServer rpcServer = new RpcServerImpl(7878,5,"127.0.0.1:2181",true);
        //暴露HelloService接口，具体实现为HelloServiceImpl
        rpcServer.register(HelloService.class.getName(),HelloServiceImpl.class);
        rpcServer.register(HService.class.getName(),HServiceImpl.class);
        //服务注册其实就是把接口名字

        //启动rpc服务
        rpcServer.start();
    }
}