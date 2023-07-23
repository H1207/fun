<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp"%>
<link rel="stylesheet" href="../_inc/css/common.css">
<link rel="stylesheet" href="../_inc/css/header.css">
<%@ include file="../_inc/my_menu.jsp" %>
<%
	request.setCharacterEncoding("utf-8");

	ArrayList<MemberPoint> pointList = (ArrayList<MemberPoint>) request.getAttribute("pointList");// 포인트 내역
	MemberPoint mp = (MemberPoint) request.getAttribute("mp"); // 하나의 포인트 내역 담김
	//포인트 정보 받아오기

	String args = "";
	int bsize = 0, cpage = 0, psize = 0, pcnt = 0, spage = 0, rcnt = 0;
	PageInfo pageInfo = new PageInfo();
	pageInfo = (PageInfo) request.getAttribute("pageInfo"); // 페이지 정보
	if (request.getAttribute("pageInfo") != null) {

		bsize = pageInfo.getBsize();
		cpage = pageInfo.getCpage();
		psize = pageInfo.getPsize();
		pcnt = pageInfo.getPcnt();
		spage = pageInfo.getSpage();
		rcnt = pageInfo.getRcnt();
		args = "?cpage=" + cpage; //링크에서 사용할 쿼리스트링 	
	}
	// 페이지 정보 받아오기
	System.out.println("pageInfo" + pageInfo);
	System.out.println("pointList" + pointList);
	System.out.println("mp" + mp);

	String su = ""; // 포인트 사용('u')과 적립('s')구분
	if (mp.getMp_su() != null && "".equals(mp.getMp_su())) {
		su = mp.getMp_su();
	} else if (!"u".equals(mp.getMp_su())) {
		su = "포인트 적립";
	} else if (!"s".equals(mp.getMp_su())) {
		su = "포인트 사용";
	}

	int upoint = 0, spoint = 0;
	int totalPoint = 0;
	totalPoint = mp.getMp_totalPoint();
	//사용포인트,  적립 포인트, 현재 보유  포인트
%>
<style>
.container {
	display: flex;
	width: 60%;
	margin-left: 180px;
    margin-top: -500px;
}

.my-point {
	width: 100%;
}

.point-now {
	border: 1px solid rgb(102, 102, 102);
	width: 50%;
	height: 130px;
	border-right: none;
	padding: 10px;
	background-color: f6bc49;
}

.point-now>h2 {
	text-align: right;
	font-size: 50px;
	height: 100px;
}

.point-history {
	width: 50%;
}

.point-history>p {
	border: 1px solid rgb(102, 102, 102);
	height: 30px;
	line-height: 30px;
	padding-left: 5px;
	background-color: bisque;
}

.point-history>ul {
	border: 1px solid rgb(102, 102, 102);
	height: 100px;
	border-top: none;
	line-height: 32px;
	padding: 5px;
}

.point-history ul li span {
	float: right;
}

.pageing {
    width : 100%;
    margin-bottom : 400px;
	padding-top: 20px;
}
</style>


<div class="my-point">
	<form name="frmPoint" method="post" action="/point_view" width="700">
		<div class="container">
			<div class="point-now">
				<p>사용가능 포인트</p>
				<h2><%=totalPoint%></h2>
			</div>
			<div class="point-history">
				<p>포인트 적립 및 사용 내역</p>
				<!-- 포인트 정보 목록이 있다면 -->
				<%
					if (pointList.size() > 0) {
						int num = rcnt - (psize * (cpage - 1));

						for (int i = 0; i < pointList.size(); i++) {
							mp = pointList.get(i); // 담기
				%>
				<ul>

					<li><%=mp.getMp_date()%></li>
					<!-- 적립 / 사용날짜 -->
					<%
						if (!"s".equals(mp.getMp_su())) {
					%>
					<!-- 포인트 사용일 때  -->
					<li><%=su%> <span><%=mp.getMp_point() * -1%></span></li>

					<%
						} else if (!"u".equals(mp.getMp_su())) {
					%>
					<!-- 포인트 적립일 때 -->
					<li><%=su%> :<%=mp.getMp_point()%> <br /> <span>잔여포인트
							: <%=totalPoint%></span></li>

					<%
						spoint += mp.getMp_point();
								}
					%>
				</ul>
				<%
					num--;
							totalPoint += spoint + upoint; //num 목록 줄어들어야함 
						}

					}
				%>
				<div class="pageing" align="center">
					<%
						if (rcnt > 0) { // 게시글이 있으면 - 페이징 영역을 보여줌
							String lnk = "point_view?cpage=";
							pcnt = rcnt / psize;
							if (rcnt % psize > 0)
								pcnt++; // 전체 페이지 수(마지막 페이지 번호)
							if (cpage == 1) {
								out.println("&lt;&lt; &nbsp;&nbsp;&lt; &nbsp;&nbsp;&nbsp;");
							} else {
								out.println("<a href = '" + lnk + 1 + "'> &lt;&lt; </a>&nbsp;&nbsp;&nbsp;");
								out.println("<a href = '" + lnk + (cpage - 1) + "'> &lt; </a>&nbsp;&nbsp;");
							}
							spage = (cpage - 1) / bsize * bsize + 1; // 현재 블록에서의 시작 페이지 번호
							for (int i = 1, j = spage; i <= bsize && j <= pcnt; i++, j++) {
								// i : 블록에서 보여줄 페이지의 개수많큼 루프를 돌릴 조건으로 사용되는 변수
								// j : 실제 출력할 페이지 번호로 전체 페이지 개수(마지막 페이지 번호)를 넘지 않게 사용해야 함	
								if (cpage == j) {
									out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
								} else {
									out.println("&nbsp;<a href='" + lnk + j + "'>");
									out.println(j + "</a>&nbsp;");
								}
							}
							if (cpage == pcnt) {
								out.println("&nbsp;&nbsp;&nbsp &gt; &nbsp;&nbsp; &gt;&gt; ");
							} else {
								out.println("<a href = '" + lnk + (cpage + 1) + "'>&nbsp;&nbsp;&nbsp; &gt; </a>");
								out.println("<a href = '" + lnk + pcnt + "'>&nbsp;&nbsp; &gt;&gt; </a>");
							}
						}
					%>
				</div>
			</div>

		</div>

	</form>
</div>




<%@ include file="../_inc/footer.jsp"%>