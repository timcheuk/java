package com.timhappyjava.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.timhappyjava.springsecurity.merchant.Merchant;
import com.timhappyjava.springsecurity.merchant.MerchantService;

@Controller
public class HomeController {
	
	@Autowired
	private MerchantService merchantService;
	
	@RequestMapping(value = {"/home","/"})
	public String home() {
		return "home.html";
	}
	
	@RequestMapping(value = "/merchant", method = RequestMethod.GET)
	public String listMerchants(Model model) {
		model.addAttribute("listMerchants", merchantService.findAllMerchants());
	    return "merchant.html";
	}
	
	@RequestMapping(value = "/merchant", method = RequestMethod.POST)
	public String updateMerchant(@ModelAttribute("merchant") Merchant merchant) {
		merchantService.addMerchant(merchant);
		
	    return "redirect:/merchant";
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
