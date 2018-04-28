package entity;

public class Operation {

	private String operationId = "";
	private String operationDescription = "";
	private String debit = "";
	private String credit = "";

	public Operation() {
		this.setOperationId("");
		this.setOperationDescription("");
		this.setDebit("");
		this.setCredit("");
	}

	public Operation(String operationId, String operationDescription, String debit, String credit) {
		this.setOperationId(operationId);
		this.setOperationDescription(operationDescription);
		this.setDebit(debit);
		this.setCredit(credit);
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getOperationDescription() {
		return operationDescription;
	}

	public void setOperationDescription(String operationDescription) {
		this.operationDescription = operationDescription;
	}

	public String getDebit() {
		return debit;
	}

	public void setDebit(String debit) {
		this.debit = debit;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}
}
