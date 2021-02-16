package com.timhappyjava.springsecurity.merchant;

//import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {
	
	@Autowired
	private MerchantRepository merchantRepository;
	
	@PostFilter("hasPermission(filterObject, 'READ')")
	public List<Merchant> findAllMerchants(){
		/*if use CRUD repository, need to handle object convert
		 * List<Merchant> merchants = new ArrayList<>();
		
		merchantRepository.findAll().forEach(merchants::add);
		return merchants;*/
		return merchantRepository.findAll();
	}
	
	@PostAuthorize("hasPermission(returnObject, 'READ')")
	public Merchant findMerchantbyID(Long id) throws EntityNotFoundException{
		//convert optional object to merchant and handle null.
		 Optional<Merchant> merchant = merchantRepository.findById(id);

		if(merchant==null) 
			throw new EntityNotFoundException("merchant with id '"+ id +"' not found.");
		else {
			return merchant.get();
		}

		//convert optional object to Merchant by get()
		//return merchantRepository.findById(id).get();
	}
	
	//check has Permission in this class PermissionEvaluator 
	//@PreAuthorize("hasPermission(#this.id,'Merchant','WRITE')")
	/*public void saveMerchant( Merchant merchant) {
		merchantRepository.saveMerchantbyID(merchant.getId(),merchant.getMerchant_code(),merchant.getMerchant_name());
	}*/
	@PreAuthorize("hasPermission(#merchant, 'WRITE')")
	public void saveMerchant(Long id, Merchant merchant) {
		merchantRepository.save(merchant);
	}
	
	@PreAuthorize ("hasAuthority('ADMIN_AUTHORITY')")
	public void createMerchant(Merchant merchant) {
		merchantRepository.save(merchant);
	}

	@PreAuthorize ("hasAuthority('ADMIN_AUTHORITY')")
	public void deleteMerchant(Long id) {
		merchantRepository.deleteById(id);
	}

}
