package svc;
import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;


public class EvTorFormSvc {
	public int didTor(String miid, String date){
		//ȸ���� ���̵�� ������('2022-02')�� �������� t_ev_cus_tor ���̺� ���� �ִ��� 
		//���η� ��ʸ�Ʈ�� �ش� ���� �����ߴ��� Ȯ���ϴ� �޼ҵ� 
		int result=0;
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		
		result = evTorProcDao.didTor(miid, date);
		close(conn);
		return result;
	}
	
	public ArrayList<ProductCustom> getbuyCustomList(String miid){
		ArrayList<ProductCustom> customList =   new ArrayList<ProductCustom>();
				
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		
		customList = evTorProcDao.getbuyCustomList(miid);

		close(conn);
		return customList;	
	}
	public int torInsert(String miid, EvCusTor ect){
		//��ʸ�Ʈ �� �Է� 
		int result=0;
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		
		result = evTorProcDao.torInsert(miid, ect);
	
		if(result == 1) 	commit(conn);
		else				rollback(conn);	
		
		close(conn);
		return result;
	}
	

}
