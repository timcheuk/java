package com.timhappyjava.springsecurity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@Autowired
	private MerchantService merchantService;
	
	@RequestMapping(value = {"/home","/"})
	public String home() {
		return "home.html";
	}
	
	@RequestMapping("/list")
	public List<Merchant> getAllMerchants() {
		List<Merchant> listMerchants = merchantService.findAllMerchants();
		return listMerchants;
		//return "list.html";
	}
	
	@RequestMapping("/merchant")
	public String merchant() {
		return "merchant.html";
	}
	
	@RequestMapping("/login")
	public String loginPage() {
		return "login.html";
	}
	
	@RequestMapping("/logout-success")
	public String logoutPage() {
		return "logout.html";
	}
	/*
	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name="id") Long id){
		service.delete(id);
		
		return "redirect:/";
	}
	 * */
	
	@GetMapping("/403")
	public String error403() {
		return "403";
	}
}
