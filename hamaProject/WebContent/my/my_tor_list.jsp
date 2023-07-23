<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../_inc/header.jsp" %>
<link rel="stylesheet" href="../_inc/css/common.css">
<link rel="stylesheet" href="../_inc/css/header.css">
<%@ include file="../_inc/my_menu.jsp" %>
<%
	request.setCharacterEncoding("utf-8");
	ArrayList<EvCusTor> torList = (ArrayList<EvCusTor>)request.getAttribute("torList");
	//내 토너먼트 글 보여줌
	
	EvCusTor ect = (EvCusTor)request.getAttribute("ect");
	
	String o = request.getParameter("o");// 정렬
	
	LocalDate today = LocalDate.now();
	int	cYear = today.getYear(); // 현재 연도
	int cMonth = today.getMonthValue();// 현재 월 월
	
	
	
	String args = "";	//쿼리스트링(페이지 번호)을 저장할 변수
	String oargs = "";
	
	
	if (o != null && !o.equals("")){	oargs += "&o=" + o ; } else { o = "a";}; // 정렬방식 a일경우 b일 경우	//기본이 득표순으로 설정
			 
	String lnk = "my_tor_list?" + args;		// my_tor_list? + kind=b	
	args += oargs;	
	




%>
<style>
.torO {margin:0 auto; width:100%;}
.container{width: 80%; margin:0 auto;     margin-left: 200px;
    margin-top: -550px; margin-bottom: 500px;}
table{border-collapse: collapse;} 
th, td{ text-align: center;}
tr{border-bottom: 1px dotted black;}
h2 {padding-bottom:30px}
select {    margin-top: -540px;	margin-left: 400px; border:none;}
</style>

<div class="torO"><!-- 득표순, 등록역순  정렬--><!-- 제목 받아오기 -->

	<select name="o" onchange="location.href='<%=lnk%>&o=' + this.value;">
		<option value="a" <% if (o.equals("a")) { %>selected="selected"<% } %>>득표순</option>
		<option value="b" <% if (o.equals("b")) { %>selected="selected"<% } %>>최신순</option>
	</select>	

</div>
<div class="container">
	<div>
		<h2 align="left" margin-top="30px"  > 내 토너먼트 보기 </h2>
		<form name="frmMyTor" method="post" action="my_tor_list" >
			<input type="hidden" name="kind" value="b" />
			<table width="100%" cellpadding="5">	
				<tr>
					<th>번호</th>
					<th>이미지</th>
					<th>제목</th>
					<th>득표수</th>
					<th>등록일</th>
				</tr>
				
			<%
			String img = "noimg.PNG";
			boolean letterimg = false;
			if(torList.size()>0){ //토너먼트 리스트가 있을 경우
				int total=0; //사용안함나중에지울것
				for(int i=0; i<torList.size() ; i++){
					ect = torList.get(i);
					lnk = "ev_tor_view?ectidx=" + ect.getEct_idx() + "&pmcidx=" + ect.getPmc_idx() +args;
					if(ect.getEct_img1()!=null && !ect.getEct_img1().equals("")) {
						//이미지관련처리해야함
						letterimg = true;
						img = ect.getEct_img1();
					}
					int ect_idx = ect.getEct_idx(); //토너먼트리스트의 일련번호
					String title = ect.getEct_title();
			%>
			
				<tr>
					
					<td width="10%"><%= i + 1 %></td>
					<td width="10%">
						<img src="/hamaProject/event/upload/<%=img %>" width="40" height="50" border="0" />
					</td>
					<td><a href='<%=lnk %>' onclick="view(<%=ect.getEct_idx()%>);"><%=title %></a></td>
					<td><%= ect.getEct_vote() %></td>
					<td><%=ect.getEct_date().substring(0, 10) %></td>
				</tr>		
				
	<%		
				}
	%>
			</table>
	
		<%
			}else{ //토너먼트가 없을 경우
				out.println("<tr><td colspan=8 align='center'> 제출한 토너먼트가 없습니다.</td></tr></table>");
			}
		%>
		
			</form>
		</div>
	</div>







<%@ include file="../_inc/footer.jsp" %>
</body>
</html>


