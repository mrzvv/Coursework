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
<title>ОАО "Приорбанк" - открытие нового депозита</title>
<style>
    <%@include file="/WEB-INF/css/style.css"%>
</style>
</head>
<body>

	<%
		HashMap<String, String> jsp_parameters = new HashMap<String, String>();
		Deposit deposit = new Deposit();
		Person person = new Person();
		String error_message = "";

		if (request.getAttribute("jsp_parameters") != null) {
			jsp_parameters = (HashMap<String, String>) request.getAttribute("jsp_parameters");
		}

		if (request.getAttribute("person") != null) {
			person = (Person) request.getAttribute("person");
		}

		if (request.getAttribute("deposit") != null) {
			deposit = (Deposit) request.getAttribute("deposit");
		}

		error_message = jsp_parameters.get("error_message");
	%>

	<div class="wrapper">
		<form action="<%=request.getContextPath()%>/" method="post">
			<input type="hidden" name="id" value="<%=person.getId()%>" />
			<div class="content">
				<hr>
					<h4 id="box">Открытие нового депозита ОАО "Приорбанк"</h4>
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
						<table align="center" width="70%" class="data">
							<p align = "center">Новый депозит на имя <%=person.getSurname()%> <%=person.getName()%> <%=person.getMiddlename()%></p>
							<%
								if ((error_message != null) && (!error_message.equals(""))) {
							%>
							<tr>
								<td colspan="2" align="center"><span style="color: red"><%=error_message%></span></td>
							</tr>
							<%
								}
							%>
							<tr>
								<td>Вид депозита:</td>
								<td><select name="depositType">
										<option><%=deposit.getDepositType()%></option>
										<option>Вклад до востребования</option>
										<option>Срочный вклад</option>
								</select></td>
							</tr>
							<tr>
								<td>Валюта депозита:</td>
								<td><select name="currency">
										<option><%=deposit.getCurrency()%></option>
										<option>BYN</option>
										<option>USD</option>
										<option>EUR</option>
								</select></td>
							</tr>
							<tr>
								<td>Дата старта депозитной программы:</td>
								<td><input type="text" name="startDate"
									value="<%=deposit.getStartDate()%>" /></td>
							</tr>
							<tr>
								<td>Дата окончания депозитной программы<br /> (для срочных вкладов):</td>
								<td><input type="text" name="endDate"
									value="<%=deposit.getEndDate()%>" /></td>
							</tr>
							<tr>
								<td>Сумма вклада:</td>
								<td><input type="text" name="sum"
									value="<%=deposit.getSum()%>" /></td>
							</tr>
							<tr>
								<td colspan="2" align="center"><input id="button"
									type="submit" name="<%=jsp_parameters.get("next_action")%>"
									value="<%=jsp_parameters.get("next_action_label")%>" /> <br /></td>
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