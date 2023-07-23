package ctrl;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.websocket.SendResult;

import svc.*;
import vo.*;

@WebServlet("/product_view")
public class ProductViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductViewCtrl() {super();}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String piid = request.getParameter("piid"); //��ǰ���̵�

		ProductViewSvc productViewSvc = new ProductViewSvc();

		int result = productViewSvc.readUpdate(piid);	// ��ǰ ��ȸ�� ����
		ProductInfo pi = productViewSvc.getProductInfo(piid); //���̵�� ��������
		
		if (pi == null) { //�˻� null�̸� ƨ���. 
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��ǰ ������ �����ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		request.setAttribute("pi", pi);
		
	
			//��ǰ ���̵�� �����ؼ� ������ ����/jsp������ �ٸ���
		
			if(piid.equals("mc100")) {
				//Ŀ���Ҹ�ī����ȣ��
				response.sendRedirect("product_custom_form");
				
				
			}else if(piid.equals("cb101")||piid.equals("cb102")) { //5,10������ī��
				//���Դ� setctrl�� ���� 
				int temp=5;
				if(piid.equals("cb102")) temp = 10;
				System.out.println(temp + ": productviewctrl");
	
				RequestDispatcher dispatcher = 
						request.getRequestDispatcher("productsetview?boxSize="+temp);
					dispatcher.forward(request, response);
				
			}else { //����Ʈ���� �Ϲ� ��ǰ Ŭ���� �� �������� �̵� 
				RequestDispatcher dispatcher = 
						request.getRequestDispatcher("/product/product_view.jsp");
						dispatcher.forward(request, response);
			}
		
	}
			
}


