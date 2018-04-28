<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>ОАО "Приорбанк" - автоматизированная система | Приорбанк</title>
    <style>
    <%@include file="/WEB-INF/css/style.css"%>
    </style>
</head>
<body>
    <div class="wrapper">
        <div class="content">
            <hr>
            <h4 id="box">Добро пожаловать в автоматизированную банковскую систему работы с клиентами и депозитами ОАО "Приорбанк"</h4>
            <hr>
            <div id="header">
                <div class="line"><a href="#"><img id="logo" src="d:/logo.jpg" height="80"></a></div>
                <div class="line" id="home"><a href="<%=request.getContextPath()%>/Welcome"><img id="logo" src="d:/home.png" height="60"></a></div>
                <hr>
            </div>
            <table width="device-width">
                <tr>
                    <td width="30%" valign="top">
                        <p id="module">Процентные индикаторы</p>
                        <ul class="menu">
                            <li><a href="https://www.priorbank.by/bazovye-stavki-libor">Базовые ставки LIBOR</a></li>
                            <li><a href="https://www.priorbank.by/bazovye-stavki-mosprime">Базовые ставки MOSPRIME</a></li>
                            <li><a href="https://www.priorbank.by/euribor">EURIBOR</a></li>
                        </ul>
                    </td>

                    <td width="33%" valign="top">
                        <p id="module">Выберите модуль, которым вы хотите воспользоваться:</p>
                        <ul class="menu">
                            <li><a href="<%=request.getContextPath()%>/?action=showList">Работа с клиентами</a></li>
                            <li><a href="<%=request.getContextPath()%>/?action=showDepositPrograms">Депозитные программы</a></li>
                            <li><a href="<%=request.getContextPath()%>/?action=choseAccountsToView">Бухгалтерская отчетность</a></li>
                        </ul>
                    </td>

                    <td width="30%" valign="top">
                        <div>
                            <p id="module">Последние новости в сфере валютного законодательства</p>
                            <ul class="menu">
                                <li><a href="https://www.priorbank.by/poslednie-izmenenia">Последние изменения в валютном законодательстве</a></li>
                                <li><a href="https://www.priorbank.by/rekomendacii-po-registracii-sdelki">Рекомендации по регистрации сделки</a></li>
                                <li><a href="https://www.priorbank.by/kontrol-vnesnetorgovyh-operacij">Контроль внешнеторговых операций</a></li>
                                <li><a href="https://www.priorbank.by/offsornyj-sbor">Оффшорный сбор</a></li>
                                <li><a href="https://www.priorbank.by/obazatel-naa-prodaza-valuty">Обязательная продажа иностранной валюты</a></li>
                            </ul>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        <div class="footer">
            <p align="center">© 1998-2018 «Приорбанк» ОАО | Лицензия НБ РБ №12 от 06.05.2013</p>
        </div>
    </div>
</body>
</html>