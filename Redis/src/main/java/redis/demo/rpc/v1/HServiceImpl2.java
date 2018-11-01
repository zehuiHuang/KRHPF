package redis.demo.rpc.v1;

/**
 * @author huangzehui
 * @date 18-11-1 下午4:47
 */
public class HServiceImpl2 implements HService{
    @Override
    public String testH() {
        return "调用的HServiceImpl2";
    }
}
