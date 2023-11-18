package admin_gui;

import facade.UIData;
import mgr.PlaylistMgr;
import stream.Music;
import stream.Playlist;

import java.util.ArrayList;

/* 2단 테이블 구조에서 하단 테이블의 데이터를 불러옵니다. */

@SuppressWarnings("serial")
public class SubTableFormat extends TableFormat {

	/*
	loadData 메서드는 Keyword 의 matches 검색 결과를 반환한 리스트를 통해 테이블모델에 추가합니다.
	다만 Playlist 수록곡의 경우 특정한 Keyword 로 한 번에 검색할 수 없어, 별도의
	loadPlaylist 메서드를 추가해 List 를 매니저로부터 직접 불러와 테이블모델에 추가하도록 변경하였습니다.
	*/

	void loadPlaylist(String id) {
		Playlist p = PlaylistMgr.getInstance().find(id);
		ArrayList<Music> musicList = p.getList();
		tableModel.setRowCount(0);
		for(Music m : musicList){
			tableModel.addRow(((UIData)m).getUiTexts());
		}
	}
}
