package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/member_form_up")
public class MemberFormUpCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MemberFormUpCtrl() { super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String kind = request.getParameter("kind");
		System.out.println(kind);
		String update = "";
		
		
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setMi_id(loginInfo.getMi_id());
		memberInfo.setMi_pw(loginInfo.getMi_pw());
		
		if(kind.equals("u")) {String new_pw = request.getParameter("new_pw");
		memberInfo.setMi_phone("010-" + request.getParameter("p2") + "-" + request.getParameter("p3"));
		memberInfo.setMi_email(request.getParameter("e1").trim() + "@" + request.getParameter("e3").trim());
		
		update += " mi_pw = '" + new_pw + "', " +
				" mi_phone = '" + memberInfo.getMi_phone() + "', " +
				" mi_email = '" + memberInfo.getMi_email() + "' ";
		}else{ update = " mi_status = 'c' "; }
		
		MemberProcUpSvc memberProcUpSvc = new MemberProcUpSvc();
		
		int result = memberProcUpSvc.memberProcUp(memberInfo, update);
		if(result == 1) {	// ���������� ȸ������ ������ �̷�� ������
			
			if(kind.equals("u")) {
				loginInfo.setMi_phone(memberInfo.getMi_phone());
				loginInfo.setMi_email(memberInfo.getMi_email());
				
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('�������� �Ϸ��߽��ϴ�.');");
				out.println("location.href='/hamaProject/my/my_info_view.jsp';");
				out.println("</script>");
				out.close();

			}else { //ȸ�� Ż���� ���
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('����Ʈ�� Ż���߽��ϴ�.');");
				out.println("location.href='/hamaProject/logout.jsp';");
				out.println("</script>");
				out.close();
			}
			
		}else {	// ���� ���� ����
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('����ó���� �����߽��ϴ�.\\n�ٽ� �õ��ϼ���.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		 
	      
	}

}







