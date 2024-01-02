<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h1>all</h1>
	<a href="member">member</a>
	<a href="admin">admin</a>
	<form method="post" action="/logout">
		<button>�α׾ƿ�</button>
		<sec:csrfInput/>
	</form>
	<%
		// �α����� ��������� / �ƴ��� 
	%>
	<sec:authorize access="authenticated">
		<h1>�α��� ����</h1>
		
		<c:set var="value" value="10" />
		<sec:authentication property="principal" var="p"/>
		<h2><sec:authentication property="principal.member" var="member" scope="sesssion"/>
		
		<h2><sec:authentication property="principal.username"/></h2> <!-- admin -->
		<h2><sec:authentication property="principal"/></h2> <!-- User (CustomUser) -->
		<h2><sec:authentication property="principal.member"/></h2> <!-- MemberVO -->
		<h2><sec:authentication property="principal.member.id"/></h2> <!-- admin -->
		<h2><sec:authentication property="principal.member.username"/></h2> <!-- ȫ�浿 -->
		<hr>
		<h2>${p}</h2>
		<h2>${member}</h2>
	</sec:authorize>
	<sec:authorize access="anonymous">
		<h1>�� �α���</h1>
	</sec:authorize>

</body>
</html>