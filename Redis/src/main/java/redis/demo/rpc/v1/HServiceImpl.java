package redis.demo.rpc.v1;

/**
 * @author huangzehui
 * @date 18-11-1 上午11:27
 */
public class HServiceImpl implements HService {
    @Override
    public String testH() {
        return "HServiceImpl------------------";
    }
}
