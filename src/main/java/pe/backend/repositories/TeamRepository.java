package pe.backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.backend.entities.Team;

@Repository
public interface TeamRepository 
				extends JpaRepository<Team, Integer> {
	
	public List<Team> getTeamsByTournamentId(int id);
	
	public List<Team> findTeamsWithPartOfName(@Param("name") String name);
	
	public List<Team> getPlayerId(int id);
}


