package mgr;

import facade.DataEngineImpl;
import stream.User;

public class UserMgr extends DataEngineImpl<User> {
    private static UserMgr engine = null;
    private UserMgr() {
        setLabels(headers);
    }
    public static UserMgr getInstance() {
        if (engine == null)
            engine = new UserMgr();
        return engine;
    }
    private String[] headers = {"ID", "전화번호", "이름", "보유한 플레이리스트 수"};
}
