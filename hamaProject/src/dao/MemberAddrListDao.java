package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import svc.*;
import vo.*;

public class MemberAddrListDao {
	
	private static MemberAddrListDao memberAddrListDao;
	private Connection conn;
	
	public static MemberAddrListDao getInstance() {
		if(memberAddrListDao == null)		memberAddrListDao = new MemberAddrListDao();
		
		return memberAddrListDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList<MemberAddr> addrList(String miid){
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<MemberAddr> addrList = new ArrayList<MemberAddr>();
		MemberAddr ma = null;
		
		try {
			String sql = "select * from t_member_addr "
					+ " where mi_id= '"+miid+"'";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			System.out.println(sql + "MemberAddrListDao / addrList");
			while(rs.next()) {
				ma = new MemberAddr();
				ma.setMi_id(rs.getString("mi_id"));
				ma.setMa_rname(rs.getString("ma_rname"));
				ma.setMa_phone(rs.getString("ma_phone"));
				ma.setMa_zip(rs.getString("ma_zip"));
				ma.setMa_addr1(rs.getString("ma_addr1"));
				ma.setMa_addr2(rs.getString("ma_addr2"));
				ma.setMa_basic(rs.getString("ma_basic"));
				addrList.add(ma);
				System.out.println(rs.getString("mi_id"));
			}
			
		}catch(Exception e) {
			System.out.println("MemberAddrListDao 클래스의 addrList() 메소드 오류");
			e.printStackTrace();
		}finally {
			close(rs);		close(stmt);
		}
		
		return addrList;
	}
}
