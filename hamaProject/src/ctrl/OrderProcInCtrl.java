package ctrl;

import static db.JdbcUtil.commit;
import static db.JdbcUtil.rollback;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/order_proc_in")
public class OrderProcInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public OrderProcInCtrl() {super();}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		
		if(loginInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 후 이용해 주시기 바랍니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		String miid = loginInfo.getMi_id(); 
		String kind = request.getParameter("kind");
		int total = Integer.parseInt(request.getParameter("total"));

		String orderName = request.getParameter("order_name");
		String orderPhone = request.getParameter("order_phone");
		String rname = request.getParameter("receiveName");
		String phone = request.getParameter("receivePhone");
		String zip = request.getParameter("receiveZip");
		String addr1 = request.getParameter("receiveAdd1");
		String addr2 = request.getParameter("receiveAdd2");
		String receiveMemo = request.getParameter("receiveMemo");
		String receiveDate = request.getParameter("receiveDate");
		String payment = request.getParameter("order_payment");
		String where = request.getParameter("where");


		OrderInfo oi = new OrderInfo();
		oi.setOi_sender(orderName);
		oi.setOi_sephone(orderPhone);
		oi.setMi_id(miid);
		oi.setOi_name(rname); //�닔痍⑥씤�씠由�
		oi.setOi_phone(phone);
		oi.setOi_addr1(addr1);
		oi.setOi_addr2(addr2);
		oi.setOi_zip(zip);
		oi.setOi_payment(payment);
		oi.setOi_pay(total);
		oi.setOi_memo(receiveMemo);
		oi.setOi_date(receiveDate);
		//#####################################################################################
		
		OrderProcInSvc orderProcInSvc = new OrderProcInSvc();
		String result = null, temp = "";
		if(kind.equals("c")) { 
			temp = request.getParameter("ocidxs");
			
		}else { 
			temp = where;
		}
		result = orderProcInSvc.orderInsert(kind, oi, temp);
		String[] arr = result.split(",");
		
		if(arr[1].equals(arr[2])) {
			
			response.sendRedirect("order_end?oiid=" + arr[0]);			
			
		}	
		else{

			
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('援щℓ媛� �젙�긽�쟻�쑝濡� 泥섎━�릺吏� �븡�븯�뒿�땲�떎. \\n 怨좉컼 �꽱�꽣�뿉 臾몄쓽�븯�꽭�슂. ');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		
	}

}








