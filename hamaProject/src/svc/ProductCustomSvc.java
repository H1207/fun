package svc;
import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;


public class ProductCustomSvc {
	public ArrayList<ProductCustom> getCustomList(String miid){
		//���̵� �ش��ϴ� Ŀ���� ����Ʈ��
		ArrayList<ProductCustom> customList = new ArrayList<ProductCustom>();
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		customList = productCustomDao.getCustomList(miid);

		close(conn);
		return customList;	
	}
	public ProductCustom getCustomInfo(int pmcidx) {
		//�ش� �ε����� ���� Ŀ���Ҹ�ī�� �����Ǹ� ��������
		ProductCustom pc= null;
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		pc = productCustomDao.getCustomInfo(pmcidx);
	
		close(conn);
		return pc;
	}
	public String getToppingName(String tpid) {
		//�ش� ���ξ��̵� ���� ������ �̸��� ��������
		String tpname = "";
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		tpname = productCustomDao.getToppingName(tpid);
		close(conn);
		return tpname;
	}
	public int countCustom(String miid) {
		//�ش� ���̵� ȸ���� ���� ��ī�� Ŀ���� ������ �߿��� �Խð����� ���� ���� �������� ���ڵ�
		int cnt = 0;
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		cnt = productCustomDao.countCustom(miid);
		close(conn);
		
		return cnt;
		
	}
	public ArrayList<ProductInfo> getMacList(){
		//��ǰ ����Ʈ�� ���� ���� ��������
		ArrayList<ProductInfo> macList = new ArrayList<ProductInfo>();
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		macList = productCustomDao.getMacList();

		close(conn);
		return macList;	
	}
	public ArrayList<ProductTopping> getToppingList(){
		//���� ����Ʈ �������� 
		ArrayList<ProductTopping> toppingList = new ArrayList<ProductTopping>();
		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		toppingList = productCustomDao.getToppingList();

		close(conn);
		return toppingList;	
	}
	
	public int customInsert(String miid, ProductCustom pc){
		//Ŀ���� �����Ǹ� ���	
		int result = 0;

		Connection conn = getConnection();
		ProductCustomDao productCustomDao = ProductCustomDao.getInstance();
		productCustomDao.setConnection(conn);
		
		result = productCustomDao.customInsert(miid, pc);
		
		if(result == 1) 	commit(conn);
		else				rollback(conn);	

		close(conn);
		return result;
	}
	
	
	
	
	
	
	

}


