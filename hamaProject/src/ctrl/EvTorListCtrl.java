package ctrl;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import java.util.*;
import vo.*;
import java.net.*;
import java.time.LocalDate;

// ��ʸ�Ʈ ����Ʈ ���
@WebServlet("/ev_tor_list")
public class EvTorListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public EvTorListCtrl() {super();}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request , response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		doProcess(request , response);
		}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String kind = request.getParameter("kind"); 
		
		
		String where = " where ect_isview='y' ";   //�Խñ� ���̴��� �Ⱥ��̴��� �뵵()-�� �� �ش�Ǵ� ����

		
		//��¥�� ����������(��¥�� a�� b�� �� �������� ����)
		LocalDate today = LocalDate.now();
		int	cYear = today.getYear(); // ���� ����
		int cMonth = today.getMonthValue();// ���� �� ��
		String month = "";
		if(cMonth<10) {
			month = "0"+Integer.toString(cMonth);
		}else {
			month = Integer.toString(cMonth);
		}
		String date = Integer.toString(cYear)+"-"+month; 
		System.out.println(date+"EvTorListCtrl");//2023-02 �̷��� ����
		
		
		
		//���������� ��ǥ��(ect_vote), �ֱټ� (ect_date)
		String orderBy = ""; 
		String o = request.getParameter("o");
		if (o == null || o.equals(""))	o = "a"; 

		switch (o) {
		case "a" :	// ��ǥ����
			orderBy = " order by ect_vote desc ";	break;
		case "b" :	// �ֽż�
			orderBy = " order by ect_date desc ";	break;
	}
		

		EvTorListSvc evTorListSvc = new EvTorListSvc();
		//rcnt = evTorListSvc.getTorListCount(where); rcnt���� ����¡���������
		
		//��ʸ�Ʈ ����Ʈ
		ArrayList<EvCusTor> torList = evTorListSvc.getTorList(kind, date, orderBy);

		request.setAttribute("torList", torList);

		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("event/ev_tor_list.jsp");
		dispatcher.forward(request, response);
		
		
	}
}
