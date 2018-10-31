package redis.demo.rpc;

/**
 * @author huangzehui
 * @date 18-10-31 下午4:29
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello() {
        return "Hello";
    }

    @Override
    public String hello(String name) {
        return "Hello," + name;
    }

}