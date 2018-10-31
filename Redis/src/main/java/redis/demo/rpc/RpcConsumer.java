package redis.demo.rpc;

/**
 * @author huangzehui
 * @date 18-10-31 下午4:29
 */
public class RpcConsumer {
    public static void main(String[] args) throws Exception {
        HelloService service = RpcFramework.refer(HelloService.class, "127.0.0.1", 8999);
        String result = service.hello("rod");
        System.out.println(result);
    }

}