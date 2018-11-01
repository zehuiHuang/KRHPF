package redis.demo.rpc.v1;

/**
 * @author huangzehui
 * @date 18-10-31 下午4:28
 */
public interface  HelloService {

    String hello();

    String hello(String name);

    String test(String h);

}