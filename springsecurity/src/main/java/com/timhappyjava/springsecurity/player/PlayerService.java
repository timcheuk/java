package com.timhappyjava.springsecurity.player;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
@Service
public class PlayerService {
	@Autowired
	private PlayerRepository playerRepository;
	
	//@PostFilter("filterObject.getPlayer_merchant().getMerchant_code() == 'GDG'") work. The context is correct
	@PostFilter("hasPermission(filterObject.getPlayer_merchant(), 'READ')") 
	//not work. The merchant do not same as object identify
	public List<Player> findAllPlayers(){
		
		return playerRepository.findAll();
	}

	public Player findPlayerID(Long id) throws EntityNotFoundException{

		 Optional<Player> player = playerRepository.findById(id);

		if(player==null) 
			throw new EntityNotFoundException("player with id '"+ id +"' not found.");
		else {
			return player.get();
		}
	}

}
