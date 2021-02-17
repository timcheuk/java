package com.timhappyjava.springsecurity.player;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.timhappyjava.springsecurity.merchant.Merchant;
import com.timhappyjava.springsecurity.merchant.MerchantService;
@Service
public class PlayerService {
	@Autowired
	private PlayerRepository playerRepository;
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@PostFilter("hasPermission(filterObject?.getPlayermerchant(), 'READ')") 
	public List<Player> findAllPlayers(){
		log.info("list all in service");
		
		return playerRepository.findAll();
	}

	@PostAuthorize("hasPermission(returnObject.getPlayermerchant(), 'READ')") 
	public Player findPlayerByID(Long id) throws EntityNotFoundException{
		log.info("search with id in service");
		 Optional<Player> player = playerRepository.findById(id);

		if(player.isEmpty()) {
			log.info("findByID with null result in service");
			throw new EntityNotFoundException("player with id '"+ id +"' not found.");
		}
		else {
			return player.get();
		}
	}
	
	@PostFilter("hasPermission(filterObject?.getPlayermerchant(), 'READ')") 
	public List<Player> findPlayerByName(String playername) throws EntityNotFoundException{
		log.info("search with playername in service");
		
		return playerRepository.findByPlayername(playername);
	}
	
	@PostFilter("hasPermission(filterObject?.getPlayermerchant(), 'READ')") 
	public List<Player> findPlayerByPlayermerchant(Merchant playermerchant) {
		log.info("search with merchant name in service");
		 List<Player> players = playerRepository.findByPlayermerchant(playermerchant);

		if(players.isEmpty()) 
			throw new EntityNotFoundException("player with playermerchant '"+ playermerchant +"' not found.");
		else {
			return players;
		}
	}
	
	@PostFilter("hasPermission(filterObject?.getPlayermerchant(), 'READ')") 
	public List<Player> showPlayerBySearchbyEM(MerchantService merchantService, Map<String,String> allParams)
	{
		/* Build Example and ExampleMatcher object */
		//Long default =0 , need to ignore paths player balance
		ExampleMatcher customExampleMatcher = ExampleMatcher.matching().withIgnorePaths("playerbalance");
		
		/* Build Search object */
		Player player= new Player();
		long id = 0;
		if(!allParams.get("id").equals("")) {
			try
			{
				id = Long.parseLong(allParams.get("id"));
				player.setId(id);
			}
			catch (Exception e) {
				log.debug("Supplied filter text is not a Number");
			}
		}
		if(!allParams.get("name").equals("")) {
			player.setPlayername(allParams.get("name"));
			customExampleMatcher.withMatcher("playername", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		}
		
		if (!allParams.get("merchantname").equals("")){
			Merchant merchant = merchantService.findMerchantByCode(allParams.get("merchantname"));
			player.setPlayermerchant(merchant);
		}

		System.out.println("Example player: "+player);
		Example<Player> playerExample= Example.of(player, customExampleMatcher);

		/* Get employees based on search criteria*/
		return playerRepository.findAll(playerExample);
	}

}
