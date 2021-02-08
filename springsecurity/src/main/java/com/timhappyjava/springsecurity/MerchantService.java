package com.timhappyjava.springsecurity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {
	
	@Autowired
	private MerchantRepository merchantRepository;
	
	public List<Merchant> findAllMerchants(){
		List<Merchant> merchants = new ArrayList<>();
		
		merchantRepository.findAll().forEach(merchants::add);
		return merchants;
	}
	
	public void addMerchant(Merchant merchant) {
		merchantRepository.save(merchant);
	}
	

}
