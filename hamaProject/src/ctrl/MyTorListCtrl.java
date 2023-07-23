package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.util.*;
import java.net.*;
import java.time.*;

import svc.*;
import vo.*;

@WebServlet("/my_tor_list")
public class MyTorListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public MyTorListCtrl() {super();}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
	//	String kind = request.getParameter("kind"); // kind=b일때 명예의 전당
		
		//세션 가져와서 로그인 안되어있으면 로그인 하게 한 뒤에 ctrl로 다시 돌아오게 함 
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		if(loginInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 후 사용하실수있습니다.');");
			out.println("location.replace('login_form.jsp?url=my_tor_list');");
			out.println("</script>");
			out.close();	
		}

		String miid = loginInfo.getMi_id(); //세션의 아이디 뽑아오기
	
	//	String where = " where ect_isview='y' ";   //마이페이지에서 게시여부 'n'상태도 보이게할거라 주석처리

		
	
		
		
		//정렬조건은 득표순(ect_vote), 최근순 (ect_date)
		String orderBy = ""; 
		String o = request.getParameter("o");
		if ( o == null || "".equals(o) ) {	o = "a"; }

		switch (o) {
		case "a" :	// 득표수순
			orderBy = " order by ect_vote desc ";	break;
		case "b" :	// 최신순
			orderBy = " order by ect_date desc ";	break;
	}
		
		
		MyTorListSvc myTorListSvc = new MyTorListSvc(); // 서비스 생성
		ArrayList<EvCusTor> torList = myTorListSvc.getTorList(miid, orderBy);
		//해당 아이디인 회원이 만들어둔 커스텀 리스트 가져오기 	
		
		request.setAttribute("torList", torList);
		//어레이리스트 customList에 담아서 리퀘스트에 담아 보내준다. jsp뷰로 볼 수 있게 포워드로 보냄. 그러니까 리퀘스트에 담는다.
		//sendRedirect로 보내면 url이 보내감.파일자체가 확 넘어가니까 request를 안가져감
		//sendRedirect는 url에 쿼리스트링으로 담아가는데, 어레이리스트를 쿼리스트링으로 가져갈 수 없으니 디스패처를 이용해서 이동한다. 
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("my/my_tor_list.jsp"); 
		dispatcher.forward(request,response);
		
	          
	}
	
	
	
	
	
	
	
	
	



}
