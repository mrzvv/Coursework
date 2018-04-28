<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="entity.Bankwork"%>
<%@ page import="entity.Person"%>
<%@ page import="entity.Deposit"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.LinkedHashMap"%>
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
<title>ОАО "Приорбанк" - закрытые депозиты</title>
<style>
    <%@include file="/WEB-INF/css/style.css"%>
</style>
</head>
<body>

	<%
		String positive_user_message = "";
		String negative_user_message = "";
		HashMap<String, String> jsp_parameters = new HashMap<String, String>();
		Bankwork bank = (Bankwork) request.getAttribute("bank");

		if (request.getAttribute("jsp_parameters") != null) {
			jsp_parameters = (HashMap<String, String>) request.getAttribute("jsp_parameters");
		}

		positive_user_message = jsp_parameters.get("positive_current_action_result_label");
		negative_user_message = jsp_parameters.get("negative_current_action_result_label");
	%>
	<input type="hidden" name="indicator" value="presents" />
	<div class="wrapper">
		<div class="content">
			<hr>
				<h4 id="box">Архив депозитных программ ОАО "Приорбанк"</h4>
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
					<p></p>
					<table align="center" width="99%" class="data">
						<%
							if ((positive_user_message != null) && (!positive_user_message.equals(""))) {
						%>
						<tr>
							<td colspan="9" align="center" style="color: green;"><%=positive_user_message%></td>
						</tr>
						<%
							}
						%>
						<%
							if ((negative_user_message != null) && (!negative_user_message.equals(""))) {
						%>
						<tr>
							<td colspan="9" align="center" style="color: red;"><%=negative_user_message%></td>
						</tr>
						<%
							}
						%>
						<tr>
							<th class="col1">Номер договора</th>
							<th>Вкладчик</th>
							<th>Вид вклада</th>
							<th>Выдано денег по вкладу</th>
							<th>Валюта вклада</th>
							<th>Дата начала программы</th>
							<th>Дата закрытия программы</th>
						</tr>
						<%
							for (Deposit deposit : bank.getClosedDeposits().values()) {
						%>
						<tr>
							<td class="col1"><%=deposit.getContractId()%></td>
							<td><%=bank.getClients().get(deposit.getClientId()).getSurname()%> <%=bank.getClients().get(deposit.getClientId()).getName()%> <%=bank.getClients().get(deposit.getClientId()).getMiddlename()%></td>
							<td><%=deposit.getDepositType()%></td>
							<td><%=deposit.getSumWithFillings()%></td>
							<td><%=deposit.getCurrency()%></td>
							<td><%=deposit.getStartDate()%></td>
							<td><%=deposit.getClosureDate()%></td>
						</tr>
						<%
							}
						%>
					</table>
		</div>
		<div class="footer">
			<p align="center">© 1998-2018 «Приорбанк» ОАО | Лицензия НБ РБ №12 от 06.05.2013</p>
		</div>
	</div>
</body>
</html>