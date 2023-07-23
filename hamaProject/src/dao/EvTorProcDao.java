package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.*;
import vo.*;
import svc.*;

public class EvTorProcDao {
	// ��ʸ�Ʈ �Խ��� ���� �����۾�(���, ���, ����)���� ��� ó���ϴ� Ŭ����
	private static EvTorProcDao evTorProcDao;
	private Connection conn;

	private EvTorProcDao() {
	} // �⺻������

	public static EvTorProcDao getInstance() {
		if (evTorProcDao == null)
			evTorProcDao = new EvTorProcDao();
		// �̹� ������ freeProcDao Ŭ������ �ν��Ͻ��� ������ ���Ӱ� �ν��Ͻ��� �����϶�
		return evTorProcDao;
	}

	public void setConnection(Connection conn) {
		// �� Dao Ŭ�������� ����� Ŀ�ؼ� ��ü�� �޾ƿͼ� �������ִ� �޼ҵ�
		this.conn = conn;
	}

	public int getTorListCount(String where) {
		// ��ʸ�Ʈ ����Ʈ�� �˻��� ����� ���ڵ�(�Խñ�) ������ �����ϴ� �޼ҵ� 
		// ����¡ ������ �Ƚ�� 
		int rcnt = 0;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select count(*) from t_ev_cus_tor " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next())
				rcnt = rs.getInt(1);
			// �÷��� ��� �ε��� ��ȣ �׳� ��
			// select count�ϴ� �ſ��� ��� �˻� ���ص� ��.

		} catch (Exception e) {
			System.out.println("EvTorProcDaoŬ����:getTorListCount()����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}

		return rcnt;
	}

	public ArrayList<EvCusTor> getTorList( String kind, String date,  String orderBy){
		// ��ʸ�Ʈ ����� ArrayList�� �����ϴ� �޼ҵ�(��ʸ�Ʈ ��� �����ֱ⸸ �� ��)
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<EvCusTor> torList = new ArrayList<EvCusTor>();
		// �׳� �����ص� ��
		EvCusTor ect = null;
		// torList�� ������ �ϳ��� �Խñ� ������ ���� �ν��Ͻ�
		// where ���� kina=a or b�� ��� ���� �ٸ���
		String orderby = orderBy;// ���ĵ� �ٸ���
		try {
		
			// torList���� ������ ���
			System.out.println(kind);
			System.out.println(orderby);
					
			
			String sql = "select * from t_ev_cus_tor ";
			String datesql = " where ect_isview='y' ";
			
			// kind=a �̹� �޿� �ö�� �۵鸸 
			if (kind.equals("a")) {
				datesql += " and left(ect_date, 7)='"+date+"' ";
			}
			// kind=b �̹� ���� �ƴ�  �۵鸸 
			if (kind.equals("b")) { //���� ��,���� ���� �ʴ� �ɷ� limit 100, ������ �α�(��ǥ��)
				datesql += " and left(ect_date, 7)<>'"+date+"'";
				orderby += " limit 0,100 " ;
			}
						
			sql += (datesql+ orderby) ;
			stmt = conn.createStatement();
			System.out.println(sql);

			rs = stmt.executeQuery(sql);
			// �� ���� �ƴϴϱ� ����������
			while (rs.next()) {
				ect = new EvCusTor();
				ect.setMi_id(rs.getString("mi_id"));
				ect.setPmc_idx(rs.getInt("pmc_idx"));
				ect.setEct_idx(rs.getInt("ect_idx"));
				ect.setEct_vote(rs.getInt("ect_vote"));
				ect.setEct_img1(rs.getString("ect_img1"));
				ect.setEct_title(rs.getString("ect_title"));
				ect.setEct_date(rs.getString("ect_date"));
				torList.add(ect);
			}

		} catch (Exception e) {
			System.out.println("EvTorProcDao : getTorList()�޼ҵ���� ");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}

		return torList;
	}

	public EvCusTor getEvCusTorInfo(int ectidx) {
		// ������ �Խñ��� �������� EvCusTor�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		EvCusTor ect = null;

		try {
			String sql = "select * from t_ev_cus_tor where ect_isview = 'y' and ect_idx=" + ectidx;

			stmt = conn.createStatement();
			System.out.println(sql + ":EvTorProcDao :getEvCusTorInfo()");
			rs = stmt.executeQuery(sql);
			// �� �Խñۿ��� ������ �׸��
			// �� �� �ϱ� ���� �ȵ����� ��
			if (rs.next()) {
				ect = new EvCusTor();
				ect.setEct_idx(ectidx);
				ect.setMi_id(rs.getString("mi_id"));
				ect.setPmc_idx(rs.getInt("pmc_idx"));
				ect.setEct_date(rs.getString("ect_date"));
				ect.setEct_vote(rs.getInt("ect_vote"));
				ect.setEct_img1(rs.getString("ect_img1"));
				ect.setEct_title(rs.getString("ect_title"));
				ect.setEct_content(rs.getString("ect_content"));
	
			}

		} catch (Exception e) {
			System.out.println("EvTorProcDaoŬ����:getEvCusTorInfo()����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return ect;
	}									
	public int voteBtn(int ectidx, String miid) { // ##############################
		// ������ �Խù��� ��ǥ�ϱ�[���ƿ�� ����]�� ó���ϴ� �޼ҵ�
		// �̹� ��ǥ�� ������ 0, ��ǥ�� �ȵǾ����� 1��, ���� ó�� �Ǿ����� 2�� ����
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;

		try {
			stmt = conn.createStatement();

			String sql = "select * from t_ev_cus_tor_poll" + " where left(ectp_date , 4) = left(now() , 4) and mi_id ='"+ miid+ "'";
			// mi_id

			System.out.println(sql + ":EvTorProcDao: voteBtn ����Ʈ����");
			rs = stmt.executeQuery(sql);

			if (rs.next()) { // �̹� ��ǥ�� ������ result = 0;
				result = 0;
				System.out.println("�̹� ��ǥ�� �� ����");
				System.out.println("���� ���а� ���� ���;���");
			} else {
				 sql = "select * from t_ev_cus_tor" + " where left(ect_date , 4) = left(now() , 4) and " +
						 " ect_isview = 'y' and ect_idx = '" + ectidx +"'";
				rs = stmt.executeQuery(sql);
				int pmcidx = 0;
				if (rs.next()) {
					result =1;
					pmcidx = rs.getInt("pmc_idx");
				}
				if (result == 1) {
					sql = "update t_ev_cus_tor set ect_vote = ect_vote + 1 ";
					sql += " where ect_idx = " + ectidx;
					
					System.out.println(sql + ":EvTorProcDao: �ڱⰡ ���ϴ� �����ǿ� ��ǥ�ϴ� ����");
					result = stmt.executeUpdate(sql);
					// �����ǿ� ��ǥ�ϱ� ������Ʈ ����
					
					sql = "insert into t_ev_cus_tor_poll  " + " (ect_idx, pmc_idx, mi_id) values ('" + ectidx
							+ "', '" + pmcidx + "', '" + miid + "')";
					System.out.println(sql + ":EvTorProcDao:��ǥ�� ����� ����� ���� ���� ");

					result += stmt.executeUpdate(sql);
					// ��ǥ�ϱ� ���� �߰� ����
				}
			}
		} catch (Exception e) {
			System.out.println("EvTorProcDaoŬ����:voteBtn()����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return result;
	}
	
	public EvCusTorPoll isvote(String miid) { // ##############################
		// ��ǥ�ߴ��� Ȯ���ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		EvCusTorPoll ectp = new EvCusTorPoll();

		try {
			stmt = conn.createStatement();
			String sql = "select * from t_ev_cus_tor_poll" + " where left(ectp_date , 4) = left(now() , 4) and mi_id ='"+ miid+ "'";
			// mi_id
			System.out.println(sql + ":��ǥ���� Ȯ�� ����");
			rs = stmt.executeQuery(sql);
			if (rs.next()) { // �̹� ��ǥ�� ������ result = 0;
				ectp.setEctp_idx(rs.getInt("ectp_idx"));
				ectp.setEct_idx(rs.getInt("ect_idx"));
				ectp.setPmc_idx(rs.getInt("pmc_idx"));
				ectp.setMi_id(rs.getString("mi_id"));
				ectp.setEctp_date(rs.getString("ectp_date"));
			} else {
				ectp = null;
			}
		} catch (Exception e) {
			System.out.println("EvTorProcDaoŬ����:IsBtn()����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return ectp;
	}
	public int didTor(String miid, String date) {
		//ȸ���� ���̵�� ������('2022-02')�� �������� t_ev_cus_tor ���̺� ���� �ִ��� 
		//���η� ��ʸ�Ʈ�� �ش� ���� �����ߴ��� Ȯ���ϴ� �޼ҵ� 
		int result = 0;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select count(*) from t_ev_cus_tor where left(ect_date, 7)='"
					+ date + "' and mi_id = '" + miid+ "';";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			System.out.println(sql+"didTor");
			if (rs.next())
				result = rs.getInt(1);
			// �÷��� ��� �ε��� ��ȣ �׳� ��
			// select count�ϴ� �ſ��� ��� �˻� ���ص� ��.

		} catch (Exception e) {
			System.out.println("EvTorProcDaoŬ����:didTor()����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}

		return result;
	}
	public ArrayList<ProductCustom> getbuyCustomList(String miid){
		//��ʸ�Ʈ ���� ���� ȸ���� �����ϰ� �ø� ���� ���� Ŀ���� ��ī�� ����Ʈ�� �������� �޼ҵ� 
		Statement stmt = null; 
		ResultSet rs = null;
		ArrayList<ProductCustom> customList = new ArrayList<ProductCustom>();
		ProductCustom pc = null;
		
		try {
			String sql = "select a.*, b.pi_id, b.pi_name, b.pi_img1 "
					+ " from t_product_ma_custom a, t_product_info b "
					+ " where a.mi_id = '"
					+ miid
					+ "'and a.pi_id = b.pi_id  and a.pmc_isbuy = 'y' and a.pmc_idx "
					+ " not in(select pmc_idx from t_ev_cus_tor);";
			//���̵�� Ŀ���Ҹ�ī�� �˻��ϴµ�
			//��ʸ�Ʈ�� �ø����� ������, ������ ��ī�ո�  �ĺ��� ������ �� ���� 
			System.out.println(sql + ": /EvTorProcDao/getbuyCustomList");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);	
			
			while(rs.next()) {
				pc = new ProductCustom();
				
				pc.setPmc_idx(rs.getInt("pmc_idx")); //Ŀ���� ��ī�� �ε���
				pc.setPi_name(rs.getString("pi_name")); //���̸�
				pc.setPi_img1(rs.getString("pi_img1")); //���̹���
				pc.setPi_id(rs.getString("pi_id")); //����ǰ���̵�
				pc.setMi_id(miid); //ȸ�� ���̵� 
				pc.setPmc_name(rs.getString("pmc_name"));
				pc.setPmc_sugar(rs.getInt("pmc_sugar"));
				pc.setPmc_vg(rs.getString("pmc_vg"));
				pc.setPmc_pl(rs.getString("pmc_pl"));
				pc.setPmc_tp1(rs.getString("pmc_tp1"));
				pc.setPmc_tp2(rs.getString("pmc_tp2"));
				pc.setPmc_date(rs.getString("pmc_date"));
				pc.setPmc_price(rs.getInt("pmc_price"));
				pc.setPmc_isview(rs.getString("pmc_isview"));
				pc.setPmc_isbuy(rs.getString("pmc_isbuy"));		
				
				if(rs.getString("pmc_tp1")!=null && !rs.getString("pmc_tp1").equals("")) {
					String tpname = getToppingName(rs.getString("pmc_tp1"));
					pc.setTpname1(tpname); 
				}
				if(rs.getString("pmc_tp2")!=null && !rs.getString("pmc_tp2").equals("")) {
					String tpname = getToppingName(rs.getString("pmc_tp2"));
					pc.setTpname2(tpname);
				}
					
				customList.add(pc);
			}

		}catch(Exception e){
			System.out.println("EvTorProcDao��  getbuyCustomList�޼ҵ忡�������߻�");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt);
		}
		return customList;	
	}

	public String getToppingName(String tpid) {		
		//���� ���̵� �����ͼ� ���� �̸��� �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		String tpname = "";
		try {
			String sql = "select pmt_name from t_product_ma_topping where "
					+ "pmt_id = '"+ tpid+ "'";
		
			stmt = conn.createStatement();
			System.out.println(sql + "EvTorProcDao:getToppingName");
			rs = stmt.executeQuery(sql); 
			//�� �� �ϱ� ���� �ȵ����� �� 
			if(rs.next()) {
				tpname = rs.getString(1);		
			}
		
		}catch(Exception e) {
			System.out.println("EvTorProcDao:getToppingName()����");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}		
		return tpname;

	}
	public int torInsert(String miid, EvCusTor ect){
		//��ʸ�Ʈ ���� ����ϴ� �޼ҵ�
		int result = 0;
		Statement stmt = null;
		try {
			String sql = "insert into t_ev_cus_tor(pmc_idx, mi_id, ect_img1, ect_title, "
					+ " ect_content ) values ( "
					+ ect.getPmc_idx()
					+ ",'"
					+ miid
					+ "', '"
					+ ect.getEct_img1()
					+ "', '"
					+ ect.getEct_title()
					+ "'  , '"
					+ ect.getEct_content()
					+ "')";
					
			System.out.println(sql + "EvTorProcDao:torInsert");	
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		
		}catch(Exception e) {
			System.out.println("EvTorProcDao:torInsert()����");
			e.printStackTrace();
		}finally {
			close(stmt); 
		}		
		return result;

	}
	
	public ProductCustom getCustomInfo(int pcidx) {
		//ȸ���� Ŭ���� Ŀ���Ҹ�ī���� �󼼺�(Ŀ���Ҹ�ī���� ��� ����, ��, ���θ�)�� �������� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		ProductCustom pc = null;
		try {
			String sql = "select a.*, b.pi_name, b.pi_img1 "
					+ " from t_product_ma_custom a, t_product_info b "
					+ " where a.pi_id = b.pi_id and pmc_idx=" + pcidx;
		
			stmt = conn.createStatement();
			System.out.println(sql + "ProductCustomDao:getCustomInfo");
			rs = stmt.executeQuery(sql); 
			//�� �� �ϱ� ���� �ȵ����� �� 
			if(rs.next()) {
				pc = new ProductCustom();
				
				pc.setPmc_idx(pcidx); //Ŀ���� ��ī�� �ε���
				pc.setPi_name(rs.getString("pi_name")); //���̸�
				pc.setPi_img1(rs.getString("pi_img1")); //���̹���
				pc.setPi_id(rs.getString("pi_id")); //����ǰ���̵�
				pc.setPmc_name(rs.getString("pmc_name")); //����ڼ�������
				pc.setPmc_sugar(rs.getInt("pmc_sugar"));
				pc.setPmc_vg(rs.getString("pmc_vg"));
				pc.setPmc_pl(rs.getString("pmc_pl"));
				pc.setPmc_tp1(rs.getString("pmc_tp1"));
				pc.setPmc_tp2(rs.getString("pmc_tp2"));
				pc.setPmc_date(rs.getString("pmc_date"));
				pc.setPmc_price(rs.getInt("pmc_price"));
				pc.setPmc_isview(rs.getString("pmc_isview"));
				pc.setPmc_isbuy(rs.getString("pmc_isbuy"));		
				pc.setPmc_img(rs.getString("pmc_img"));	
				
				if(rs.getString("pmc_tp1")!=null && !rs.getString("pmc_tp1").equals("")) {
					String tpname = getToppingName(rs.getString("pmc_tp1"));
					pc.setTpname1(tpname); 
				}
				if(rs.getString("pmc_tp2")!=null && !rs.getString("pmc_tp2").equals("")) {
					String tpname = getToppingName(rs.getString("pmc_tp2"));
					pc.setTpname2(tpname);
				}
			}
		
		}catch(Exception e) {
			System.out.println("ProductCustomDao:getCustomInfo()����");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}		
		return pc;
	}
	
	
	
	
	
	
	
	
	
}