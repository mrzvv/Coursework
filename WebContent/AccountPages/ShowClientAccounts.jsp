<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="entity.Bankwork"%>
<%@ page import="entity.Person"%>
<%@ page import="entity.Account"%>
<%@ page import="entity.Operation"%>
<%@ page import="entity.Deposit"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Map.Entry"%>
<%@ page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>ОАО "Приорбанк" - клиентские счета</title>
<style>
    <%@include file="/WEB-INF/css/style.css"%>
</style>
</head>
<body>

	<%
		String user_message = "";
		HashMap<String, String> jsp_parameters = new HashMap<String, String>();
		Bankwork bank = (Bankwork) request.getAttribute("bank");
		Account byn_account = bank.getBankAccounts().get("Касса банка (BYN)");
		Account usd_account = bank.getBankAccounts().get("Касса банка (USD)");
		Account eur_account = bank.getBankAccounts().get("Касса банка (EUR)");
		HashMap<String, HashMap<String, Account>> clientsAccounts = bank.getClientAccounts();
		HashMap<String, Account> particularClientAccounts = bank.getClientAccounts().get(request.getAttribute("clientId"));

		if (request.getAttribute("jsp_parameters") != null) {
			jsp_parameters = (HashMap<String, String>) request.getAttribute("jsp_parameters");
		}

		user_message = jsp_parameters.get("current_action_result_label");
	%>
	<input type="hidden" name="indicator" value="presents" />
	<input type="hidden" name="clientId" value="<%=request.getAttribute("clientId") %>>" />
	<div class="wrapper">
		<div class="content">
			<hr>
				<h4 id="box">Информация о счетах клиента <%=request.getAttribute("clientId")%> ОАО "Приорбанк"</h4>
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
					
					<%
					if (particularClientAccounts != null) {
						for (Map.Entry<String, Account> entry : particularClientAccounts.entrySet()) {
					%>
					
					<table align="center" width="80%" class="data">
						<p></p>
						<tr>
							<td colspan="4" align="center"><%=entry.getKey()%></td>
						</tr>
						<tr>
							<th class="col1">Номер операции</th>
							<th>Содержание операции</th>
							<th>Дебет</th>
							<th>Кредит</th>
						</tr>
						<%
							for (Operation o : entry.getValue().getAccountOperations().values()) {
						%>
						<tr>
							<td class="col1"><%=o.getOperationId()%></td>
							<td><%=o.getOperationDescription()%></td>
							<td><%=o.getDebit()%></td>
							<td><%=o.getCredit()%></td>
						</tr>
						<%
							}
						%>
					</table>
					<p></p>
					<% }
							}%>
		</div>
		<div class="footer">
			<p align="center">© 1998-2018 «Приорбанк» ОАО | Лицензия НБ РБ №12 от 06.05.2013</p>
		</div>
	</div>
</body>
</html>