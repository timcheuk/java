package com.timhappyjava.springsecurity.player;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.timhappyjava.springsecurity.merchant.Merchant;

public interface PlayerRepository extends JpaRepositoryImplementation<Player, Long>, QueryByExampleExecutor<Player>{

	List<Player> findByPlayername(String playername);

	List<Player> findByPlayermerchant(Merchant playermerchant);

}
