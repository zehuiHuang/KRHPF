package redis.demo.rpc.zk_netty.client;

import redis.demo.rpc.v1.HService;
import redis.demo.rpc.v1.HelloService;
import redis.demo.rpc.zk_netty.proxy.RemoteServiceImpl;

/**
 * 服务调用 （客户端）
 * @author huangzehui
 * @date 18-11-1 下午3:24
 */

public class ClientApp {
    public static void main(String[] args) {
        //设置Zookeeper的地址
        RemoteServiceImpl.setZookeeperAddress("127.0.0.1:2181");

        //获取动态代理的HelloService的“真实对象（其实内部不是真实的，被换成了调用远程方法）”
//        HelloService helloService = RemoteServiceImpl.newRemoteProxyObject(HelloService.class);
//        String result = helloService.hello("hzh");
//        System.out.println(result);

        HService hService = RemoteServiceImpl.newRemoteProxyObject(HService.class);
        String result2= hService.testH();
        System.out.println(result2);

        /*//启动10个线程去请求
        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        String result = helloService.sayHello("yyf");
                        System.out.println(result);
                        MyResult myResult = calService.getResult(1,2);
                        System.out.println(myResult);
                    }
                }
            }.start();
        }*/



    }
}