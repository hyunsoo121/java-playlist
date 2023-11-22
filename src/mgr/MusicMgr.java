package mgr;

import facade.DataEngineImpl;
import stream.Album;
import stream.Music;

public class MusicMgr extends DataEngineImpl<Music> {
    private static MusicMgr engine = null;
    private MusicMgr() {
        setLabels(headers);
    }
    public static MusicMgr getInstance() {
        if (engine == null)
            engine = new MusicMgr();
        return engine;
    }
    private String[] headers = {"ID", "제목", "아티스트", "앨범명", "재생수"};

    Album album;

    public void setOrder(int n) {
        album = AlbumMgr.getInstance().find(""+n);
    }
}
