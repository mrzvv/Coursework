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
<title>ОАО "Приорбанк" - депозитные программы</title>
<style>
    <%@include file="/WEB-INF/css/style.css"%>
</style>
</head>
<body>

	<%
		HashMap<String, String> jsp_parameters = new HashMap<String, String>();

		if (request.getAttribute("jsp_parameters") != null) {
			jsp_parameters = (HashMap<String, String>) request.getAttribute("jsp_parameters");
		}
	%>

	<div class="wrapper">
		<form action="<%=request.getContextPath()%>/" method="post">
			<div class="content">
				<hr>
					<h4 id="box">Депозитные программы ОАО "Приорбанк"</h4>
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
							<p></p>

							<tr>
								<th class="col1"></th>
								<th class="col2">Вклад до востребования "Горячие деньги"</th>
								<th class="col3">Срочный вклад "Выше.net"</th>
							</tr>
							<tr>
								<td class="col1">Процентная ставка</td>
								<td class="col2">3% в BYN / 0,01% в USD/EUR</td>
								<td class="col3">8,2% в BYN / 0,75% в USD / 0,25% в EUR</td>
							</tr>
							<tr>
								<td class="col1">Дополнительные взносы</td>
								<td class="col2">Без ограничений</td>
								<td class="col3">Без ограничений</td>
							</tr>
							<tr>
								<td class="col1">Минимальная сумма для открытия вклада</td>
								<td class="col2">1 BYN / 1 USD/ 1 EUR</td>
								<td class="col3">50 BYN / 50 USD/ 50 EUR</td>
							</tr>
							<tr>
								<td class="col1">Начисление процентов</td>
								<td class="col2">Ежемесячная капитализация процентов</td>
								<td class="col3">Ежемесячная капитализация процентов</td>
							</tr>
							<tr>
								<td class="col1">Снижение процентной ставки при досрочном расторжении договора</td>
								<td class="col2">-</td>
								<td class="col3">При расторжении договора до 3 месяцев с момента заключения снижение до 0,01%</td>
							</tr>
							<tr>
								<td colspan="3" align="center"><a
									href="<%=request.getContextPath()%>/?action=manageDeposits">Управление текущими депозитами</a>  <a href="<%=request.getContextPath()%>/?action=showArchive">Архив депозитов</a><br /></td>
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