package com.timhappyjava.springsecurity.merchant;

import java.util.Optional;

//import java.util.List;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.security.access.prepost.PostAuthorize;
//import org.springframework.security.access.prepost.PostFilter;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreFilter;


public interface MerchantRepository extends JpaRepository<Merchant, Long> {

//public interface MerchantRepository extends CrudRepository<Merchant, Long> {
	//check base class by Ctrl click on BasePermission

	Optional<Merchant> findByMerchantcode(String code);
	
	/* move all check to service level in order to handle null return for optional object before Filter and Authorize*/
    //@PostFilter("hasPermission(filterObject, 'READ')")
	//@Query(value = "select * from merchant m", nativeQuery = true)
    //public List<Merchant> findAll();

     //@PostAuthorize("hasPermission(returnObject, 'READ')")
    //public Optional<Merchant> findById(Long id);
    
	//@PreAuthorize("hasPermission(#Merchant, 'WRITE')")
    //public Merchant save(Merchant merchant);

	//@Query(value = "update m SET m.merchant_code= :merchant_code, m.merchant_name= :merchant_name WHERE m.merchant_id = :id",nativeQuery = true)
	//public void saveMerchantbyID(Long id, String merchant_code, String merchant_name);
    
    //@PreAuthorize("hasPermission(#Merchant, 'DELETE')")
	//public void deleteById(Long id);

	
}
