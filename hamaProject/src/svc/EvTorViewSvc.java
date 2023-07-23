package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;


public class EvTorViewSvc {
	public EvCusTor getEvCusTorInfo(int ectidx) {
		EvCusTor ect = null;
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		
		ect = evTorProcDao.getEvCusTorInfo(ectidx);
	
		close(conn);
		return ect;
	}
	
	// ��ǥ�ϱ� �޼ҵ�
	public int voteBtn(	int ectidx, String miid) {
		int result = 0;			
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
			
		result = evTorProcDao.voteBtn(ectidx, miid);
		if (result==2) 	commit(conn);
		else			rollback(conn);
		close(conn);
			
		return result;
	}
	
	
	public EvCusTorPoll didvote(String miid) {	//###################��ǥ�ߴ��� Ȯ���ϴ� �޼ҵ�
		EvCusTorPoll ectp = null;
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		ectp = evTorProcDao.isvote(miid);
	
		close(conn);
		return ectp;
	}

	public ProductCustom getCustomInfo(int pcidx) {
		ProductCustom pc = null;
		Connection conn = getConnection();
		EvTorProcDao evTorProcDao = EvTorProcDao.getInstance();
		evTorProcDao.setConnection(conn);
		pc = evTorProcDao.getCustomInfo(pcidx);
		
		close(conn);
		return pc;
	}
}
