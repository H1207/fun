package ctrl;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/product_custom_view")
public class ProductCustomViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProductCustomViewCtrl() {super();  }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int pmcidx = Integer.parseInt(request.getParameter("pmcidx"));
		
		
		//�Ƿ����� �α��ΰ�
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		
		if(loginInfo == null ) { 
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�߸��� ��η� �����̽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();	
		}
		String miid = loginInfo.getMi_id();
		
		//ProductCustom������ �ش� Ŀ���Ҹ�ī���ε����� �ش��ϴ� ������ ��ƿ´�. 
		ProductCustomSvc productCustomSvc = new ProductCustomSvc();
		ProductCustom pc = productCustomSvc.getCustomInfo(pmcidx);
		
	
		if(pc==null) { //Ŭ���ؼ� ���°ǵ� ������ ������ 
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�߸��� ��η� �����̽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
			
		
		//������ �ε����� �����´�. null�̳� ���ڿ��� �ƴϸ� �ε����� ������ ������ 
		String tpid1=""; String tpid2="";
		//���� �̸� ������ 
		if(pc.getPmc_tp1()!=null && !pc.getPmc_tp1().equals("")){
			tpid1= pc.getPmc_tp1();
			String tpname1 = productCustomSvc.getToppingName(tpid1);
			request.setAttribute("tpname1", tpname1);
		}
		if(pc.getPmc_tp2()!=null && !pc.getPmc_tp2().equals("")){
			tpid2= pc.getPmc_tp2();
			String tpname2 = productCustomSvc.getToppingName(tpid2);
			request.setAttribute("tpname2", tpname2);
		}
		
		

			request.setAttribute("pc", pc); //���
			//����� ���䵵 ��ư������ٵ� ��� �� �� ����??
			
			RequestDispatcher dispatcher 
			= request.getRequestDispatcher("product/product_custom_view.jsp");
			dispatcher.forward(request, response);
			
		
		
	
		
	
	}

}












