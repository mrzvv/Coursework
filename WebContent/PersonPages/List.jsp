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
<title>ОАО "Приорбанк" - работа с клиентами</title>
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
				<h4 id="box">Клиенты-физические лица ОАО "Приорбанк"</h4>
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
					<table align="center" width="80%" class="data">
						<p align="center">
							<a style="color: green; font-size: 18px;" href="<%=request.getContextPath()%>/?action=addPerson">Добавить нового клиента</a>
						</p>
						<%
							if ((positive_user_message != null) && (!positive_user_message.equals(""))) {
						%>
						<tr>
							<td colspan="6" align="center" style="color: green;"><%=positive_user_message%></td>
						</tr>
						<%
							}
						%>
						<%
							if ((negative_user_message != null) && (!negative_user_message.equals(""))) {
						%>
						<tr>
							<td colspan="6" align="center" style="color: red;"><%=negative_user_message%></td>
						</tr>
						<%
							}
						%>
						<tr>
							<th>Клиент</th>
							<th>Текущие депозиты</th>
							<th>Счета клиента</th>
						</tr>
						<%
							for (Person person : bank.getClients().values()) {
						%>
						<tr>
							<td><%=person.getSurname()%> <%=person.getName()%> <%=person.getMiddlename()%>
								<a href="<%=request.getContextPath()%>/?action=editPerson&amp;id=<%=person.getId()%>"><img src="d:/edit.png" height="15"></a><a
								href="<%=request.getContextPath()%>/?action=deletePerson&amp;id=<%=person.getId()%>"><img src="d:delete.png" height="15"></a></td>
							<td>
								<%
									for (Map.Entry<String, String> deposit : person.getDeposites().entrySet()) {
								%> 
									<% out.write(deposit.getValue());%> <%if(bank.getActiveDeposits().containsKey(deposit.getKey())) {%>
									<a id="none" href="<%=request.getContextPath()%>/?action=performDepositOperation&amp;contractId=<%=deposit.getKey()%>"><% out.write(deposit.getKey());%></a><br /> 
									<%} else {%><a id="none" href="<%=request.getContextPath()%>/?action=showArchive"><% out.write(deposit.getKey());%></a><br /><%}%>
								<%
 									}
 								%> <a id="none" href="<%=request.getContextPath()%>/?action=addDeposit&amp;id=<%=person.getId()%>">Оформить новый депозит</a>
							</td>
							<td><a href="<%=request.getContextPath()%>/?action=showClientAccounts&amp;clientId=<%=person.getId()%>">Просмотр счетов</a></td>
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