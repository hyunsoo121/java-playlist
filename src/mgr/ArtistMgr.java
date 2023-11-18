package mgr;

import facade.DataEngineImpl;
import stream.Artist;

public class ArtistMgr extends DataEngineImpl<Artist> {
    private static ArtistMgr engine = null;
    private ArtistMgr() {
        setLabels(headers);
    }
    public static ArtistMgr getInstance() {
        if (engine == null)
            engine = new ArtistMgr();
        return engine;
    }
    private String[] headers = {"아티스트", "데뷔일", "타입", "소속사"};
}