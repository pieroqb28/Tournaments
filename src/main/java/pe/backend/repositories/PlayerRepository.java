package pe.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.backend.entities.Player;

@Repository
public interface PlayerRepository 
				extends JpaRepository<Player, Integer> {
	
	public List<Player> getPlayersFromTeamId(int id);		
	
	public Player findPlayerByName(String name);

}
