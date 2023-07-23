package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import vo.*;

// 마이페이지 회원 포인트 내역 
public class MyShoppingDao {
	private static MyShoppingDao myShoppingDao;
	private Connection conn;
	private MyShoppingDao() {}
	public static MyShoppingDao getInstance() {
		if (myShoppingDao == null) myShoppingDao = new MyShoppingDao(); 
		return myShoppingDao;
	}
	public void setConnection(Connection conn) {this.conn = conn;}
	
	// 마이페이지 포인트에서 검색된 결과의 레코드(게시글) 개수를 리턴하는 메소드
	public int getPointCount(String miid ) {
		Statement stmt = null;
		ResultSet rs = null;
		int rcnt  = 0;
		try {
			String sql =" select count(*) from t_member_point where mi_id = '" + miid + "'";
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()) rcnt = rs.getInt(1);
			
		}catch(Exception e) {
				System.out.println("MyShoppingDao의  getPointCount() 오류");
				e.printStackTrace();
			}finally {
				close(rs);close(stmt);
			}
			return rcnt;
	}
	// 현재 보유한 포인트(사용가능한 포인트)//
	public int getPointSum(String miid  ) {
		Statement stmt = null;
		ResultSet rs = null;
		int totalPoint  = 0; 
		try {
			String sql ="select sum(mp_point) from t_member_point " + 
					"where if(mp_su = 's', mp_point = mp_point , mp_point = mp_point * -1 ) " +
					" and mi_id = '" + miid + "'";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()) totalPoint = rs.getInt(1);
			
		}catch(Exception e) {
			System.out.println("MyShoppingDao의  getPointSum() 오류");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt);
		}
		return totalPoint;
	}
	// 포인트 사용 적립  내역을 보여줄 메소드
	public ArrayList<MemberPoint> getPointList(String miid, int cpage,int psize){
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<MemberPoint> pointList = new ArrayList<MemberPoint>();
		// 검색된 포인트들을 저장할 ArrayList<MemberPoint>
		MemberPoint mp = null;
		//pointList에 저장할 하나의 포인트 정보를 담을 인스턴스
		
		try {
			String sql ="select mp_idx, mp_point, mp_su, mp_desc, mp_detail, mi_id ,"+
					"if(curdate()=date(mp_date), right(mp_date, 8), replace(mid(mp_date,3,8), '-',' .')) wdate"+
					" from t_member_point where mi_id = '"+miid +"' order by mp_idx desc  limit " 
					+ ((cpage-1)*psize) + ", " + psize;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			while (rs.next()) {
				 mp = new MemberPoint();// 인스턴스 생성
					mp.setMp_idx(rs.getInt("mp_idx"));
					mp.setMp_point(rs.getInt("mp_point"));
					mp.setMp_su(rs.getString("mp_su"));
					mp.setMp_desc(rs.getString("mp_desc"));
					mp.setMp_detail(rs.getString("mp_detail"));
					mp.setMp_date(rs.getString("wdate"));
					mp.setMi_id(rs.getString("mi_id"));
					pointList.add(mp);
			}// 포인트 내역을 ArrayList에 담음
			
		}catch(Exception e) {
			System.out.println("MyShoppingDao의  getPointList() 오류");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt);
		}
		return pointList;
	}
	
	
}
