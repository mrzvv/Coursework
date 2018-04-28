package entity;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import client_actions.AddPerson;
import client_actions.DeletePerson;
import client_actions.EditPerson;
import deposit_actions.AccurePercent;
import deposit_actions.AddDeposit;
import deposit_actions.CloseDeposit;
import deposit_actions.RefillDeposit;
import deposit_actions.ReturnAccountMoney;
import deposit_actions.TakeAwayAccountMoney;
import util.DBWorker;
import util.DBDataEjector;

public class Bankwork {

	// Хранилище записей о людях.
	private LinkedHashMap<String, Person> persons = new LinkedHashMap<String, Person>();
	private HashMap<String, Deposit> activeDeposits = new HashMap<String, Deposit>();
	private HashMap<String, Deposit> closedDeposits = new HashMap<String, Deposit>();
	private HashMap<String, Account> bankAccounts = new HashMap<String, Account>();

	private HashMap<String, HashMap<String, Account>> clientAccounts = new HashMap<String, HashMap<String, Account>>();

	// Объект для работы с БД.
	private DBWorker db = DBWorker.getInstance();

	// Указатель на экземпляр класса.
	private static Bankwork instance = null;

	// Метод для получения экземпляра класса (реализован Singleton).
	public static Bankwork getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null) {
			instance = new Bankwork();
		}
		return instance;
	}

	// При создании экземпляра класса из БД извлекаются все записи.
	protected Bankwork() throws ClassNotFoundException, SQLException {

		// Данные о клиентах.
		DBDataEjector.ejectClients(persons, this.db);

		// Данные об активных депозитах.
		DBDataEjector.ejectActiveDeposits(activeDeposits, this.db);

		// Данные о закрытых депозитах.
		DBDataEjector.ejectClosedDeposits(closedDeposits, this.db);

		// Данные о кассе банка в белорусских рублях.
		if (DBDataEjector.ejectAccountOperations("bank_cash_byn", this.db) != null) {
			this.bankAccounts.put("Касса банка (BYN)", DBDataEjector.ejectAccountOperations("bank_cash_byn", this.db));
		} else {
			System.out.println("An error has occured trying to eject bank cash byn account info");
		}

		// Данные о кассе банка в долларах США.
		if (DBDataEjector.ejectAccountOperations("bank_cash_usd", this.db) != null) {
			this.bankAccounts.put("Касса банка (USD)", DBDataEjector.ejectAccountOperations("bank_cash_usd", this.db));
		} else {
			System.out.println("An error has occured trying to eject bank cash usd account info");
		}

		// Данные о кассе банка в евро.
		if (DBDataEjector.ejectAccountOperations("bank_cash_eur", this.db) != null) {
			this.bankAccounts.put("Касса банка (EUR)", DBDataEjector.ejectAccountOperations("bank_cash_eur", this.db));
		} else {
			System.out.println("An error has occured trying to eject bank cash eur account info");
		}

		// Данные о счетах клиентов.
		if (DBDataEjector.ejectClientAccounts(clientAccounts, this.db) != null) {
			this.clientAccounts = DBDataEjector.ejectClientAccounts(clientAccounts, this.db);
		} else {
			System.out.println("An error has occured trying to eject client accounts info");
		}

		// Данные о счете фонда развития банка.
		if (DBDataEjector.ejectAccountOperations("bank_development_fund", this.db) != null) {
			this.bankAccounts.put("Счет фонда развития банка",
					DBDataEjector.ejectAccountOperations("bank_development_fund", this.db));
		} else {
			System.out.println("An error has occured trying to eject bank development fund account info");
		}
	}

	public static int generateBankKey() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			Random rand = new Random();
			int x = rand.nextInt(10);
			sb.append(x);
		}
		return Integer.parseInt(sb.toString());
	}

	// Добавление записи о человеке.
	public boolean addPerson(Person person) {
		if (AddPerson.add(person)) {
			this.persons.put(person.getId(), person);
			return true;
		}
		return false;
	}

	// Обновление записи о человеке.
	public boolean updatePerson(Person person) {
		if (EditPerson.edit(person)) {
			this.persons.put(person.getId(), person);
			return true;
		}
		return false;
	}

	// Удаление записи о человеке.
	public boolean deletePerson(Person person) {
		if (DeletePerson.delete(person)) {
			for (Map.Entry<String, String> deposit : person.getDeposites().entrySet()) {
				this.activeDeposits.remove(deposit.getKey());
			}
			this.persons.remove(person.getId());
			return true;
		}
		return false;
	}

	// Добавление депозита.
	public boolean addDeposit(Deposit deposit, Person person) {
		if (AddDeposit.add(deposit, this.clientAccounts,
				this.bankAccounts.get("Касса банка (" + deposit.getCurrency() + ")"))) {
			this.persons.get(person.getId()).getDeposites().put(deposit.getContractId(), deposit.getDepositType());
			this.activeDeposits.put(deposit.getContractId(), deposit);

			return true;
		}
		return false;
	}

	// Пополнение депозита.
	public boolean refillDepositAccount(Deposit deposit, String fillingSum) {
		if (RefillDeposit.refill(deposit, fillingSum,
				this.bankAccounts.get("Касса банка (" + deposit.getCurrency() + ")"),
				this.clientAccounts.get(deposit.getClientId()).get("Текущий счет в " + deposit.getCurrency()))) {
			this.activeDeposits.put(deposit.getContractId(), deposit);
			return true;
		}
		return false;
	}

	// Изъятие средств со счета клиента.
	public boolean takeAwayAccountMoney(Deposit deposit, String takeAwaySum, String accountType, boolean forBankNeeds) {
		if (TakeAwayAccountMoney.take(deposit, takeAwaySum, accountType,
				this.bankAccounts.get("Касса банка (" + deposit.getCurrency() + ")"),
				this.clientAccounts.get(deposit.getClientId()).get("Текущий счет в " + deposit.getCurrency()),
				this.clientAccounts.get(deposit.getClientId()).get("Процентный счет в " + deposit.getCurrency()),
				this.bankAccounts.get("Счет фонда развития банка"), forBankNeeds, this.db)) {
			this.activeDeposits.put(deposit.getContractId(), deposit);
			return true;
		}
		return false;
	}

	// Возврат средств на счет клиента.
	public boolean returnAccountMoney(Deposit deposit) {
		if (ReturnAccountMoney.returnMoney(deposit,
				this.clientAccounts.get(deposit.getClientId()).get("Текущий счет в " + deposit.getCurrency()),
				this.bankAccounts.get("Счет фонда развития банка"), this.db)) {
			this.activeDeposits.put(deposit.getContractId(), deposit);
			return true;
		}
		return false;
	}

	// Начисление процентов по депозиту.
	public boolean accureDepositPercents(Deposit deposit) {
		if (AccurePercent.accure(deposit, this.bankAccounts.get("Счет фонда развития банка"),
				this.clientAccounts.get(deposit.getClientId()).get("Процентный счет в " + deposit.getCurrency()))) {
			this.activeDeposits.put(deposit.getContractId(), deposit);
			return true;
		}
		return false;
	}

	// Закрытие депозита.
	public boolean closeDeposit(Deposit deposit, String closureDate) {
		if (CloseDeposit.close(deposit, closureDate,
				this.bankAccounts.get("Касса банка (" + deposit.getCurrency() + ")"),
				this.clientAccounts.get(deposit.getClientId()).get("Текущий счет в " + deposit.getCurrency()),
				this.clientAccounts.get(deposit.getClientId()).get("Процентный счет в " + deposit.getCurrency()),
				this.db)) {
			this.activeDeposits.remove(deposit.getContractId());
			this.closedDeposits.put(deposit.getContractId(), deposit);
			return true;
		}
		return false;
	}

	public LinkedHashMap<String, Person> getClients() {
		return this.persons;
	}

	public HashMap<String, Deposit> getActiveDeposits() {
		return this.activeDeposits;
	}

	public HashMap<String, Deposit> getClosedDeposits() {
		return this.closedDeposits;
	}

	public Deposit getDeposit(String contractId) {
		return this.activeDeposits.get(contractId);
	}

	public Person getPerson(String id) {
		return this.persons.get(id);
	}

	public HashMap<String, Account> getBankAccounts() {
		return this.bankAccounts;
	}

	public HashMap<String, HashMap<String, Account>> getClientAccounts() {
		return this.clientAccounts;
	}

}
