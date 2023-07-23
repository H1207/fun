<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../_inc/header.jsp" %>
<link rel="stylesheet" href="../_inc/css/common.css">
<link rel="stylesheet" href="../_inc/css/header.css">
<%@ include file="../_inc/my_menu.jsp" %>
<%
ArrayList<MemberAddr> addrList = (ArrayList<MemberAddr>)request.getAttribute("addrList");
%>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample6_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }
</script>
<style>
    .my-place{ width: 100%; }
    .place-list{
        margin: 5px auto 0;
        padding: 10px;
        width: 80%;
        border: 1px solid rgb(103, 103, 103);
        line-height: 30px;
    }
    .place-list:nth-of-type(1){ margin-top: 30px; }
    .place-list > a{
        padding: 2px 10px;
        border: 1px solid rgb(103, 103, 103);
        color: tomato;
    }
    .place-list input{
        height: 30px;
        margin-bottom: 5px;
    }
    .my-place > a{
        display: block;
        margin: 10px auto;
        width: 80%;
        border: 1px solid rgb(103, 103, 103);
        padding: 10px;
        text-align: center;
        font-weight: bold;
        color: tomato;
    }
    .my-place a:hover{
        background-color: rgb(241, 239, 239);
        color : tomato;
    }
    .adrname{
        font-size: 22px;
        font-weight: bold;
    }
    .adrdf{
        font-size: 13px;
        font-style: italic ;
        color: gray;
    }
    #df{
        display: flex;
        align-items: center;
    }
    #df > label{
        font-size: 13px;
        margin-left: 5px;
    }
</style>
<form name="frmAddr" action="/hamaProject/member_addr_up" method="post" style="width: 100%">
<div class="my-place">
    <h2>배송지 관리</h2>
<%
if(addrList.size() > 0){
	for(int i = 0 ; i <addrList.size() ; i++){
		MemberAddr ma = addrList.get(i);
%>
    <div class="place-list">
        <ul>
            <li class="adrname"><%=ma.getMa_rname() %></li>
            <li class="adrdf"><%=ma.getMa_basic() %></li>
            <li>(<%=ma.getMa_zip() %>)<%=ma.getMa_addr1() %>, <%=ma.getMa_addr2() %></li>
            <li><%=ma.getMa_phone() %></li>
        </ul>
        <a href="">수정</a>
    </div>
<% 
	}
}
%>
    <a href="">+ 배송지 추가</a>
    <div class="place-list">
		<p>
		    <input type="text" name="rname" id="rname" placeholder="받는 사람">
		</p>
		<p>
		    <input type="text" id="sample6_postcode" placeholder="우편번호" name="zip" readonly>
		    <input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" ><br>
		<input type="text" id="sample6_address" placeholder="주소" readonly name="addr1" style="width: 360px"><br>
		    <input type="text" id="sample6_detailAddress" placeholder="상세주소" name="addr2" >
		    <input type="text" id="sample6_extraAddress" readonly placeholder="자동주소">
		</p>
		<p><input type="text" name="aphone" id="" placeholder="휴대폰 번호"></p>
		<p id="df"><input type="checkbox"><label for="default">기본 배송지로 선택</label></p>
		<p><input type="submit" value="저장하기"></p>
	</div>
</div>
</form>


</div>

<%@ include file="../_inc/footer.jsp" %>