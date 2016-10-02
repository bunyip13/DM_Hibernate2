package hibernate;



public class Account {
	private Integer id;
	private Long amount;
	private String iban;
	private Integer clientid;
	
	public Account() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public Integer getClientid() {
		return clientid;
	}

	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", amount=" + amount + ", iban=" + iban + ", clientid=" + clientid + "]";
	}
}
