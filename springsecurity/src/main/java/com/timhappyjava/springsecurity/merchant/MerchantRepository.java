package com.timhappyjava.springsecurity.merchant;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;


public interface MerchantRepository extends JpaRepository<Merchant, Long> {
	//@Query(value = "SELECT * FROM merchant" )
	//List<Merchant> findAllMerchants();
	
}
