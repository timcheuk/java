package com.timhappyjava.springsecurity.currency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="currency")//no need to add this annotation if same name)
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="currency_id")
	private Long currency_id;
	@Column(name="currency_code")
	private String currencycode;
	
	public Long getId() {
		return currency_id;
	}
	public void setId(Long id) {
		this.currency_id = id;
	}
	public String getCurrencycode() {
		return currencycode;
	}
	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}
}