package ctrl;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/ev_tor_form")
public class EvTorFormCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public EvTorFormCtrl() { super(); }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");	// ���ڵ�
    	
		//��ʸ�Ʈ ������ �������ִ� ��Ʈ�ѷ�
		//������ �� ������ �����ֵ�,selectbox�� �ѷ��� ��ʸ�Ʈ�� �ø� �� �ִ� ��ī�� Ŀ���� ����Ʈ(�ű⼭ 1���ؼ� �ø��°�)���� 
		//���� ���̵� ���� �����ͼ� ����Ʈ ���·� �������ְ� ev_tor_form.jsp�� ���������
		
		//������
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// �α��ΰ˻�
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");	
		if(loginInfo == null) {
			
			out.println("<script>");
			out.println("alert('�α��� ���� ����Ͻ� �� �ֽ��ϴ�.');");
			out.println("location.replace('login_form.jsp?url=ev_tor_form');");
			out.println("</script>");
			out.close();
		}
		String miid = loginInfo.getMi_id();
		
		
		
		//1. �갡 ���� ������ ���� �ִ����� �˻�
		//��¥ �� �������� ��ġ�ϴ� ���̵� �ִ��� ����
		//���̵� ������ �ȵȴ� ->�̹� ��ʸ�Ʈ ����� �߰�,�����޿� ������ �� �ִٴ°� �˸�.
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
			
		EvTorFormSvc evTorFormSvc = new EvTorFormSvc();
		
		int result =  evTorFormSvc.didTor(miid, date);
		
		if(result!=0) { //0�� �ƴϸ� ������ �����߱� ������ ����������
			out.println("<script>");
			out.println("alert('이미 이번달의 레시피를 제출하셨습니다.');");
			out.println("location.replace('ev_tor_list?kind=a');");
			out.println("</script>");
			out.close();
		}
		
		
		
		
		//2. ����ϱ�� ����, Ŀ���Ҹ�ī�� ����Ʈ�� �������� ���̵� ����Ʈ�ڽ��� �ѷ��ְ� ������
		//�׸��� ������ Ŀ���Ҹ�ī�վ��̵� ���� �� ��������(�̰� ��ٱ��Ͽ��� �ּ� ����)

		// ���� ! ���̵�� �˻�, pmcidx�� ��ʸ�Ʈ�� �ö������ ������ϸ�/ ���ű�Ͽ� �־����  join�ؾ��ϳ׿�?
		// ���� �̸� �������� �͵� �Ƹ� �ؾ����ٵ� �� �𸣰��� 
	
		ArrayList<ProductCustom> customList = evTorFormSvc.getbuyCustomList(miid);
		request.setAttribute("customList", customList);
		
		

		if(customList.size()<=0) { //산 게 없을 경우, 
			out.println("<script>");
			out.println("alert('구매한 커스텀 마카롱만 등록할 수 있습니다.');");
			out.println("location.replace('ev_tor_list?kind=a');");
			out.println("</script>");
			out.close();
		}else {
	
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/event/ev_tor_form.jsp");
				dispatcher.forward(request, response);
		
		}
		
	}

}
