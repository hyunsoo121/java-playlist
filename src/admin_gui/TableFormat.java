package admin_gui;

import facade.IDataEngine;
import facade.UIData;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;

/*
 * 테이블을 포함한 패널로 테이블과 테이블모델을 생성하여 초기화하고(init)
 * 매니저에서 데이터를 받아와 테이블 행을 추가하는 기능(loadData)과
 * 행이 선택되었을 때 필요한 일을 하는 기능(valueChanged)를 가진다.
 */
public class TableFormat extends JPanel implements ListSelectionListener {
    private static final long serialVersionUID = 1L;
    JTable table;
    DefaultTableModel tableModel;
    int selectedIndex = -1; // 테이블에서 선택된 행의 인덱스를 가질 변수
    String tableTitle = null;
    IDataEngine<?> dataMgr; // 엔진 쪽의 데이터를 관리하는 매니저 파사드 인터페이스

    public TableFormat() {
        super(new BorderLayout());
    }

    // 테이블을 생성하여 초기화하고 스크롤 붙여서 패널에 add한다
    public void addComponentsToPane(IDataEngine<?> mgr) {
        init(mgr);
        JScrollPane center = new JScrollPane(table);
        add(center, BorderLayout.CENTER);
    }

    // 테이블의 기본 설정을 하는 부분 (테이블 모델을 생성하고 초기 데이터 불러오고
    // 테이블에 필요한 설정을 초기화한다
    // 장바구니 테이블은 오버라이드하여 가져올 주문번호를 세팅한 후 수퍼 호출
    @SuppressWarnings("serial")
    void init(IDataEngine<?> mgr) {
        dataMgr = mgr;
        tableModel = new DefaultTableModel(mgr.getColumnNames(), 0) {
            // 셀 수정 못하게 하는 부분
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loadData("");

        table = new JTable(tableModel);
        ListSelectionModel rowSM = table.getSelectionModel();
        rowSM.addListSelectionListener(this);
        table.setPreferredScrollableViewportSize(new Dimension(500, 220));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    // 매니저에서 검색된 객체들을 테이블에 보여준다. kwd가 ""면 모두 출력
    void loadData(String kwd) {
        List<?> result = dataMgr.search(kwd); // 매니저에서 검색결과를 가져옴
        // System.out.format("매니저에서 검색결과를 가져옴. 개수 %d개", result.size());
        tableModel.setRowCount(0); // 현재 데이터모델의 행을 모두 지운다
        for (Object m : result) // 한 행씩 추가함
            tableModel.addRow(((UIData) m).getUiTexts());
    }

    // 아이템 패널에서 상세보기 버튼을 눌렀을 때 실행되는 메소드
    void showDetail(int tabNumber) {
        if (selectedIndex < 0)
            return;
        String[] rowTexts = new String[tableModel.getColumnCount()];
        for (int i = 0; i < rowTexts.length; i++)
            rowTexts[i] = (String) tableModel.getValueAt(selectedIndex, i);
        DetailDialog dlg = new DetailDialog(rowTexts);
        dlg.setup(tabNumber, this); // 수정1
        dlg.pack();
        dlg.setVisible(true);
    }

    public void valueChanged(ListSelectionEvent e) {
        // ListSelectionModel을 통해 테이블 내 선택된 행의 index를 (selectedIndex 로) 가져옵니다.
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
        if (!lsm.isSelectionEmpty()) {
            selectedIndex = lsm.getMinSelectionIndex();
            String name = (String) tableModel.getValueAt(selectedIndex, 1);
            if (tableTitle.equals("item")) {
                AdminGUI.getInstance().musicTop.kwdTextField.setText(name);
            } else if (tableTitle.equals("album")) {
                // selectedIndex 행의 0번째 열 (=제목)을 loadData 의 인수로 보내 일치하는 Music 을 albumMusicTable
                // 에 보여지도록 합니다.
                TableModel data = table.getModel();
                String albumTitle = (String) data.getValueAt(selectedIndex, 0);
                AdminGUI.getInstance().albumMusicTable.loadData(albumTitle);
            } else if (tableTitle.equals("playlist")) {
                // selectedIndex 행의 0번째 열 (=id)를 loadPlaylist 의 인수로 보내 PlaylistMgr 이 일치하는 id의
                // ArrayList 를 반환하도록 합니다.
                TableModel data = table.getModel();
                String id = (String) data.getValueAt(selectedIndex, 0);
                AdminGUI.getInstance().playlistMusicTable.loadPlaylist(id);
            }
        }
    }
}