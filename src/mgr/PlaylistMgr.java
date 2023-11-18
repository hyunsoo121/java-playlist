package mgr;

import facade.DataEngineImpl;
import stream.Playlist;

public class PlaylistMgr extends DataEngineImpl<Playlist> {
    private static PlaylistMgr engine = null;
    private PlaylistMgr() {
        setLabels(headers);
    }
    public static PlaylistMgr getInstance() {
        if (engine == null)
            engine = new PlaylistMgr();
        return engine;
    }
    private String[] headers = {"ID", "사용자번호", "제목", "생성일", "트랙수"};
}