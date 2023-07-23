package svc;
import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrderProcInSvc {
	public String orderInsert(String kind, OrderInfo oi, String temp){
		String result= null;
 	
		Connection conn = getConnection();
		OrderProcDao orderProcDao = OrderProcDao.getInstance();
		orderProcDao.setConnection(conn);
		
		result = orderProcDao.orderInsert(kind, oi, temp);
		//result : 주문번호, 적용된 레코드 수, 적용되야 할 레코드 수 
		String[] arr = result.split(",");
		
		if(arr[1].equals(arr[2]))	commit(conn);
		else				rollback(conn);
		//실제 적용된 레코드 개수와 적용되어야 할 레코드 개수가 같으면 트랜잭션 
		//다르면 롤백 
		
		close(conn);
		return result;
	}
}
