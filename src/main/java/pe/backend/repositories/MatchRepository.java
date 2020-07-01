package pe.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.backend.entities.Match;

@Repository
public interface MatchRepository 
				extends JpaRepository<Match, Integer> {
	
	public List<Match> MatchesPorTournamentID(int id);

}
