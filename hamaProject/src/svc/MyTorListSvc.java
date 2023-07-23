package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

// 마이페이지의 내 토너먼트 보기
public class MyTorListSvc {
	public ArrayList<EvCusTor>  getTorList(String miid,  String orderBy){
		//아이디에 해당하는 커스텀 리스트를
		 ArrayList<EvCusTor> torList = new  ArrayList<EvCusTor>();
		Connection conn = getConnection();
		MyTorListDao myTorListDao = MyTorListDao.getInstance();
		myTorListDao.setConnection(conn);
		
		torList = myTorListDao.getTorList(miid, orderBy);

		close(conn);
		return torList;	
	}
}
