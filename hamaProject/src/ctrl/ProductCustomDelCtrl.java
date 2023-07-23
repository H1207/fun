package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/product_custom_del")
public class ProductCustomDelCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductCustomDelCtrl() { super();}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		//Ŀ���� ��ī���� �ε��� ��ȣ 
		String pmcidx = request.getParameter("pmcidx");
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");

		//�α��� ������ �������� �ǰ� ���ǿ� �ִ°� �̾ƿ��� 
		if(loginInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�α��� ���� ����Ͻ� �� �ֽ��ϴ�.');");
			out.println("location.replace('login_form.jsp?url=product_custom_list');");
			out.println("</script>");
			out.close();
		}
		String miid=loginInfo.getMi_id();

		ProductCustomDelSvc productCustomDelSvc = new ProductCustomDelSvc();
		int result = productCustomDelSvc.customDelete(miid, pmcidx);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		//result ������ֱ�>�׷� ajax�� ����.
	}
}
