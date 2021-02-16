package com.timhappyjava.springsecurity.player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.timhappyjava.springsecurity.currency.Currency;
import com.timhappyjava.springsecurity.merchant.Merchant;

@Entity
@Table(name="player")//no need to add this annotation if same name)
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="player_id")
	private Long id;
	private String player_name;
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="currency_id")
	private Currency player_currency;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="merchant_id")
	private Merchant player_merchant;

	private double player_balance;

	public Player() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlayer_name() {
		return player_name;
	}

	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}

	public double getPlayer_balance() {
		return player_balance;
	}

	public void setPlayer_balance(double player_balance) {
		this.player_balance = player_balance;
	}

	public Currency getPlayer_currency() {
		return player_currency;
	}

	public void setPlayer_currency(Currency player_currency) {
		this.player_currency = player_currency;
	}

	public Merchant getPlayer_merchant() {
		return player_merchant;
	}

	public void setPlayer_merchant(Merchant player_merchant) {
		this.player_merchant = player_merchant;
	}
	
	

}