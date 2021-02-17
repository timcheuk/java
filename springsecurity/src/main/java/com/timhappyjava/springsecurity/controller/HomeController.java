package com.timhappyjava.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.timhappyjava.springsecurity.merchant.Merchant;
import com.timhappyjava.springsecurity.merchant.MerchantService;
import com.timhappyjava.springsecurity.player.Player;
import com.timhappyjava.springsecurity.player.PlayerService;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Controller
public class HomeController {
	
	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private PlayerService playerService;
	
	@RequestMapping(value = {"/home","/"})
	public String home() {
		return "home";
	}
	
	//Service to get all merchant in a list
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
	
	//edit merchant need to separate with create merchant ? 
	@RequestMapping(value = "/merchant", method = RequestMethod.POST)
	public String saveMerchant(@ModelAttribute("new_merchant") Merchant merchant) {
		
		if (merchant.getId() == null) 
			merchantService.createMerchant(merchant);
		else 
			merchantService.saveMerchant(merchant.getId(),merchant);
		
	    return "redirect:/merchant";
	}
	
	@RequestMapping("/edit_merchant/{id}")
	public ModelAndView showEditMerchantForm(@PathVariable(name="id") Long id) {
		//this edit_merchant is the template html
		ModelAndView mav = new ModelAndView("edit_merchant");
		
		//this edit_merchant is the merchant object which is being edit
		Merchant merchant = merchantService.findMerchantByID(id);
		mav.addObject("edit_merchant",merchant);
		
		return mav;
	}
	
	@RequestMapping(value = "/player", 
			params = {"id","name","merchantname"}, method = RequestMethod.GET)
	public String showPlayerBySearch(Model model, @RequestParam Map<String,String> allParams) {
		model.addAttribute("listPlayers", playerService.showPlayerBySearchbyEM(this.merchantService,allParams));
		return "player";
	}
	//replace if else condition with example matcher. alternative specification and query.
	/*public String showPlayerBySearch(Model model, @RequestParam Map<String,String> allParams) {	
		if (allParams.get("id").equals("") && allParams.get("name").equals("") && allParams.get("merchantname").equals("")) {
			model.addAttribute("listPlayers", playerService.findAllPlayers());
		}else if(!allParams.get("id").equals("")) {
			log.info("search with id in controlle");
			long id = Long.parseLong(allParams.get("id"));
			model.addAttribute("listPlayers", playerService.findPlayerByID(id));
		}else if (!allParams.get("name").equals("") && allParams.get("merchantname").equals("")) {
			log.info("search with name in controller");
			model.addAttribute("listPlayers", playerService.findPlayerByName(allParams.get("name")));
		}else if (!allParams.get("merchantname").equals("") && allParams.get("name").equals("")) {
			log.info("search with merchantname in controller");
			Merchant merchant = merchantService.findMerchantByCode(allParams.get("merchantname"));
			model.addAttribute("listPlayers", playerService.findPlayerByPlayermerchant(merchant));		
		}
		return "player";
		
	}*/
	
	
	@RequestMapping(value = "/player", method = RequestMethod.GET)
	public String listPlayers(Model model) {
		log.info("player list function");
		model.addAttribute("listPlayers", playerService.findAllPlayers());
	    return "player";
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
