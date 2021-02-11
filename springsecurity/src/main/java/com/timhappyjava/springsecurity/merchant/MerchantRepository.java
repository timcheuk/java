package com.timhappyjava.springsecurity.merchant;

import java.util.List;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PostFilter;


public interface MerchantRepository extends JpaRepository<Merchant, Long> {
	//@Query(value = "SELECT * FROM merchant" )
    @PostFilter("hasPermission(filterObject, 'WRITE')")
    List<Merchant> findAll();
	//List<Merchant> findAllMerchants();
	
}
