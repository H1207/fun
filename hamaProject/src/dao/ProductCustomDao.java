package dao;
import static db.JdbcUtil.*;  
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.*;
import vo.*;
import svc.*;


public class ProductCustomDao {
	private static ProductCustomDao productCustomDao;
	private Connection conn;
	private ProductCustomDao() {}

	public static ProductCustomDao getInstance() {
		if (productCustomDao == null)	productCustomDao = new ProductCustomDao();
		return productCustomDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList<ProductCustom> getCustomList(String miid){
		//ȸ���� ���� Ŀ���� ��ī�� ����Ʈ�� �������� �޼ҵ�
		Statement stmt = null; 
		ResultSet rs = null;
		ArrayList<ProductCustom> customList = new ArrayList<ProductCustom>();
		ProductCustom pc = null;
		
		try {
			String sql = " select  a.*, b.pi_name , b.pi_img1, b.pi_isview "
					+ " from t_product_ma_custom a, t_product_info b "
					+ " where  a.pi_id = b.pi_id and"
					+ " a.mi_id = '"+ miid +"' and " 
					+ " a.pmc_isview = 'y' and b.pi_isview = 'y' order by pmc_date ";
			
			System.out.println(sql + ": /ProductCustomDao/getCustomList");
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
				customList.add(pc);
			}

		}catch(Exception e){
			System.out.println("ProductCustomDao��  getCustomList�޼ҵ忡�������߻�");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt);
		}
		return customList;	
	}
		
	public ProductCustom getCustomInfo(int pcidx) {
		//ȸ���� Ŭ���� Ŀ���Ҹ�ī���� �󼼺�(Ŀ���Ҹ�ī���� ��� ����, ��, ���θ�)�� �������� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		ProductCustom pc = null;
		try {
			String sql = "select a.*, b.pi_name, b.pi_img1 "
					+ " from t_product_ma_custom a, t_product_info b "
					+ " where a.pi_id = b.pi_id and a.pmc_isview = 'y' and "
					+ " pmc_isview = 'y' and pmc_idx=" + pcidx;
		
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
			}
		
		}catch(Exception e) {
			System.out.println("ProductCustomDao:getCustomInfo()����");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}		
		return pc;
	}
	
	public int countCustom(String miid) {
		//�ش� ȸ���� Ŀ���� ��ī�� �� ���� ��������� ���� �������� �޼ҵ� 
		int cnt = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) "
					+ " from t_product_ma_custom "
					+ " where mi_id = '"+miid+"' "
					+ "and pmc_isview = 'y'";
			stmt = conn.createStatement();
			System.out.println(sql + "ProductCustomDao:countCustom");
			rs = stmt.executeQuery(sql); 
			//�� �� �ϱ� ���� �ȵ����� �� 
			if(rs.next()) {
				cnt = rs.getInt(1);		
			}
		
		}catch(Exception e) {
			System.out.println("ProductCustomDao:countCustom()����");
			e.printStackTrace();
		}finally {
			close(rs); close(stmt); 
		}		
		return cnt;
	}
	
	public String getToppingName(String tpid) {		
			Statement stmt = null;
			ResultSet rs = null;
			String tpname = "";
			try {
				String sql = "select pmt_name from t_product_ma_topping where "
						+ "pmt_id = '"+ tpid+ "'";
			
				stmt = conn.createStatement();
				System.out.println(sql + "ProductCustomDao:getToppingName");
				rs = stmt.executeQuery(sql); 
				//�� �� �ϱ� ���� �ȵ����� �� 
				if(rs.next()) {
					tpname = rs.getString(1);		
				}
			
			}catch(Exception e) {
				System.out.println("ProductCustomDao:getToppingName()����");
				e.printStackTrace();
			}finally {
				close(rs); close(stmt); 
			}		
			return tpname;
	
		}
	
	public ArrayList<ProductInfo> getMacList(){
		//��ī��(��) ����Ʈ �������� �޼ҵ�... 
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<ProductInfo> productList = new ArrayList<ProductInfo>();
		ProductInfo pi = null;

		try {
			String sql = "select pi_id, pi_img1, pi_name, pc_id, " + 
				" pi_price from t_product_info  where pc_id = 'mc' and "
				+ " pi_isview = 'y' ";
				
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				pi = new ProductInfo();
				pi.setPi_id(rs.getString("pi_id"));
				pi.setPi_img1(rs.getString("pi_img1"));
				pi.setPi_name(rs.getString("pi_name"));
				pi.setPi_price(rs.getInt("pi_price"));
				pi.setPc_id(rs.getString("pc_id"));

				productList.add(pi);
			}

		} catch(Exception e) {
			System.out.println("ProductCustomDao Ŭ������ getProductList() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return productList;
	
	}
	
	
	public ArrayList<ProductTopping> getToppingList() {
		// ���θ���Ʈ 
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<ProductTopping> toppingList = new ArrayList<ProductTopping>();
			ProductTopping pt = null;

			try {
				String sql = "select * from t_product_ma_topping where pmt_isview = 'y'";
					
				System.out.println(sql);
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					pt = new ProductTopping();
					pt.setPmt_id(rs.getString("pmt_id"));
					pt.setPmt_idx(rs.getInt("pmt_idx"));
					pt.setPmt_name(rs.getString("pmt_name"));
					
					toppingList.add(pt);
				}

			} catch(Exception e) {
				System.out.println("ProductDao Ŭ������ getProductList() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}
			return toppingList;
		}
	
	
	
	
	public int customDelete(String miid, String pmcidx) {
		//�ش� ȸ���� ������ Ŀ������ �ε����� ���̵� ���� ���� ����(isview�� n���� ������Ʈ �ϴ� �޼ҵ�)
		int result = 0;
		Statement stmt = null;
		try {
			String sql = "update t_product_ma_custom set pmc_isview = 'n' "
					+ " where mi_id = '"+ miid +"' "
					+ " and pmc_idx = '" +  pmcidx +"'";
			System.out.println(sql + "ProductCustomDao:customDelete");	
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			System.out.println(result+"ProductCustomDao:customDelete() result");	
		
		}catch(Exception e) {
			System.out.println("ProductCustomDao:customDelete()����");
			e.printStackTrace();
		}finally {
			close(stmt); 
		}		
		return result;
	}

	public int customInsert(String miid, ProductCustom pc){
		//Ŀ���� �����Ǹ� ����ϴ� �޼ҵ� 
		int result = 0;
		Statement stmt = null;
		try {
			String sql = "insert into t_product_ma_custom(mi_id,   pmc_name,   "
					+ " pmc_sugar,   pmc_vg,   pmc_pl,   pi_id,   pmc_img,  "
					+ " pmc_tp1,   pmc_tp2) values("
					+ "'"+ miid+"',   "
					+ "'"+ pc.getPmc_name()+"',  "
					+ pc.getPmc_sugar()+", "
					+ "'"+ pc.getPmc_vg()+"', "
					+ "'"+ pc.getPmc_pl()+"',"
					+ " '"+ pc.getPi_id()+"' , "
					+ "'"+ pc.getPmc_img()+"',  "
					+ " '"+ pc.getPmc_tp1()+"'   ,"
					+ " '"+ pc.getPmc_tp2()+"');";
					
			System.out.println(sql + "ProductCustomDao:customInsert");	
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		
		}catch(Exception e) {
			System.out.println("ProductCustomDao:customInsert()����");
			e.printStackTrace();
		}finally {
			close(stmt); 
		}		
		return result;
	}
			
	

	
	
	
	


	
	
	
	

}



















