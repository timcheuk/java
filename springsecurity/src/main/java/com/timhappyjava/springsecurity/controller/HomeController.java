package com.timhappyjava.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.timhappyjava.springsecurity.merchant.Merchant;
import com.timhappyjava.springsecurity.merchant.MerchantService;

@Controller
public class HomeController {
	
	@Autowired
	private MerchantService merchantService;
	
	@RequestMapping(value = {"/home","/"})
	public String home() {
		return "home";
	}
	
	
	@RequestMapping(value = "/merchant", method = RequestMethod.GET)
	public String listMerchants(Model model) {
		model.addAttribute("listMerchants", merchantService.findAllMerchants());
	    return "merchant";
	}
	
	@RequestMapping(value = "/delete_merchant/{id}", method = RequestMethod.POST)
	public String deletMerchant(@PathVariable(name="id") Long id){
		merchantService.deleteMerchant(id);
		
		return "redirect:/merchant";
	}
	
	@RequestMapping("/new_merchant")
	public String showNewMerchantForm(Model model) {
		Merchant merchant = new Merchant();
		model.addAttribute("new_merchant",merchant);
		return "new_merchant";
	}
	
	@RequestMapping(value = "/merchant", method = RequestMethod.POST)
	public String saveMerchant(@ModelAttribute("new_merchant") Merchant merchant) {
		merchantService.saveMerchant(merchant);
		
	    return "redirect:/merchant";
	}
	
	@RequestMapping("/edit_merchant/{id}")
	public ModelAndView showEditMerchantForm(@PathVariable(name="id") Long id) {
		//this edit_merchant is the template html
		ModelAndView mav = new ModelAndView("edit_merchant");
		
		//this edit_merchant is the merchant object which is being edit
		Merchant merchant = merchantService.findMerchantbyID(id);
		mav.addObject("edit_merchant",merchant);
		
		return mav;
	}
	
	@RequestMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@RequestMapping("/logout-success")
	public String logoutPage() {
		return "logout";
	}
	
	@GetMapping("/deny")
	public String accessdeny() {
		return "deny";
	}
	
}
