package mgr;

import facade.DataEngineImpl;
import stream.Album;

public class AlbumMgr extends DataEngineImpl<Album> {
    private static AlbumMgr engine = null;
    private AlbumMgr() {
        setLabels(headers);
    }
    public static AlbumMgr getInstance() {
        if (engine == null)
            engine = new AlbumMgr();
        return engine;
    }
    private String[] headers = {"앨범명", "아티스트", "발매일", "장르", "수록곡수"};
}
