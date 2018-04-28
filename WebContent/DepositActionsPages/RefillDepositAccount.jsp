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
<title>ОАО "Приорбанк" - пополнение депозитного счета</title>
<style>
    <%@include file="/WEB-INF/css/style.css"%>
</style>
</head>
<body>

	<%
		HashMap<String, String> jsp_parameters = new HashMap<String, String>();
		Deposit deposit = new Deposit();
		String error_message = "";

		if (request.getAttribute("jsp_parameters") != null) {
			jsp_parameters = (HashMap<String, String>) request.getAttribute("jsp_parameters");
		}

		if (request.getAttribute("deposit") != null) {
			deposit = (Deposit) request.getAttribute("deposit");
		}

		error_message = jsp_parameters.get("error_message");
	%>

	<div class="wrapper">
		<form action="<%=request.getContextPath()%>/" method="post">
			<input type="hidden" name="contractId"
				value="<%=deposit.getContractId()%>" />
			<div class="content">
				<hr>
					<h4 id="box">Пополнение депозитного счета ОАО "Приорбанк"</h4>
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
						<table width="device-width" align="center">
										<%
											if ((error_message != null) && (!error_message.equals(""))) {
										%>
										<p align="center" style="color: red; font-size: 17px;"><%=error_message%></p>
										<%
											}
										%>
							<tr>
								<td valign="top">
									<p id="module"> Введите необходимую информацию<br /> для пополнения счета <%=deposit.getContractId()%>: </p>
									<table>
										<p style="margin-left: 47px; font-size: 15px;">Сумма пополнения:</p>
										<tr>
											<td><input type="text" name="fillingSum" style="margin-left: 43px;"/></td>
										</tr>
										<td colspan="2"><input id="button1"
											type="submit" name="<%=jsp_parameters.get("next_action")%>"
											value="<%=jsp_parameters.get("next_action_label")%>" /> <br /></td>
									</table>
								</td>
								<td style="padding-left: 60px;">
									<table class="data">
										
										<tr>
											<td>Номер договора:</td>
											<td>
												<%
													out.write(deposit.getContractId());
												%>
											</td>
										</tr>
										<tr>
											<td>Вид депозита:</td>
											<td>
												<%
													out.write(deposit.getDepositType());
												%>
											</td>
										</tr>
										<tr>
											<td>Первичная сумма вклада:</td>
											<td>
												<%
													out.write(deposit.getSum());
												%>
											</td>
										</tr>
										<tr>
											<td>Сумма с учетом пополнений:</td>
											<td>
												<%
													out.write(deposit.getSumWithFillings());
												%>
											</td>
										</tr>
										<tr>
											<td>Валюта депозита:</td>
											<td>
												<%
													out.write(deposit.getCurrency());
												%>
											</td>
										</tr>
										<tr>
											<td>Дата старта депозитной программы:</td>
											<td>
												<%
													out.write(deposit.getStartDate());
												%>
											</td>
										</tr>
										<tr>
											<td>Дата окончания депозитной программы:</td>
											<td>
												<%
													out.write(deposit.getEndDate());
												%>
											</td>
										</tr>
										<tr>
											<td>Депозитный процент:</td>
											<td>
												<%
													out.write(deposit.getDepositPercent());
												%>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<p align="center" style="font-size: 17px;"><a
								href="<%=request.getContextPath()%>/?action=performDepositOperation&amp;contractId=<%=deposit.getContractId()%>">К списку операций</a></p>
			</div>
			<div class="footer">
				<p align="center">© 1998-2018 «Приорбанк» ОАО | Лицензия НБ РБ №12 от 06.05.2013</p>
			</div>
		</form>
	</div>
</body>
</html>