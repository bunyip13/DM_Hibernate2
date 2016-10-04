package hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public class Client {
	private Integer id;
	private Set<Account> accounts = new HashSet<>();
	private String name;
	private String surname;

	public Client() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", accounts=" + accounts + ", name=" + name + ", surname=" + surname + "]";
	}
}
