package redis.demo.rpc;

/**
 * @author huangzehui
 * @date 18-10-31 下午4:28
 */
public interface  HelloService {

    String hello();

    String hello(String name);

}