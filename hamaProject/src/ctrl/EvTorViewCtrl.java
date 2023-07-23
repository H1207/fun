package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/ev_tor_view")
public class EvTorViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public EvTorViewCtrl() {super();}
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//PageInfo pageInfo	= (PageInfo)request.getAttribute("pageInfo"); 
		//EvCusTorPoll ecpt = (EvCusTorPoll) request.getAttribute("ectp");// ��ʸ�Ʈ ��ǥ �޾ƿ���
		// EvCusTor ect = (EvCusTor) request.getAttribute("ect");//��ʸ�Ʈ ����Ʈ �޾ƿ���
		//String good = request.getParameter("good"); 	// view.jsp�� ajax :��ǥ�ϱ� ��� -> ������ ctrl ����
		//ArrayList<EvCusTor> torList = (ArrayList<EvCusTor>)request.getAttribute("torList");
			
	
		//System.out.println("ectpidx : " + request.getParameter("ectpidx"));
		
		
		
		
		//�� ��  :  �۹�ȣ�� Ŀ���� ��ȣ�� ������, �۹�ȣ�� �ش��ϴ� �� �����ְ�, Ŀ�����ε��� ������ ���λ��� �����ְ�
		//��� �ѱ��. 
		String kind = request.getParameter("kind");
		int ectidx = Integer.parseInt(request.getParameter("ectidx")); // ��ʸ�Ʈ �� ��ȣ
		int pmcidx= Integer.parseInt(request.getParameter("pmcidx")); // Ŀ���� ��ī�� �ε��� 
		
		
		//�ܼ� �俩�� �α��� �˻� ���ص� �� 
		
	
		
		// ������ �Խù� (����, ����)
		EvTorViewSvc evTorViewSvc = new EvTorViewSvc();
		EvCusTor ect = new EvCusTor();
		ect = evTorViewSvc.getEvCusTorInfo(ectidx);
		
		// ������ �Խù� (Ŀ���� ��ī�� ����)
		ProductCustom pc = new ProductCustom();
		pc = evTorViewSvc.getCustomInfo(pmcidx);	

		if (ect == null || pc == null) { // �۸�Ͽ��� ���� Ŭ�������� ��������ϴµ� null�ϼ������� ����׷��ٸ�
			// ������ �Խñ��� ������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�߸��� ��η� �����̽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();

		} else {

			request.setAttribute("ect", ect);// �Խñۺ����ְ�
			request.setAttribute("pc", pc); //Ŀ���Ҹ�ī�� �����ְ�

			
			
			System.out.println(pc.getTpname1());
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("event/ev_tor_view.jsp");
			dispatcher.forward(request, response);
			
		}	
		
		
	}	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request ,response );
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request ,response );
	}


}
