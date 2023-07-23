package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.util.*;
import java.net.*;
import svc.*;
import vo.*;

//留덉씠�럹�씠吏��쓽 �룷�씤�듃 �궡�뿭
@WebServlet("/point_view")
public class PointViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PointViewCtrl() {  super(); }
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		request.setCharacterEncoding("utf-8");
	
		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0, spage = 0; 
		// �쁽�옱 �럹�씠吏� 踰덊샇, �럹�씠吏� �겕湲�, 釉붾줉 �겕湲�, �젅肄붾뱶(�긽�뭹) 媛쒖닔, �럹�씠吏� 媛쒖닔, �떆�옉�럹�씠吏� �벑�쓣 ���옣�븷 蹂��닔�뱾

		
		// 濡쒓렇�씤 寃��궗
    	HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		
		if(loginInfo == null) {	// 濡쒓렇�씤�씠 �븞�릺�뼱 �엳�쑝硫�
			response.setContentType("text/html charset=utf-8 ");
			PrintWriter out= response.getWriter();
			out.println("<script>");
			out.println("alert('濡쒓렇�씤 �씠�썑 �궗�슜�븯�떎 �닔 �엳�뒿�땲�떎.');");
			out.println("location.replace(login_form.jsp?url=point_view)");
			out.println("</script>");
			out.close();
		}
		
		String miid = loginInfo.getMi_id();
		if(request.getParameter("cpage") != null) { //�쁽�옱�럹�씠吏�媛� 鍮꾩뼱�엳吏��븡�쑝硫� �삎蹂��솚
			cpage = Integer.parseInt(request.getParameter("cpage"));
			}
		
		MyShoppingSvc  myShoppingSvc = new MyShoppingSvc ();// �꽌鍮꾩뒪 �깮�꽦
		
		int totalPoint = 0;// �쁽�옱 蹂댁쑀 �룷�씤�듃
		totalPoint = myShoppingSvc.getPointSum(miid);
		rcnt = myShoppingSvc.getPointCount(miid );
		// 寃��깋�맂 �룷�씤�듃 珥� 媛쒖닔濡� �쟾泥� �럹�씠吏��닔瑜� 援ы븷 �븣 �궗�슜
		ArrayList<MemberPoint> pointList = myShoppingSvc.getPointList(miid, cpage, psize);
		//紐⑸줉�솕硫댁뿉�꽌 蹂댁뿬以� �룷�씤�듃 紐⑸줉�쓣 ArrayList<MemberPoint>�삎�쑝濡� 諛쏆븘�샂
		
		MemberPoint mp = new MemberPoint();//�쉶�썝�룷�씤�듃 �궡�뿭 �씤�뒪�꽩�뒪 �깮�꽦
		mp.setMp_totalPoint(totalPoint);
		// �쉶�썝�룷�씤�듃 �궡�뿭�뿉 �쁽�옱 蹂댁쑀 �룷�씤�듃 �뀑�똿
		

		PageInfo pageInfo = new PageInfo();
		pageInfo.setBsize(bsize);		pageInfo.setCpage(cpage);
		pageInfo.setPcnt(pcnt);			pageInfo.setPsize(psize);
		pageInfo.setRcnt(rcnt);			pageInfo.setSpage(spage);
		// �럹�씠吏� 愿��젴 �젙蹂대뱾�쓣 pageInfo �씤�뒪�꽩�뒪�뿉 ���옣
System.out.println(" PageInfo : " + pageInfo);


	      if(mp == null) {   // 蹂댁뿬以� �궡�뿭�씠 �뾾�쑝硫�
	          response.setContentType("text/html; charset=utf-8"); 
	          PrintWriter out = response.getWriter();
	          out.println("<script>");
	          out.println("alert('�룷�씤�듃 �궡�뿭�씠 �뾾�뒿�땲�떎.');");
	          out.println("</script>");
	          out.close();
	       }else {      // 蹂댁뿬以� �궡�뿭�씠 �엳�쑝硫�
	    	   request.setAttribute("pageInfo", pageInfo);//�럹�씠吏�
	      	   request.setAttribute("pointList", pointList);//�쉶�썝 �룷�씤�듃 �궡�뿭
	      	   request.setAttribute("mp", mp);//�쁽�옱 蹂댁쑀 �룷�씤�듃
	      	   // dispatcher 諛⑹떇�쑝濡� view瑜� 蹂댁뿬二쇰�濡� request媛앹껜�뿉 �븘�슂�븳 �젙蹂대�� �떞�쓬
	          
	          RequestDispatcher dispatcher = request.getRequestDispatcher("my/my_point_view.jsp");
	          dispatcher.forward(request, response);
	      	// 留덉씠�럹�씠吏� �룷�씤�듃�궡�뿭�쑝濡� �씠�룞
		       
	          
	       }
	           

  	   
  	   
    

	}
    
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
