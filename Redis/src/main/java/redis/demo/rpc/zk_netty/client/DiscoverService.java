package redis.demo.rpc.zk_netty.client;

/**
 * @author huangzehui
 * @date 18-11-1 下午3:10
 */
import org.apache.zookeeper.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DiscoverService {

    //zookeeper地址,默认本机2181接口
    private static String ZOOKEEPER_HOST = "localhost:2181";
    private static Map<String, List<InetSocketAddress>> sevices = new ConcurrentHashMap<>();
    private static final int TIME_OUT = 10000;

    //发现服务，按照ip负载均衡
    public static List<InetSocketAddress> discoverServices(String serviceName) {
        List<InetSocketAddress> results = sevices.get(serviceName);
        if (results != null) {
            return results;
        } else {
            results = new ArrayList<InetSocketAddress>();
        }

        ZooKeeper zookeeper = null;
        try {
            zookeeper = new ZooKeeper(ZOOKEEPER_HOST, TIME_OUT, new MyWatcher());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            byte[] res = zookeeper.getData("/myrpc/" + serviceName, true, null);
            if (res == null) {
                System.err.println(serviceName + "服务没有发现...");
            }
            String resIpStr = new String(res);
            String[] resIps = resIpStr.split(";");
            System.out.println("发现服务： " + serviceName + " " + resIpStr);
            for (String resIp : resIps) {
                if (resIp == null || "".equals(resIp)) {
                    continue;
                }
                try {
                    InetSocketAddress result = new InetSocketAddress(resIp.split(":")[0], Integer.valueOf(resIp.split(":")[1]));
                    results.add(result);
                } catch (Exception e) {
                }

            }
            sevices.put(serviceName, results);
            return results;
        } catch (KeeperException e) {
            System.err.println(serviceName + " 服务没有发现...");
        } catch (InterruptedException e) {
            System.err.println(serviceName + " 服务没有发现...");
        } finally {
            try {
                zookeeper.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void setZookeeperHost(String zookeeperHost) {
        ZOOKEEPER_HOST = zookeeperHost;
    }


    /**
     * 优化
     * 这里可以增加一个定时器，其扫描服务端的服务是否可用，如果不可用，剔除 sevices
     */

    static class MyWatcher  implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            System.out.println("hello zookeeper");
            System.out.println(String.format("hello event! type=%s, stat=%s, path=%s",event.getType(),event.getState(),event.getPath()));
        }
    }
}