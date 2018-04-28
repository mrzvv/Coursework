package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Deposit;
import entity.Person;
import util.DepositValidator;
import util.PersonValidator;
import entity.Bankwork;

public class ManagePersonServlet extends HttpServlet {

	// Идентификатор для сериализации/десериализации.
	private static final long serialVersionUID = 1L;

	// Основной объект, хранящий данные телефонной книги.
	private Bankwork bank;

	public ManagePersonServlet() {
		// Вызов родительского конструктора.
		super();

		// Создание экземпляра телефонной книги.
		try {
			this.bank = Bankwork.getInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Реакция на GET-запросы.
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("bank", this.bank);

		HashMap<String, String> jsp_parameters = new HashMap<String, String>();

		RequestDispatcher dispatcher_for_welcomePage = request.getRequestDispatcher("/Welcome.jsp");
		RequestDispatcher dispatcher_for_depositPrograms = request
				.getRequestDispatcher("/DepositPages/DepositPrograms.jsp");
		RequestDispatcher dispatcher_for_managingDeposits = request
				.getRequestDispatcher("/DepositPages/ManageDeposits.jsp");
		RequestDispatcher dispatcher_for_refillingDepositAccount = request
				.getRequestDispatcher("/DepositActionsPages/RefillDepositAccount.jsp");
		RequestDispatcher dispatcher_for_accuringDepositPercent = request
				.getRequestDispatcher("/DepositActionsPages/AccureDepositPercent.jsp");
		RequestDispatcher dispatcher_for_chosingDepositOperation = request
				.getRequestDispatcher("/DepositPages/PerformDepositOperation.jsp");
		RequestDispatcher dispatcher_for_takingAwayCurrentAccountMoney = request
				.getRequestDispatcher("/DepositActionsPages/TakeAwayCurrentAccountMoney.jsp");
		RequestDispatcher dispatcher_for_list = request.getRequestDispatcher("/PersonPages/List.jsp");
		RequestDispatcher dispatcher_for_bankCash = request.getRequestDispatcher("/AccountPages/ShowBankCash.jsp");
		RequestDispatcher dispatcher_for_clientAccounts = request
				.getRequestDispatcher("/AccountPages/ShowClientAccounts.jsp");
		RequestDispatcher dispatcher_for_bankFund = request
				.getRequestDispatcher("/AccountPages/ShowBankDevelopmentFund.jsp");
		RequestDispatcher dispatcher_for_chosingAccountsToView = request
				.getRequestDispatcher("/AccountPages/ChoseAccountsToView.jsp");
		RequestDispatcher dispatcher_for_archive = request.getRequestDispatcher("/DepositPages/DepositArchive.jsp");
		RequestDispatcher dispatcher_for_addingPerson = request.getRequestDispatcher("/PersonPages/AddPerson.jsp");
		RequestDispatcher dispatcher_for_addingDeposit = request
				.getRequestDispatcher("/DepositActionsPages/AddDeposit.jsp");
		RequestDispatcher dispatcher_for_editingPerson = request.getRequestDispatcher("/PersonPages/EditPerson.jsp");
		RequestDispatcher dispatcher_for_closingDepositProgram = request
				.getRequestDispatcher("/DepositActionsPages/CloseDepositProgram.jsp");
		RequestDispatcher dispatcher_for_returningMoneyForBankNeeds = request
				.getRequestDispatcher("/DepositActionsPages/ReturnMoneyForBankNeeds.jsp");

		String action = request.getParameter("action");
		String personId = request.getParameter("id");
		String contractId = request.getParameter("contractId");
		String clientId = request.getParameter("clientId");

		// Если идентификатор и действие не указаны, мы находимся в состоянии
		// "просто показать список и больше ничего не делать".
		if ((action == null) && (personId == null)) {
			request.setAttribute("jsp_parameters", jsp_parameters);
			dispatcher_for_welcomePage.forward(request, response);
		}
		// Если же действие указано, то...
		else {
			switch (action) {

			// Показать список клиентов.
			case "showList":
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_list.forward(request, response);

				// Показать список закрытых депозитов.
			case "showArchive":
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_archive.forward(request, response);

				// Показать информацию по депозитным программам.
			case "showDepositPrograms":
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_depositPrograms.forward(request, response);

			case "showBankCash":
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_bankCash.forward(request, response);

			case "showBankDevelopmentFund":
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_bankFund.forward(request, response);

			case "choseAccountsToView":
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_chosingAccountsToView.forward(request, response);

				// Показать список открытых депозитов.
			case "manageDeposits":
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_managingDeposits.forward(request, response);

				// Добавить клиента.
			case "addPerson":
				Person empty_person = new Person();

				jsp_parameters.put("current_action", "addPerson");
				jsp_parameters.put("next_action", "addPersonPOST");
				jsp_parameters.put("next_action_label", "Добавить");

				request.setAttribute("person", empty_person);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_addingPerson.forward(request, response);
				break;

			// Добавить депозит.
			case "addDeposit":
				Person person_with_new_deposit = this.bank.getPerson(personId);
				Deposit new_deposit = new Deposit();

				jsp_parameters.put("current_action", "addDeposit");
				jsp_parameters.put("next_action", "addDepositPOST");
				jsp_parameters.put("next_action_label", "Добавить");

				request.setAttribute("person", person_with_new_deposit);
				request.setAttribute("deposit", new_deposit);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_addingDeposit.forward(request, response);
				break;

			// Пополнить депозит.
			case "refillDepositAccount":
				Deposit deposit_to_refill = this.bank.getDeposit(contractId);

				jsp_parameters.put("current_action", "refillDepositAccount");
				jsp_parameters.put("next_action", "refillDepositAccountPOST");
				jsp_parameters.put("next_action_label", "Пополнить");

				request.setAttribute("deposit", deposit_to_refill);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_refillingDepositAccount.forward(request, response);
				break;

			// Выдать деньги клиенту с текущего счета.
			case "takeAwayCurrentAccountMoney":
				Deposit deposit_to_takeAway = this.bank.getDeposit(contractId);

				jsp_parameters.put("current_action", "takeAwayCurrentAccountMoney");
				jsp_parameters.put("next_action", "takeAwayCurrentAccountMoneyPOST");
				jsp_parameters.put("next_action_label", "Выдать");

				request.setAttribute("deposit", deposit_to_takeAway);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_takingAwayCurrentAccountMoney.forward(request, response);
				break;

			// Выдать деньги клиенту с процентного счёта.
			case "takeAwayPercentAccountMoney":
				Deposit deposit_to_takeAwayPercent = this.bank.getDeposit(contractId);

				jsp_parameters.put("current_action", "takeAwayPercentAccountMoney");
				jsp_parameters.put("next_action", "takeAwayPercentAccountMoneyPOST");
				jsp_parameters.put("next_action_label", "Выдать");

				request.setAttribute("deposit", deposit_to_takeAwayPercent);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_takingAwayCurrentAccountMoney.forward(request, response);
				break;

			// Изъять деньги с текущего счета клиента для нужд банка.
			case "reserveMoneyForBankNeeds":
				Deposit deposit_to_takeAwayForBank = this.bank.getDeposit(contractId);

				jsp_parameters.put("current_action", "reserveMoneyForBankNeeds");
				jsp_parameters.put("next_action", "reserveMoneyForBankNeedsPOST");
				jsp_parameters.put("next_action_label", "Изъять");

				request.setAttribute("deposit", deposit_to_takeAwayForBank);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_takingAwayCurrentAccountMoney.forward(request, response);
				break;

			// Возвратить деньги, использованные банком, на текущий счет
			// клиента.
			case "returnMoneyForBankNeeds":
				Deposit deposit_to_returnMoney = this.bank.getDeposit(contractId);

				jsp_parameters.put("current_action", "returnMoneyForBankNeeds");
				jsp_parameters.put("next_action", "returnMoneyForBankNeedsPOST");
				jsp_parameters.put("next_action_label", "Возвратить");

				request.setAttribute("deposit", deposit_to_returnMoney);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_returningMoneyForBankNeeds.forward(request, response);
				break;

			// Начислить проценты по депозиту.
			case "accureDepositPercent":
				Deposit deposit_to_accure_percents = this.bank.getDeposit(contractId);

				jsp_parameters.put("current_action", "accureDepositPercent");
				jsp_parameters.put("next_action", "accureDepositPercentPOST");
				jsp_parameters.put("next_action_label", "Начислить");

				request.setAttribute("deposit", deposit_to_accure_percents);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_accuringDepositPercent.forward(request, response);
				break;

			// Закрыть депозитную программу.
			case "closeDepositProgram":
				Deposit deposit_to_close = this.bank.getDeposit(contractId);

				jsp_parameters.put("current_action", "closeDepositProgram");
				jsp_parameters.put("next_action", "closeDepositProgramPOST");
				jsp_parameters.put("next_action_label", "Закрыть депозит");

				request.setAttribute("deposit", deposit_to_close);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_closingDepositProgram.forward(request, response);
				break;

			// Редактировать информацию о клиенте.
			case "editPerson":
				Person editable_person = this.bank.getPerson(personId);

				jsp_parameters.put("current_action", "editPerson");
				jsp_parameters.put("next_action", "editPersonPOST");
				jsp_parameters.put("next_action_label", "Сохранить");

				request.setAttribute("person", editable_person);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_editingPerson.forward(request, response);
				break;

			case "showClientAccounts":
				String client_to_show_id = clientId;

				request.setAttribute("clientId", client_to_show_id);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_clientAccounts.forward(request, response);

				// Выбрать действие над депозитом.
			case "performDepositOperation":
				Deposit dep = this.bank.getDeposit(contractId);

				request.setAttribute("deposit", dep);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_chosingDepositOperation.forward(request, response);

				// Удалить клиента.
			case "deletePerson":
				// Если запись удалось удалить...
				Person person_to_delete = this.bank.getPerson(personId);
				if (bank.deletePerson(person_to_delete)) {
					jsp_parameters.put("current_action_result", "DELETION_SUCCESS");
					jsp_parameters.put("current_action_result_label", "Удаление выполнено успешно");
				} else {
					jsp_parameters.put("current_action_result", "DELETION_FAILURE");
					jsp_parameters.put("current_action_result_label", "Ошибка удаления (возможно, запись не найдена)");
				}

				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_list.forward(request, response);
				break;
			}
		}
	}

	// Реакция на POST-запросы.
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("bank", this.bank);

		HashMap<String, String> jsp_parameters = new HashMap<String, String>();

		RequestDispatcher dispatcher_for_list = request.getRequestDispatcher("/PersonPages/List.jsp");
		RequestDispatcher dispatcher_for_addingPerson = request.getRequestDispatcher("/PersonPages/AddPerson.jsp");
		RequestDispatcher dispatcher_for_addingDeposit = request
				.getRequestDispatcher("/DepositActionsPages/AddDeposit.jsp");
		RequestDispatcher dispatcher_for_editingPerson = request.getRequestDispatcher("/PersonPages/EditPerson.jsp");
		RequestDispatcher dispatcher_for_chosingDepositOperation = request
				.getRequestDispatcher("/DepositPages/PerformDepositOperation.jsp");
		RequestDispatcher dispatcher_for_archive = request.getRequestDispatcher("/DepositPages/DepositArchive.jsp");
		RequestDispatcher dispatcher_for_refillingDepositAccount = request
				.getRequestDispatcher("/DepositActionsPages/RefillDepositAccount.jsp");
				RequestDispatcher dispatcher_for_takingAwayCurrentAccountMoney = request
				.getRequestDispatcher("/DepositActionsPages/TakeAwayCurrentAccountMoney.jsp");
				RequestDispatcher dispatcher_for_closingDepositProgram = request
				.getRequestDispatcher("/DepositActionsPages/CloseDepositProgram.jsp");

		String addPersonPOST = request.getParameter("addPersonPOST");
		String addDepositPOST = request.getParameter("addDepositPOST");
		String editPersonPOST = request.getParameter("editPersonPOST");
		String refillDepositAccountPOST = request.getParameter("refillDepositAccountPOST");
		String accureDepositPercentPOST = request.getParameter("accureDepositPercentPOST");
		String takeAwayCurrentAccountMoneyPOST = request.getParameter("takeAwayCurrentAccountMoneyPOST");
		String takeAwayPercentAccountMoneyPOST = request.getParameter("takeAwayPercentAccountMoneyPOST");
		String closeDepositProgramPOST = request.getParameter("closeDepositProgramPOST");
		String reserveMoneyForBankNeedsPOST = request.getParameter("reserveMoneyForBankNeedsPOST");
		String returnMoneyForBankNeedsPOST = request.getParameter("returnMoneyForBankNeedsPOST");

		String personId = request.getParameter("id");
		String contractId = request.getParameter("contractId");

		if (addPersonPOST != null) {
			int newPersonId = Person.generateId();
			Person new_person = new Person(String.valueOf(newPersonId), request.getParameter("name"),
					request.getParameter("surname"), request.getParameter("middlename"),
					request.getParameter("dateOfBirth"), request.getParameter("sex"),
					request.getParameter("passportSeries"), request.getParameter("passportId"),
					request.getParameter("passportPlaceOfIssue"), request.getParameter("passportDateOfIssue"),
					request.getParameter("placeOfBirth"), request.getParameter("actualCity"),
					request.getParameter("address"), request.getParameter("mobilePhone"),
					request.getParameter("homePhone"), request.getParameter("email"),
					request.getParameter("registrationAddress"), request.getParameter("maritalStatus"),
					request.getParameter("disability"), request.getParameter("monthIncome"),
					request.getParameter("citizenship"));

			String error_message = PersonValidator.validate(new_person);
			if (error_message.equals("")) {
				if (this.bank.addPerson(new_person)) {
					jsp_parameters.put("current_action_result", "ADDITION_SUCCESS");
					jsp_parameters.put("positive_current_action_result_label", "Добавление клиента выполнено успешно.");
				} else {
					jsp_parameters.put("current_action_result", "ADDITION_FAILURE");
					jsp_parameters.put("negative_current_action_result_label", "Ошибка добавления клиента.");
				}
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_list.forward(request, response);
			} else {
				jsp_parameters.put("current_action", "addPerson");
				jsp_parameters.put("next_action", "addPersonPOST");
				jsp_parameters.put("next_action_label", "Добавить");
				jsp_parameters.put("error_message", error_message);

				request.setAttribute("person", new_person);
				request.setAttribute("jsp_parameters", jsp_parameters);

				dispatcher_for_addingPerson.forward(request, response);
			}
		}

		if (editPersonPOST != null) {
			Person updatable_person = this.bank.getPerson(request.getParameter("id"));

			updatable_person.setPersonalData(updatable_person, request.getParameter("name"),
					request.getParameter("surname"), request.getParameter("middlename"), request.getParameter("sex"),
					request.getParameter("dateOfBirth"), request.getParameter("mobilePhone"),
					request.getParameter("homePhone"), request.getParameter("address"), request.getParameter("email"),
					request.getParameter("actualCity"), request.getParameter("placeOfBirth"),
					request.getParameter("registrationAddress"), request.getParameter("passportId"),
					request.getParameter("passportSeries"), request.getParameter("passportPlaceOfIssue"),
					request.getParameter("passportDateOfIssue"), request.getParameter("maritalStatus"),
					request.getParameter("disability"), request.getParameter("citizenship"),
					request.getParameter("monthIncome"));
			String error_message = PersonValidator.validate(updatable_person);

			if (error_message.equals("")) {
				if (this.bank.updatePerson(updatable_person)) {
					jsp_parameters.put("current_action_result", "UPDATE_SUCCESS");
					jsp_parameters.put("positive_current_action_result_label", "Обновление клиента выполнено успешно.");
				} else {
					jsp_parameters.put("current_action_result", "UPDATE_FAILURE");
					jsp_parameters.put("negative_current_action_result_label", "Ошибка обновления клиента.");
				}
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_list.forward(request, response);
			} else {
				jsp_parameters.put("current_action", "editPerson");
				jsp_parameters.put("next_action", "editPersonPOST");
				jsp_parameters.put("next_action_label", "Сохранить");
				jsp_parameters.put("error_message", error_message);

				request.setAttribute("person", updatable_person);
				request.setAttribute("jsp_parameters", jsp_parameters);

				dispatcher_for_editingPerson.forward(request, response);
			}
		}

		if (addDepositPOST != null) {
			Deposit new_deposit = new Deposit();
			
			if (request.getParameter("depositType").equals("Вклад до востребования")) {
				if (request.getParameter("currency").equals("BYN")) {
					new_deposit = new Deposit("3014" + request.getParameter("id") + Bankwork.generateBankKey(),
							request.getParameter("id"), "Вклад до востребования", "BYN",
							request.getParameter("startDate"), request.getParameter("endDate"),
							request.getParameter("sum"), request.getParameter("sum"), "3%", "active");
				} else if (request.getParameter("currency").equals("EUR")
						|| request.getParameter("currency").equals("USD")) {
					new_deposit = new Deposit("3014" + request.getParameter("id") + Bankwork.generateBankKey(),
							request.getParameter("id"), "Вклад до востребования", request.getParameter("currency"),
							request.getParameter("startDate"), request.getParameter("endDate"),
							request.getParameter("sum"), request.getParameter("sum"), "0.01%", "active");
				}
			} else if (request.getParameter("depositType").equals("Срочный вклад")) {
				if (request.getParameter("currency").equals("BYN")) {
					new_deposit = new Deposit("3024" + request.getParameter("id") + Bankwork.generateBankKey(),
							request.getParameter("id"), "Срочный вклад", "BYN", request.getParameter("startDate"),
							request.getParameter("endDate"), request.getParameter("sum"), request.getParameter("sum"),
							"8.2%", "active");
				} else if (request.getParameter("currency").equals("USD")) {
					new_deposit = new Deposit("3024" + request.getParameter("id") + Bankwork.generateBankKey(),
							request.getParameter("id"), "Срочный вклад", "USD", request.getParameter("startDate"),
							request.getParameter("endDate"), request.getParameter("sum"), request.getParameter("sum"),
							"0.75%", "active");
				} else if (request.getParameter("currency").equals("EUR")) {
					new_deposit = new Deposit("3024" + request.getParameter("id") + Bankwork.generateBankKey(),
							request.getParameter("id"), "Срочный вклад", "EUR", request.getParameter("startDate"),
							request.getParameter("endDate"), request.getParameter("sum"), request.getParameter("sum"),
							"0.25%", "active");
				}
			}

			String deposit_error = DepositValidator.validate(new_deposit, request);
			if (deposit_error.equals("")) {
				if (this.bank.addDeposit(new_deposit, this.bank.getPerson(personId))) {
					jsp_parameters.put("current_action_result", "ADDITION_SUCCESS");
					jsp_parameters.put("positive_current_action_result_label", "Добавление депозита выполнено успешно.");
				} else {
					jsp_parameters.put("current_action_result", "ADDITION_FAILURE");
					jsp_parameters.put("negative_current_action_result_label", "Ошибка добавления депозита.");
				}

				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_list.forward(request, response);
			} else {
				jsp_parameters.put("current_action", "addDeposit");
				jsp_parameters.put("next_action", "addDepositPOST");
				jsp_parameters.put("next_action_label", "Добавить");
				jsp_parameters.put("error_message", deposit_error);

				request.setAttribute("person", this.bank.getPerson(personId));
				request.setAttribute("deposit", new_deposit);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_addingDeposit.forward(request, response);
			}
		}

		if (refillDepositAccountPOST != null) {
			Deposit deposit_to_refill = this.bank.getDeposit(contractId);
			String error_message = "";
			if (!DepositValidator.validateSum(request.getParameter("fillingSum"))) {
				error_message += "Некорректный формат суммы.";
			}
			if (error_message.equals("")) {
				if (this.bank.refillDepositAccount(deposit_to_refill, request.getParameter("fillingSum"))) {
					jsp_parameters.put("current_action_result", "ADDITION_SUCCESS");
					jsp_parameters.put("positive_current_action_result_label", "Пополнение счета выполнено успешно.");
				} else {
					jsp_parameters.put("current_action_result", "ADDITION_FAILURE");
					jsp_parameters.put("negative_current_action_result_label", "Ошибка пополнения счета.");
				}

				request.setAttribute("deposit", deposit_to_refill);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_chosingDepositOperation.forward(request, response);
			} else {
				jsp_parameters.put("current_action", "refillDepositAccount");
				jsp_parameters.put("next_action", "refillDepositAccountPOST");
				jsp_parameters.put("next_action_label", "Пополнить");
				jsp_parameters.put("error_message", error_message);

				request.setAttribute("deposit", deposit_to_refill);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_refillingDepositAccount.forward(request, response);
			}
		}

		if (takeAwayCurrentAccountMoneyPOST != null) {
			Deposit deposit_to_takeAway = this.bank.getDeposit(contractId);
			String error_message = "";
			if (!DepositValidator.validateSum(request.getParameter("takeAwaySum"))) {
				error_message += "Некорректный формат суммы.";
			}
			if (error_message.equals("")) {
				if (this.bank.takeAwayAccountMoney(deposit_to_takeAway, request.getParameter("takeAwaySum"), "current",
						false)) {
					jsp_parameters.put("current_action_result", "ADDITION_SUCCESS");
					jsp_parameters.put("positive_current_action_result_label",
							"Выдача клиенту средств с текущего счета выполнена успешно.");
				} else {
					jsp_parameters.put("current_action_result", "ADDITION_FAILURE");
					jsp_parameters.put("negative_current_action_result_label", "Ошибка выдачи средств с текущего счета.");
				}

				request.setAttribute("deposit", deposit_to_takeAway);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_chosingDepositOperation.forward(request, response);
			} else {
				jsp_parameters.put("current_action", "takeAwayCurrentAccountMoney");
				jsp_parameters.put("next_action", "takeAwayCurrentAccountMoneyPOST");
				jsp_parameters.put("next_action_label", "Выдать");
				jsp_parameters.put("error_message", error_message);

				request.setAttribute("deposit", deposit_to_takeAway);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_takingAwayCurrentAccountMoney.forward(request, response);
			}
		}

		if (takeAwayPercentAccountMoneyPOST != null) {
			Deposit deposit_to_takeAway = this.bank.getDeposit(contractId);
			String error_message = "";
			if (!DepositValidator.validateSum(request.getParameter("takeAwaySum"))) {
				error_message += "Некорректный формат суммы.";
			}
			if (error_message.equals("")) {
				if (this.bank.takeAwayAccountMoney(deposit_to_takeAway, request.getParameter("takeAwaySum"), "percent",
						false)) {
					jsp_parameters.put("current_action_result", "ADDITION_SUCCESS");
					jsp_parameters.put("positive_current_action_result_label",
							"Выдача клиенту средств с процентного счета выполнена успешно.");
				} else {
					jsp_parameters.put("current_action_result", "ADDITION_FAILURE");
					jsp_parameters.put("negative_current_action_result_label", "Ошибка выдачи средств с процентного счета.");
				}

				request.setAttribute("deposit", deposit_to_takeAway);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_chosingDepositOperation.forward(request, response);
			} else {
				jsp_parameters.put("current_action", "takeAwayPercentAccountMoney");
				jsp_parameters.put("next_action", "takeAwayPercentAccountMoneyPOST");
				jsp_parameters.put("next_action_label", "Выдать");
				jsp_parameters.put("error_message", error_message);

				request.setAttribute("deposit", deposit_to_takeAway);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_takingAwayCurrentAccountMoney.forward(request, response);
			}
		}

		if (reserveMoneyForBankNeedsPOST != null) {
			Deposit deposit_to_ReserveMoney = this.bank.getDeposit(contractId);
			String error_message = "";
			if (!DepositValidator.validateSum(request.getParameter("takeAwaySum"))) {
				error_message += "Некорректный формат суммы.";
			}
			if (error_message.equals("")) {
				if (this.bank.takeAwayAccountMoney(deposit_to_ReserveMoney, request.getParameter("takeAwaySum"),
						"current", true)) {
					jsp_parameters.put("current_action_result", "ADDITION_SUCCESS");
					jsp_parameters.put("positive_current_action_result_label",
							"Изъятие средств с текущего счета для нужд банка выполнено успешно.");
				} else {
					jsp_parameters.put("current_action_result", "ADDITION_FAILURE");
					jsp_parameters.put("negative_current_action_result_label", "Ошибка изъятия средств с текущего счета.");
				}

				request.setAttribute("deposit", deposit_to_ReserveMoney);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_chosingDepositOperation.forward(request, response);
			} else {
				jsp_parameters.put("current_action", "reserveMoneyForBankNeeds");
				jsp_parameters.put("next_action", "reserveMoneyForBankNeedsPOST");
				jsp_parameters.put("next_action_label", "Изъять");
				jsp_parameters.put("error_message", error_message);

				request.setAttribute("deposit", deposit_to_ReserveMoney);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_takingAwayCurrentAccountMoney.forward(request, response);
			}
		}

		if (returnMoneyForBankNeedsPOST != null) {
			Deposit deposit_to_ReturnMoney = this.bank.getDeposit(contractId);
			String error_message = "";
			if (error_message.equals("")) {
				if (this.bank.returnAccountMoney(deposit_to_ReturnMoney)) {
					jsp_parameters.put("current_action_result", "ADDITION_SUCCESS");
					jsp_parameters.put("positive_current_action_result_label",
							"Возврат средств на текущий счет выполнен успешно.");
				} else {
					jsp_parameters.put("current_action_result", "ADDITION_FAILURE");
					jsp_parameters.put("negative_current_action_result_label", "Ошибка возврата средств на текущий счет.");
				}

				request.setAttribute("deposit", deposit_to_ReturnMoney);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_chosingDepositOperation.forward(request, response);
			} else {
				jsp_parameters.put("current_action", "reserveMoneyForBankNeeds");
				jsp_parameters.put("next_action", "reserveMoneyForBankNeedsPOST");
				jsp_parameters.put("next_action_label", "Изъять");
				jsp_parameters.put("error_message", error_message);

				request.setAttribute("deposit", deposit_to_ReturnMoney);
				request.setAttribute("jsp_parameters", jsp_parameters);
				dispatcher_for_chosingDepositOperation.forward(request, response);
			}
		}

		if (accureDepositPercentPOST != null) {
			Deposit deposit_to_accure = this.bank.getDeposit(contractId);
			String error_message = "";
			if (error_message.equals("")) {
				if (this.bank.accureDepositPercents(deposit_to_accure)) {
					jsp_parameters.put("current_action_result", "ADDITION_SUCCESS");
					jsp_parameters.put("positive_current_action_result_label", "Начисление процентов по депозиту за месяц выполнено успешно.");
				} else {
					jsp_parameters.put("current_action_result", "ADDITION_FAILURE");
					jsp_parameters.put("negative_current_action_result_label", "Ошибка начисления процентов по депозиту за месяц.");
				}

				request.setAttribute("deposit", deposit_to_accure);
				request.setAttribute("jsp_parameters", jsp_parameters);
				jsp_parameters.put("next_action_label", "Начислить");
				dispatcher_for_chosingDepositOperation.forward(request, response);
			} else {
				jsp_parameters.put("current_action", "accureDepositPercent");
				jsp_parameters.put("next_action", "accureDepositPercentPOST");

				request.setAttribute("deposit", deposit_to_accure);
				request.setAttribute("jsp_parameters", jsp_parameters);
				jsp_parameters.put("next_action_label", "Начислить");
				dispatcher_for_chosingDepositOperation.forward(request, response);
			}
		}

		if (closeDepositProgramPOST != null) {
			Deposit deposit_to_close = this.bank.getDeposit(contractId);
			String error_message = "";
			if (!PersonValidator.validateDate(request.getParameter("closureDate"))) {
				error_message += "Пожалуйста, укажите дату закрытия депозитной программы в формате [YYYY-MM-DD].";
			}
			if (error_message.equals("")) {
				if (this.bank.closeDeposit(deposit_to_close, request.getParameter("closureDate"))) {
					jsp_parameters.put("current_action_result", "ADDITION_SUCCESS");
					jsp_parameters.put("positive_current_action_result_label", "Закрытие депозита выполнено успешно.");
				} else {
					jsp_parameters.put("current_action_result", "ADDITION_FAILURE");
					jsp_parameters.put("negative_current_action_result_label", "Ошибка закрытия депозита.");
				}

				request.setAttribute("deposit", deposit_to_close);
				request.setAttribute("jsp_parameters", jsp_parameters);
				jsp_parameters.put("next_action_label", "Закрыть");
				dispatcher_for_archive.forward(request, response);
			} else {
				jsp_parameters.put("current_action", "accureDepositPercent");
				jsp_parameters.put("next_action", "accureDepositPercentPOST");

				request.setAttribute("deposit", deposit_to_close);
				request.setAttribute("jsp_parameters", jsp_parameters);
				jsp_parameters.put("next_action_label", "Начислить");
				jsp_parameters.put("error_message", error_message);
				dispatcher_for_closingDepositProgram.forward(request, response);
			}
		}
	}
}
