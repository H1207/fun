package dao;

import static db.JdbcUtil.*;  
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.*;
import vo.*;
import svc.*;

public class MyTorListDao {
	private static MyTorListDao myTorListDao;
	private Connection conn;
	private MyTorListDao() {}

	public static MyTorListDao getInstance() {
		if (myTorListDao == null)	myTorListDao = new MyTorListDao();
		return myTorListDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList<EvCusTor>  getTorList(String miid,  String orderBy ){
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<EvCusTor> torList = new ArrayList<EvCusTor>();
		EvCusTor ect = null;
		// torList에 저장할 하나의 게시글 정보를 담을 인스턴스
		//where 절에 kind=b일때를 보여줘야함
		String orderby = orderBy;// 정렬도 다르게
		try {
			
		//	System.out.println(kind);
			System.out.println(orderby);
			
			String sql = "select * from t_ev_cus_tor " + 
					" where mi_id = '" + miid + "' "; 
			// 등록일 순으로 내 토너먼트 글 보기
			// 마이페이지이니까 게시여부 'n'도 보이게

			sql += ( orderby) ;
			System.out.println(sql + ": /MyTorListDao/getTorList");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);	
			
			while(rs.next()) {
				ect = new EvCusTor();
				// 커스텀 토너먼트에 셋팅
				ect.setMi_id(miid); //회원 아이디 
				ect.setEct_idx(rs.getInt("ect_idx")); // 토너먼트 인덱스 
				ect.setPmc_idx(rs.getInt("pmc_idx")); // 커스텀 인덱스
				ect.setEct_date(rs.getString("ect_date")); // 등록일
				ect.setEct_vote(rs.getInt("ect_vote"));	// 득표수
				ect.setEct_isview(rs.getString("ect_isview")); // 게시여부
				ect.setEct_img1(rs.getString("ect_img1"));	//이미지
				ect.setEct_title(rs.getString("ect_title"));	//제목
				ect.setEct_content(rs.getString("ect_content")); //내용
				torList.add(ect);
			}

		}catch(Exception e){
			System.out.println("MyTorListDao의  getTorList메소드에서오류발생");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt);
		}
		return torList;	
		
	}
}
