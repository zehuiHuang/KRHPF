package redis.demo.rpc;

/**
 * @author huangzehui
 * @date 18-10-31 下午4:27
 */
public class RpcProvider {

    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, HelloService.class, 8999);
    }

}