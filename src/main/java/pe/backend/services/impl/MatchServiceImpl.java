package pe.backend.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.backend.entities.Match;
import pe.backend.entities.Player;
import pe.backend.entities.Statistics;
import pe.backend.repositories.MatchRepository;
import pe.backend.repositories.PlayerRepository;
import pe.backend.repositories.StatisticsRepository;
import pe.backend.services.MatchService;


@Service
public class MatchServiceImpl implements MatchService {

	@Autowired
	MatchRepository matchRepo;
	@Autowired
	PlayerRepository playerRepo;
	@Autowired
	StatisticsRepository statisticsRepo;
	

	@Override
	public boolean insertar(Match entity) {
		boolean flag = false;
		try {
			if(matchRepo.save(entity) != null) {
				flag = true;
			}			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return flag;
	}

	@Override
	public List<Match> listarTodas() {
		return matchRepo.findAll();
	}

	@Override
	public Optional<Match> buscarPorID(int id) {
		Optional<Match> entity = null;
		try {
			entity = matchRepo.findById(id);
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return entity;
	}

	@Override
	public boolean actualizar(Match entity) {
		boolean flag = false;
		try {
			if( entity.getId() >1) {
				if(matchRepo.save(entity) != null) {
					flag = true;
				}	
			}					
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return flag;
	}

	@Override
	public boolean eliminar(int id) {
		boolean flag = false;
		try {
			if(id>1) { 
				matchRepo.deleteById(id);
				flag = true;
			}else {
				flag = false;
			}			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return flag;
	}	
	
	@Override
	public int GenerateMatches1(List<Match> matches)
	{
		for (int i = 0; i<matches.size(); i++)
		{
			Random r = new Random();
			List<Player> team1count = playerRepo.getPlayersFromTeamId(matches.get(i).getTeam1().getId());		
			for (int j = 0; j<team1count.size(); j++)
			{
				Statistics statistics = new Statistics();
				statistics.setMatch(matches.get(i));
				statistics.setPlayer(team1count.get(j));
				statistics.setKills(r.nextInt(30));
				statistics.setDeaths(r.ints(1, 2, 15).findFirst().getAsInt());
				statistics.setAssits(r.ints(1, 10, 40).findFirst().getAsInt());
				statistics.setDamage(r.ints(1, 5000, 13000).findFirst().getAsInt());
				statisticsRepo.save(statistics);
			}
			System.out.println("Cree las estadisticas 1");
			
			List<Player> team2count = playerRepo.getPlayersFromTeamId(matches.get(i).getTeam2().getId());
			
			for (int j = 0; j<team2count.size(); j++)
			{
				System.out.println("Entre a generar");
				Statistics statistics = new Statistics();
				statistics.setMatch(matches.get(i));
				statistics.setPlayer(team2count.get(j));
				statistics.setKills(r.nextInt(30));
				statistics.setDeaths(r.ints(1, 2, 15).findFirst().getAsInt());
				statistics.setAssits(r.ints(1, 10, 40).findFirst().getAsInt());
				statistics.setDamage(r.ints(1, 5000, 13000).findFirst().getAsInt());
				statisticsRepo.save(statistics);
				System.out.println("Genere");
			}
			System.out.println("Cree las estadisticas 2");
		}
		return 0;		
	}

	@Override
	public List<Match> MatchesPorTournamentID(int id) {
		List<Match> matches = null;
		matches = matchRepo.MatchesPorTournamentID(id);		
		return matches;
	}
}
