package redis.demo.rpc.v1;

/**
 * @author huangzehui
 * @date 18-10-31 下午4:29
 */
public class RpcConsumer {
    public static void main(String[] args) throws Exception {
        HelloService service = RpcFramework.refer(HelloService.class, "127.0.0.1", 8999);
        HService hService= RpcFramework.refer(HService.class, "127.0.0.1", 8999);
        //System.out.println("----------------------");
        String result = service.test("dddddddddddddddddddd");
        String result2=hService.testH();
        System.out.println("result:"+result);
        System.out.println("result2:"+result2);

    }

}