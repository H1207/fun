package ctrl;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/product_custom_form")
public class ProductCustomFormCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductCustomFormCtrl() { super(); }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//1. �α��ΰ˻�
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");	
		if(loginInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�α��� ���� ����Ͻ� �� �ֽ��ϴ�.');");
			out.println("location.replace('login_form.jsp?url=product_custom_form');");
			out.println("</script>");
			out.close();
		}
		String miid = loginInfo.getMi_id();
		
				
		//2.Ŀ���Ҹ�ī��(view=y)�ΰ� ���̵�� �����˻� 10���̸��̸� history.back
		ProductCustomSvc productCustomSvc = new ProductCustomSvc();
		int cnt = productCustomSvc.countCustom(miid);
		if(cnt>=10) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��ī�� �����Ǵ� 10�������� ���� �� �ֽ��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
			
		
		//3-1 ���θ���Ʈ�������� ���̵�,�̸�,�ε��� (+�Խÿ�������)		
		ArrayList<ProductTopping> toppingList = 
				productCustomSvc.getToppingList();
		request.setAttribute("toppingList", toppingList);
		
		
		//3-2 ��ī���� ����Ʈ �������� ��������	
		ArrayList<ProductInfo> macList = 
				productCustomSvc.getMacList();
		request.setAttribute("macList", macList);
		
		
		//4. 10�� �̸��̶�� Ŀ���� ��ī�� ����� ������ ������
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/product/product_custom_form.jsp");
		dispatcher.forward(request, response);


	
	}

}
