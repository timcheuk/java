package com.timhappyjava.springsecurity.merchant;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="merchant")//no need to add this annotation if same name)
public class Merchant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="merchant_code")
	private String merchantcode;
	@Column(name="merchant_name")
	private String merchantname;
	private String description;

	public Merchant() {}

	public Merchant(String merchantcode, String merchantname, String description) {
		this.merchantcode = merchantcode;
		this.merchantname = merchantname;
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Merchant Merchant = (Merchant) o;
		return Objects.equals(id, Merchant.id) &&
			Objects.equals(merchantcode, Merchant.merchantcode) &&
			Objects.equals(merchantname, Merchant.merchantname) &&
			Objects.equals(description, Merchant.description);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, merchantcode, merchantname, description);
	}

	public String getMerchantcode() {
		return merchantcode;
	}

	public void setMerchantcode(String merchantcode) {
		this.merchantcode = merchantcode;
	}

	public String getMerchantname() {
		return merchantname;
	}

	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Merchant{" +
			"id=" + id +
			", merchant_code='" + merchantcode + '\'' +
			", merchant_name='" + merchantname + '\'' +
			", description='" + description + '\'' +
			'}';
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}