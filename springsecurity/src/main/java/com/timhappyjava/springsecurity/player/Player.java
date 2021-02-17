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
	private String playername;
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="currency_id")
	private Currency playercurrency;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="merchant_id")
	private Merchant playermerchant;

	private double playerbalance;

	public Player() {}

	@Override
	public String toString() {
		return "Player [id=" + id + ", playername=" + playername + ", playercurrency=" + playercurrency
				+ ", playermerchant=" + playermerchant + ", playerbalance=" + playerbalance + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlayername() {
		return playername;
	}

	public void setPlayername(String playername) {
		this.playername = playername;
	}

	public double getPlayerbalance() {
		return playerbalance;
	}

	public void setPlayerbalance(double playerbalance) {
		this.playerbalance = playerbalance;
	}

	public Currency getPlayercurrency() {
		return playercurrency;
	}

	public void setPlayercurrency(Currency playercurrency) {
		this.playercurrency = playercurrency;
	}

	public Merchant getPlayermerchant() {
		return playermerchant;
	}

	public void setPlayermerchant(Merchant playermerchant) {
		this.playermerchant = playermerchant;
	}
	
	

}