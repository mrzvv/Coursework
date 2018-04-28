<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="entity.Person"%>
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
<title>ОАО "Приорбанк" - редактирование данных о клиенте</title>
<style>
    <%@include file="/WEB-INF/css/style.css"%>
</style>
</head>
<body>

	<%
		HashMap<String, String> jsp_parameters = new HashMap<String, String>();
		Person person = new Person();
		String error_message = "";

		if (request.getAttribute("jsp_parameters") != null) {
			jsp_parameters = (HashMap<String, String>) request.getAttribute("jsp_parameters");
		}

		if (request.getAttribute("person") != null) {
			person = (Person) request.getAttribute("person");
		}

		error_message = jsp_parameters.get("error_message");
	%>

	<div class="wrapper">
		<form action="<%=request.getContextPath()%>/" method="post">
			<input type="hidden" name="id" value="<%=person.getId()%>" />
			<div class="content">
				<hr>
					<h4 id="box">Редактирование информации о клиенте ОАО "Приорбанк"</h4>
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
							<p align="center"><%=person.getSurname()%>
								<%=person.getName()%>
								<%=person.getMiddlename()%></p>
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
								<td>Фамилия:</td>
								<td><input type="text" name="surname"
									value="<%=person.getSurname()%>" /></td>
							</tr>
							<tr>
								<td>Имя:</td>
								<td><input type="text" name="name"
									value="<%=person.getName()%>" /></td>
							</tr>
							<tr>
								<td>Отчество:</td>
								<td><input type="text" name="middlename"
									value="<%=person.getMiddlename()%>" /></td>
							</tr>
							<tr>
								<td>Пол:</td>
								<td><div class="select">
										<select name="sex">
											<option><%=person.getSex()%></option>
											<option>Не специфицирован</option>
											<option>Мужчина</option>
											<option>Женщина</option>
										</select>
									</div></td>
							</tr>
							<tr>
								<td>Дата рождения:</td>
								<td><input type="text" name="dateOfBirth"
									value="<%=person.getDateOfBirth()%>" /></td>
							</tr>
							<tr>
								<td>Серия паспорта:</td>
								<td><input type="text" name="passportSeries"
									value="<%=person.getPassportSeries()%>" /></td>
							</tr>
							<tr>
								<td>Номер паспорта:</td>
								<td><input type="text" name="passportId"
									value="<%=person.getPassportId()%>" /></td>
							</tr>
							<tr>
								<td>Место выдачи паспорта:</td>
								<td><input type="text" name="passportPlaceOfIssue"
									value="<%=person.getPassportPlaceOfIssue()%>" /></td>
							</tr>
							<tr>
								<td>Дата выдачи паспорта:</td>
								<td><input type="text" name="passportDateOfIssue"
									value="<%=person.getPassportDateOfIssue()%>" /></td>
							</tr>
							<tr>
								<td>Гражданство:</td>
								<td><select name="citizenship">
										<option><%=person.getCitizenship()%></option>
										<option>Беларусь</option>
										<option>Россия</option>
										<option>Украина</option>
										<option>Казахстан</option>
								</select></td>
							</tr>
							<tr>
								<td>Место рождения:</td>
								<td><input type="text" name="placeOfBirth"
									value="<%=person.getPlaceOfBirth()%>" /></td>
							</tr>
							<tr>
								<td>Адрес прописки:</td>
								<td><input type="text" name="registrationAddress"
									value="<%=person.getRegistrationAddress()%>" /></td>
							</tr>
							<tr>
								<td>Город фактического проживания:</td>
								<td><select name="actualCity">
										<option><%=person.getActualCity()%></option>
										<option>Борисов</option>
										<option>Брест</option>
										<option>Вилейка</option>
										<option>Витебск</option>
										<option>Гомель</option>
										<option>Гродно</option>
										<option>Добруш</option>
										<option>Дрозды</option>
										<option>Ельск</option>
										<option>Жлобин</option>
										<option>Жодино</option>
										<option>Заречье</option>
										<option>Кобрин</option>
										<option>Клецк</option>
										<option>Лепель</option>
										<option>Липецк</option>
										<option>Минск</option>
										<option>Могилёв</option>
										<option>Несвиж</option>
										<option>Новогрудок</option>
										<option>Прилуки</option>
										<option>Ружаны</option>
										<option>Слуцк</option>
										<option>Туров</option>
										<option>Ушачи</option>
										<option>Фаниполь</option>
										<option>Чашники</option>
										<option>Шклов</option>
										<option>Щучин</option>
								</select></td>
							</tr>
							<tr>
								<td>Адрес проживания:</td>
								<td><input type="text" name="address"
									value="<%=person.getAddress()%>" /></td>
							</tr>
							<tr>
								<td>Мобильный телефон:</td>
								<td><input type="text" name="mobilePhone"
									value="<%=person.getMobilePhone()%>" /></td>
							</tr>
							<tr>
								<td>Домашний телефон:</td>
								<td><input type="text" name="homePhone"
									value="<%=person.getHomePhone()%>" /></td>
							</tr>
							<tr>
								<td>Электронная почта:</td>
								<td><input type="text" name="email"
									value="<%=person.getEmail()%>" /></td>
							</tr>

							<tr>
								<td>Доход в месяц:</td>
								<td><input type="text" name="monthIncome"
									value="<%=person.getMonthIncome()%>" /></td>
							</tr>

							<tr>
								<td>Семейное положение:</td>
								<td><select name="maritalStatus">
										<option><%=person.getMaritalStatus()%></option>
										<option>Женат(а)</option>
										<option>Не замужем/Не женат</option>
										<option>Разведен(а)</option>
										<option>Вдовец/Вдова</option>
								</select></td>
							</tr>
							<tr>
								<td>Инвалидность:</td>
								<td><select name="disability">
										<option><%=person.getDisability()%></option>
										<option>Отсутствует</option>
										<option>I группа</option>
										<option>II группа</option>
										<option>III группа</option>
								</select></td>
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