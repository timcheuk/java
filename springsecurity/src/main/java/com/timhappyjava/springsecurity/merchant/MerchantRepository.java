package com.timhappyjava.springsecurity.merchant;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MerchantRepository extends CrudRepository<Merchant, Long> {
	//@Query(value = "SELECT * FROM merchant" )
	//List<Merchant> findAllMerchants();
}
