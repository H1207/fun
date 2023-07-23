package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;


public class MyShoppingSvc {
	public int getPointCount(String miid ) {
		int rcnt = 0;
		Connection conn = getConnection();
		MyShoppingDao myShoppingDao = MyShoppingDao.getInstance();
		myShoppingDao.setConnection(conn);
		
		rcnt = myShoppingDao.getPointCount(miid);
		close(conn);
		return rcnt;
		
	}
	public int getPointSum(String miid ) {
		int totalPoint = 0;
		Connection conn = getConnection();
		MyShoppingDao myShoppingDao = MyShoppingDao.getInstance();
		myShoppingDao.setConnection(conn);
		
		totalPoint = myShoppingDao.getPointSum(miid);
		close(conn);
		return totalPoint;
	}
	
	
	public ArrayList<MemberPoint> getPointList(String miid, int cpage,int psize){
		ArrayList<MemberPoint> pointList = new ArrayList<MemberPoint>(); 
		Connection conn = getConnection();
		MyShoppingDao myShoppingDao = MyShoppingDao.getInstance();
		myShoppingDao.setConnection(conn);
		
		pointList = myShoppingDao.getPointList(miid,cpage,psize);
		close(conn);
		return pointList;
	}
}
