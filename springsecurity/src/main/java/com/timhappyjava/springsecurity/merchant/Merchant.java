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
	private String merchant_code;
	private String merchant_name;
	private String description;

	public Merchant() {}

	public Merchant(String merchant_code, String merchant_name, String description) {
		this.merchant_code = merchant_code;
		this.merchant_name = merchant_name;
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Merchant Merchant = (Merchant) o;
		return Objects.equals(id, Merchant.id) &&
			Objects.equals(merchant_code, Merchant.merchant_code) &&
			Objects.equals(merchant_name, Merchant.merchant_name) &&
			Objects.equals(description, Merchant.description);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, merchant_code, merchant_name, description);
	}

	public String getMerchant_code() {
		return merchant_code;
	}

	public void setMerchant_code(String merchant_code) {
		this.merchant_code = merchant_code;
	}

	public String getMerchant_name() {
		return merchant_name;
	}

	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
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
			", merchant_code='" + merchant_code + '\'' +
			", merchant_name='" + merchant_name + '\'' +
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