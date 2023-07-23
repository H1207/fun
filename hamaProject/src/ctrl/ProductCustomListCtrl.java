package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/product_custom_list")
public class ProductCustomListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductCustomListCtrl() { super(); }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//���� �����ͼ� �α��� �ȵǾ������� �α��� �ϰ� �� �ڿ� ctrl�� �ٽ� ���ƿ��� �� 
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		if(loginInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�α��� �� ����ϽǼ��ֽ��ϴ�.');");
			out.println("location.replace('login_form.jsp?url=product_custom_list');");
			out.println("</script>");
			out.close();	
		}

		String miid = loginInfo.getMi_id(); //������ ���̵� �̾ƿͼ�
	
		
		ProductCustomSvc productCustomSvc = new ProductCustomSvc();
		ArrayList<ProductCustom> customList = productCustomSvc.getCustomList(miid);
		//�ش� ���̵��� ȸ���� ������ Ŀ���� ����Ʈ �������� 	
		
		request.setAttribute("customList", customList);
		//��̸���Ʈ customList�� ��Ƽ� ������Ʈ�� ��� �����ش�. jsp��� �� �� �ְ� ������� ����. �׷��ϱ� ������Ʈ�� ��´�.
		//sendRedirect�� ������ url�� ������.������ü�� Ȯ �Ѿ�ϱ� request�� �Ȱ�����
		//sendRedirect�� url�� ������Ʈ������ ��ư��µ�, ��̸���Ʈ�� ������Ʈ������ ������ �� ������ ����ó�� �̿��ؼ� �̵��Ѵ�. 
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("product/product_custom_list.jsp");
		dispatcher.forward(request,response);
		
		
	}

}
