package redis.demo.rpc.zk_netty.client;

/**
 * 负载，分配ip
 * @author huangzehui
 * @date 18-11-1 下午3:11
 */
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
public class ClientCluster {

    /**
     * 当有多台服务器的时候，随机分配一台。规则可自定义
     *
     * @param serviceName
     * @return
     */
    public static InetSocketAddress getServerIPByRandom(String serviceName) {
        List<InetSocketAddress> inetSocketAddressList = DiscoverService.discoverServices(serviceName);
        int length = inetSocketAddressList.size();

        return inetSocketAddressList.get(new Random().nextInt(length));
    }
}