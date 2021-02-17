package com.timhappyjava.springsecurity;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.test.context.support.WithMockUser;

import com.timhappyjava.springsecurity.merchant.Merchant;
import com.timhappyjava.springsecurity.merchant.MerchantRepository;

public class merchantPermissiontest {
	
	@Autowired
	private MerchantRepository merchantRepository;
	
	@Test
	@WithMockUser(username = "timcheuk")
	public void givenUsernametimcheuk_whenFindMerchantId_thenOK(){
		final Long id = (long)1;
	    Optional<Merchant> merchant = merchantRepository.findById(id);
		if(merchant==null) {
			System.out.println("merchant is null");
		}
		assertEquals(id,merchant.get().getId());
	}

	@Test(expected = AccessDeniedException.class)
	@WithMockUser(username = "timcheuk")
	public void givenUsernametimcheuk_whenUpdateMerchantId1_thenFail(){
	    Merchant merchant = new Merchant();
	    merchant.setId((long)1);
	    merchant.setMerchantcode("GDG2");
	    merchant.setMerchantname("Golden Diamond Gaming");
	    merchantRepository.save(merchant);
	}


}
