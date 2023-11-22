package mainGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import admin_gui.AdminGUI;
import admin_gui.DetailDialog;
import facade.IDataEngine;
import facade.UIData;

public class MainTableFormat extends JPanel {
    private static final long serialVersionUID = 1L;
    JTable table;
    DefaultTableModel tableModel;
    int selectedIndex = -1;   // 테이블에서 선택된 행의 인덱스를 가질 변수
    String tableTitle = null;
    IDataEngine<?> dataMgr;  // 엔진 쪽의 데이터를 관리하는 매니저 파사드 인터페이스
    public MainTableFormat() {
        super(new BorderLayout());
    }
    // 테이블을 생성하여 초기화하고 스크롤 붙여서 패널에 add한다
    public void addComponentsToPane(IDataEngine<?> mgr) {
        init(mgr);
        JScrollPane center = new JScrollPane(table);
        add(center, BorderLayout.CENTER);
        JTableClickEvent clickEvent = new JTableClickEvent();
        table.addMouseListener(clickEvent);
    }
    // 테이블의 기본 설정을 하는 부분 (테이블 모델을 생성하고 초기 데이터 불러오고
    // 테이블에 필요한 설정을 초기화한다
    // 장바구니 테이블은 오버라이드하여 가져올 주문번호를 세팅한 후 수퍼 호출
    @SuppressWarnings("serial")
    void init(IDataEngine<?> mgr) {
        dataMgr = mgr;
        tableModel = new DefaultTableModel(mgr.getColumnNames(), 0){
            // 셀 수정 못하게 하는 부분
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        loadData("");

        table = new JTable(tableModel);
        //ListSelectionModel rowSM = table.getSelectionModel();
        //rowSM.addListSelectionListener(this);
        table.setPreferredScrollableViewportSize(new Dimension(500, 220));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    // 매니저에서 검색된 객체들을 테이블에 보여준다. kwd가 ""면 모두 출력
    void loadData(String kwd) {
        List<?> result = dataMgr.search(kwd); // 매니저에서 검색결과를 가져옴
        //System.out.format("매니저에서 검색결과를 가져옴. 개수 %d개", result.size());
        tableModel.setRowCount(0);  // 현재 데이터모델의 행을 모두 지운다
        for (Object m : result)     // 한 행씩 추가함
            tableModel.addRow(((UIData)m).getUiTexts());
    }
    
    @SuppressWarnings("serial")
	public class JTableClickEvent extends JFrame implements MouseListener {
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		if(tableTitle.equals("playlist")) return;
    		int row = table.getSelectedRow();
    		selectedIndex = row;
    		String[] rowValue = new String[table.getModel().getColumnCount()];
    		for(int i = 0; i < rowValue.length; i++) {
    			rowValue[i] = (String)table.getModel().getValueAt(row, i);
    		}
    		MainFrame.getInstance().infoRefresh(tableTitle, rowValue);
    	}
    	 @Override
    	 public void mousePressed(MouseEvent e) {
    	  // TODO Auto-generated method stub  	  
    	 }
    	 @Override
    	 public void mouseReleased(MouseEvent e) {
    	  // TODO Auto-generated method stub
    	 }

    	 @Override
    	 public void mouseEntered(MouseEvent e) {
    	  // TODO Auto-generated method stub
    	 }
    	 @Override
    	 public void mouseExited(MouseEvent e) {
    	  // TODO Auto-generated method stub
    	 }
    }
}
