package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import java.util.*;
import vo.*;
import java.net.*;
import java.time.*;



@WebServlet("/ev_tor_good")// ��ǥ�ϱ� ����� ���� ��Ʈ��
public class EvTorGoodCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public EvTorGoodCtrl() {  super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//String good = request.getParameter("good");
	//	EvCusTorPoll ecpt = (EvCusTorPoll)request.getAttribute("ecpt");
	//	LocalDate today = LocalDate.now();
		
		int ectidx = Integer.parseInt(request.getParameter("ectidx")); // ��ʸ�Ʈ �� ��ȣ ��������
		//int pmcidx = Integer.parseInt(request.getParameter("pmcidx")); //Ŀ�����ε��� �����;��ϰ�
		System.out.println("������� �۵�?1");
		
	//	 int year = today.getYear();
	//	 int month = today.getMonthValue();
		
	
		//�α���üũ  //�α��� ������ �������� �ǰ� ���ǿ� �ִ°� �̾ƿ��� 
		HttpSession session = request.getSession();
		MemberInfo mi = (MemberInfo)session.getAttribute("loginInfo");
		String miid = mi.getMi_id();
		
		//�����͸����
		/*
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		//�α����� �ȵ� �����̸� ƨ���
		if(mi == null) {
			out.println("<script>");
			out.println("alert('�߸��� ��η� �����̽��ϴ�. �α����ϼ���.');");
			out.println("</script>");
			out.close();
		}
		*/
		
		int result = 0;
		EvCusTorPoll ectp = new EvCusTorPoll();
		// � �ۿ� ��ǥ�ߴٶ� ��¥�� �������� ��(�α����� ����)
		// �˻��ؾߵǴ°�
		// ������ �̹� �޿� ��ǥ�� �ߴ���
		// ��ǥ���� �˻� ���� ������ alert(�����丮 ��) result ==1
		//�������� �� :���� int,  ȸ�����̵�, Ŀ���� �ε��� //����Ʈ ���� where mi_id -> ���ڵ尳�� 
		// ��ǥ �� ecpt_date (����) -> ���ڵ� ���� reuslt =1 return result; �̹��� ��ǥ�ϱ� �ȵ�
		/*
		if(request.getParameter("ectpidx") != null) {
			int ectidx = Integer.parseInt(request.getParameter("ectidx"));
			int pmcidx = Integer.parseInt(request.getParameter("pmcidx"));
			ectp.setEctp_idx(ectpidx);
			ectp.setMi_id(mi.getMi_id());
			ectp.setEct_idx(ectidx);
		//	ectp.setEctp_date(ectp_date);
		}
		//EvCusTorPoll�� �ν��Ͻ��� ��� 
		*/
		// evTorViewSvc�� didvote() �޼ҵ� 
		EvTorViewSvc evTorViewSvc = new EvTorViewSvc();
		result = evTorViewSvc.voteBtn(ectidx,miid);
		System.out.println("������� �۵�?");
		System.out.println(result);
		
		//################################
		/*
		if( result == 0) { // ��ǥ�ߴ��� �˻� �̹� ��ǥ�� ���� ���
			out.println("<script>");
			out.println("alert('�̹� ��ǥ�ϼ̽��ϴ�.');");
			out.println("location.href('history.back()');");
			out.println("</script>");
			out.close();	
		}
		if( result == 0) { // ��ǥ�� ������ ���
			
			request.setAttribute("ectidx", ectidx); 
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher("event/ev_tor_list.jsp"); // ��ǥ�� ������ ��� ��ʸ�Ʈ ����Ʈ�� ������
			dispatcher.forward(request, response);
		}
		

		
	
		result = evTorViewSvc.dovote( miid,  ectidx ); // Ŀ���� ��ʸ�Ʈ ��ǥ ���̺� insert(��ʸ�Ʈ �� ��ȣ ) �Ұ� // ��ǥ��..?
		String sql = "insert into where mi_id =  " + miid ;	// 
		
		
		
		result = evTorViewSvc.updatevote(  ectidx ); //2 ��ǥ�� ������Ʈ Ŀ������ʸ�Ʈ �۹�ȣ
		 sql = " update t_ev_cus_tor set ect_vote = ect_vote + 1 "; // ��ǥ�� ������Ʈ
		
		result += sql;

		//���� ����� (insert�� update �� ����) 
		 result = evTorViewSvc.voteBtn(ectp);
		 return result;
		 */
		//################################
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		//ev_tor_view.jsp��  [voteBtn()] ajax �� �� ����  

	}
	
}

