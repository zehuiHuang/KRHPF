package redis.demo.rpc.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangzehui
 * @date 18-10-31 下午4:27
 */
public class RpcProvider {

    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        HService hService = new HServiceImpl();
        Map<Object,Object> map = new HashMap<>();
        map.put("redis.demo.rpc.v1.HService",hService);
        map.put("redis.demo.rpc.v1.HelloService",service);

        RpcFramework.export(map, 8999);

    }

}