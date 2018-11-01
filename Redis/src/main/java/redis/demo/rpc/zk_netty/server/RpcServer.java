package redis.demo.rpc.zk_netty.server;

/**
 * @author huangzehui
 * @date 18-11-1 下午2:58
 */
public interface RpcServer {
    /**
     * 启动rpc服务
     */
    void start();

    /**
     * 停止rpc服务
     */
    void stop();

    /**
     * 把服务注册进rpc
     */
    void  register(String className, Class clazz) throws Exception;

    /**
     * rpc 服务是否存活
     * @return
     */
    boolean isAlive();


}