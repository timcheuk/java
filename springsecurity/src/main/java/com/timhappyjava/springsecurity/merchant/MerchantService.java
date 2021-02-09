package com.timhappyjava.springsecurity.merchant;

//import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {
	
	@Autowired
	private MerchantRepository merchantRepository;
	
	public List<Merchant> findAllMerchants(){
		/*if use CRUD repository, need to handle object convert
		 * List<Merchant> merchants = new ArrayList<>();
		
		merchantRepository.findAll().forEach(merchants::add);
		return merchants;*/
		return merchantRepository.findAll();
	}
	
	public Merchant findMerchantbyID(Long id) throws EntityNotFoundException{
		/*convert optional object to merchant and handle null.
		 Merchant merchant = merchantRepository.findByMerchantId(id);
		 
		if(merchant==null) throw new EntityNotFoundException("merchant with id '"+ id +"' not found.");
		return merchant;*/
		
		//convert optional object by get()
		return merchantRepository.findById(id).get();
	}
	
	public void saveMerchant(Merchant merchant) {
		merchantRepository.save(merchant);
	}

	public void deleteMerchant(Long id) {
		merchantRepository.deleteById(id);
	}

}
