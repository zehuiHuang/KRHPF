package redis.demo.rpc.zk_netty.proxy;

/**
 * 动态代理处理程序
 * @author huangzehui
 * @date 18-11-1 下午3:20
 */
import redis.demo.rpc.zk_netty.RpcRequest;
import redis.demo.rpc.zk_netty.RpcResponse;
import redis.demo.rpc.zk_netty.client.ClientCluster;
import redis.demo.rpc.zk_netty.client.NettyClient;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class ProxyHandler implements InvocationHandler {
    private Class<?> service;
    //远程调用地址
    private InetSocketAddress remoteAddress;


    public ProxyHandler(Class<?> service) {
        this.service = service;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        //准备传输的对象
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setServiceName(service.getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setArguments(args);
        rpcRequest.setParameterTypes(method.getParameterTypes());
        rpcRequest.setReturnType(method.getReturnType());
        return this.request(rpcRequest);
    }

    private Object request(RpcRequest rpcRequest) throws ClassNotFoundException {
        //获取需要请求的地址
        remoteAddress = ClientCluster.getServerIPByRandom(rpcRequest.getServiceName());
        if (remoteAddress == null) {
            return null;
        }
        Object result;
        RpcResponse rpcResponse = (RpcResponse) NettyClient.send(rpcRequest,remoteAddress);
        result = rpcResponse.getResult();
        return result;
    }
}
