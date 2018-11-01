package redis.demo.rpc.zk_netty;

/**
 * @author huangzehui
 * @date 18-11-1 下午2:42
 */
public class RpcResponse {
    public RpcResponse() {
    }

    public RpcResponse(Object result) {
        this.result = result;
    }

    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}