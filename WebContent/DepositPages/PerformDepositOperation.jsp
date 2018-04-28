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
<title>ОАО "Приорбанк" - выбор операции для депозитной программы</title>
<style>
    <%@include file="/WEB-INF/css/style.css"%>
</style>
</head>
<body>

	<%
		HashMap<String, String> jsp_parameters = new HashMap<String, String>();
		Deposit deposit = new Deposit();
		String error_message = "";
		String positive_user_message = "";
		String negative_user_message = "";

		if (request.getAttribute("jsp_parameters") != null) {
			jsp_parameters = (HashMap<String, String>) request.getAttribute("jsp_parameters");
		}

		if (request.getAttribute("deposit") != null) {
			deposit = (Deposit) request.getAttribute("deposit");
		}

		error_message = jsp_parameters.get("error_message");
		positive_user_message = jsp_parameters.get("positive_current_action_result_label");
		negative_user_message = jsp_parameters.get("negative_current_action_result_label");
	%>

	<div class="wrapper">
		<form action="<%=request.getContextPath()%>/" method="post">
			<input type="hidden" name="contractId"
				value="<%=deposit.getContractId()%>" />
			<div class="content">
				<hr>
					<h4 id="box">Операции для депозитных программ ОАО "Приорбанк"</h4>
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
										<%
											if ((positive_user_message != null) && (!positive_user_message.equals(""))) {
										%>
										<p align="center" style="color: green; font-size: 17px; margin-bottom: -5px;"><%=positive_user_message%></p>
										<%
											}
										%>
										<%
											if ((negative_user_message != null) && (!negative_user_message.equals(""))) {
										%>
										<p align="center" style="color: red; font-size: 17px; margin-bottom: -5px;"><%=negative_user_message%></p>
										<%
											}
										%>
							<tr>
								<td>
									<p id="module">
										Выберите операцию для депозита
										<%=deposit.getContractId()%>:
									</p>
									<ul class="menu1">
										<li><a href="<%=request.getContextPath()%>/?action=refillDepositAccount&amp;contractId=<%=deposit.getContractId()%>">Пополнение счёта клиентом</a></li>
										<li><a href="<%=request.getContextPath()%>/?action=reserveMoneyForBankNeeds&amp;contractId=<%=deposit.getContractId()%>">Изъятие средств со счёта на нужды банка</a></li>
										<li><a href="<%=request.getContextPath()%>/?action=returnMoneyForBankNeeds&amp;contractId=<%=deposit.getContractId()%>">Возврат изъятых на нужды банка средств</a></li>
										<li><a href="<%=request.getContextPath()%>/?action=accureDepositPercent&amp;contractId=<%=deposit.getContractId()%>">Начисление процентов по депозиту за месяц</a></li>
										<li><a href="<%=request.getContextPath()%>/?action=takeAwayCurrentAccountMoney&amp;contractId=<%=deposit.getContractId()%>">Изъятие клиентом средств с текущего счёта</a></li>
										<li><a href="<%=request.getContextPath()%>/?action=takeAwayPercentAccountMoney&amp;contractId=<%=deposit.getContractId()%>">Изъятие клиентом средств с процентного счёта</a></li>
										<li><a href="<%=request.getContextPath()%>/?action=closeDepositProgram&amp;contractId=<%=deposit.getContractId()%>">Закрытие депозита с последующей выдачей клиенту денежных средств</a></li>
									</ul>
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
											<td>Сумма с учетом пополнений и %:</td>
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
							<tr>
								<td colspan="2" align="center"><a style="font-size: 17px;" href="<%=request.getContextPath()%>/?action=manageDeposits">К списку депозитов</a><br /></td>
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