package vo;

//회원 포인트 내역을 저장하는 클래스
public class MemberPoint {
//mp_totalPoint 임의 추가 <이다희>
	private int mp_idx , mp_totalPoint , mp_point, mp_admin; 
	private String mi_id, mp_su, mp_desc, mp_detail, mp_date;
	
	public int getMp_idx() {
		return mp_idx;
	}
	public void setMp_idx(int mp_idx) {
		this.mp_idx = mp_idx;
	}
	
	
	public int getMp_totalPoint() {
		return mp_totalPoint;
	}
	public void setMp_totalPoint(int mp_totalPoint) {
		this.mp_totalPoint = mp_totalPoint;
	}
	
	public int getMp_point() {
		return mp_point;
	}
	public void setMp_point(int mp_point) {
		this.mp_point = mp_point;
	}

	public int getMp_admin() {
		return mp_admin;
	}
	public void setMp_admin(int mp_admin) {
		this.mp_admin = mp_admin;
	}
	public String getMi_id() {
		return mi_id;
	}
	public void setMi_id(String mi_id) {
		this.mi_id = mi_id;
	}
	public String getMp_su() {
		return mp_su;
	}
	public void setMp_su(String mp_su) {
		this.mp_su = mp_su;
	}
	public String getMp_desc() {
		return mp_desc;
	}
	public void setMp_desc(String mp_desc) {
		this.mp_desc = mp_desc;
	}
	public String getMp_detail() {
		return mp_detail;
	}
	public void setMp_detail(String mp_detail) {
		this.mp_detail = mp_detail;
	}
	public String getMp_date() {
		return mp_date;
	}
	public void setMp_date(String mp_date) {
		this.mp_date = mp_date;
	}
	
		
}
