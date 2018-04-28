<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="entity.Person"%>
<%@ page import="entity.Deposit"%>
<%@ page import="entity.Bankwork"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>ОАО "Приорбанк" - выбор счета для просмотра состояния</title>
<style>
    <%@include file="/WEB-INF/css/style.css"%>
</style>
</head>
<body>

	<%
		HashMap<String, String> jsp_parameters = new HashMap<String, String>();
		String error_message = "";
		String user_message = "";

		if (request.getAttribute("jsp_parameters") != null) {
			jsp_parameters = (HashMap<String, String>) request.getAttribute("jsp_parameters");
		}

		Bankwork bank = (Bankwork) request.getAttribute("bank");

		error_message = jsp_parameters.get("error_message");
		user_message = jsp_parameters.get("current_action_result_label");
	%>

	<div class="wrapper">
		<form action="<%=request.getContextPath()%>/" method="post">
			<div class="content">
				<hr>
					<h4 id="box">Выбор счета для просмотра ОАО "Приорбанк"</h4>
					<hr>
						<div id="header">
							<div class="line">
								<a href="#"><img id="logo" src="d:/logo.jpg" height="80"></a>
							</div>
							<div class="line" id="home">
								<a href="<%=request.getContextPath()%>/Welcome"><img id="logo" src="d:/home.png" height="60"></a>
							</div>
							<hr>
						</div>

						<table width="device-width" align="center">
						<p></p>
						<%
							if ((error_message != null) && (!error_message.equals(""))) {
						%>
							<tr>
								<td colspan="2" align="center"><span style="color: red"><%=error_message%></span></td>
							</tr>
						<%
							}
						%>
						<%
							if ((user_message != null) && (!user_message.equals(""))) {
						%>
							<tr>
								<td colspan="2" align="center"><span style="color: green"><%=error_message%></span></td>
							</tr>
						<%
							}
						%>
							<tr>
								<td valign="top">
									<p id="module">
										Счета банка:
									</p>
									<ul class="menu1">
										<li><a href="<%=request.getContextPath()%>/?action=showBankCash">1010 Касса банка</a></li>
										<li><a href="<%=request.getContextPath()%>/?action=showBankDevelopmentFund">7327 Счет фонда развития банка</a></li>
									</ul>
								</td>
								<td valign="top">
									<p id="module">
										Клиентские счета:
									</p>
									<ul class="menu1">
									<%
										for (String clientId : bank.getClientAccounts().keySet()) {
									%>
											<li><a href="<%=request.getContextPath()%>/?action=showClientAccounts&amp;clientId=<%=clientId%>">Счет клиента <%=clientId%></a></li>
									<%
										}
									%>
									</ul>
								</td>
							</tr>
						</table>
			</div>
			<div class="footer">
				<p align="center">© 1998-2018 «Приорбанк» ОАО | Лицензия НБ РБ №12 от 06.05.2013</p>
			</div>
		</form>
	</div>
</body>
</html>