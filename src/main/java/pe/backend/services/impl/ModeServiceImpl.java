package pe.backend.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.backend.entities.Match;
import pe.backend.entities.Mode;
import pe.backend.entities.Team;
import pe.backend.entities.Tournament;
import pe.backend.repositories.ModeRepository;
import pe.backend.repositories.TournamentRepository;
import pe.backend.services.MatchService;
import pe.backend.services.ModeService;

@Service
public class ModeServiceImpl implements ModeService {
	@Autowired
	ModeRepository modeRepo;
	
	@Autowired
	TournamentRepository tournamentRepo;
	
	@Autowired
	MatchService matchRepo;
	
	@Override
	public boolean insertar(Mode objMode) {
		boolean flag = false;
		try {
			if(modeRepo.save(objMode) != null) {
				flag = true;
			}			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return flag;
	}

	@Override
	public List<Mode> listarTodas() {
		// TODO Auto-generated method stub
		return modeRepo.findAll();
	}

	@Override
	public Optional<Mode> buscarPorID(int id) {
		Optional<Mode> objMode = null;
		try {
			objMode = modeRepo.findById(id);
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return objMode;
	}

	@Override
	public boolean actualizar(Mode objMode) {
		boolean flag = false;
		try {
			if( objMode.getId() >1) { 
				if(modeRepo.save(objMode) != null) {
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
				modeRepo.deleteById(id);
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
	public Team TrueResults(List<Team> equipos)
	{
		Random r = new Random();
		Team winner = equipos.get(r.nextInt(2));
		return winner;
	}
	
	@Override
	public List<Team> randomTeams(List<Team> equipos)
	{
		List<Integer> randomNumbers = new ArrayList<Integer>();
		int n = equipos.size();
		int number = 0;
		Random r = new Random();
		
		int m = n;
		
		while (n > 1)
		{
			do
			{
				number = r.nextInt(m);
			}while (randomNumbers.contains(number));
			n--;
			randomNumbers.add(number);
			int k = number;
			Team team = equipos.get(k);
			System.out.println(team.getId());
			equipos.set(k, equipos.get(n));
			System.out.println(equipos.get(k).getId());
			equipos.set(n, team);
			System.out.println(equipos.get(n).getId());
		}
		return equipos;
	}
	
	@Override
	public String GenerateMatchesMode1(List<Team> equipos, int fase, int TournamentId)
	{
		System.out.println("Entre GenerateMatchesMode1");
		List<Match> matches = new ArrayList<Match>();
		if (equipos.size() >=2)
		{
			equipos = this.randomTeams(equipos);
		}
		System.out.println("Mezcle teams");
		Match match;
		System.out.println(equipos.size());
		for (int i = 0; i < equipos.size(); i+=2)
		{
			match = new Match();
			match.setTeam1(equipos.get(i));
			match.setTeam2(equipos.get(i+1));
			
			Optional<Tournament> auxTournament = tournamentRepo.findById(TournamentId);
			match.setTournament(auxTournament.get());
			
			matches.add(match);	
			System.out.println("Agregue un match"+i);
		}
		Team winner;
		List<Team> auxTeam;
		List<Team> winteam = new ArrayList<Team>();
		System.out.println(matches.size());
		if (matches.size()>=1)
		{
			System.out.println("Entre la condicional");
			for (int i = 0; i < matches.size(); i++)
			{
				auxTeam = new ArrayList<Team>();
				auxTeam.add(matches.get(i).getTeam1());
				auxTeam.add(matches.get(i).getTeam2());
				winner = this.TrueResults(auxTeam);
				winteam.add(winner);
				
				if (winner.getId() == matches.get(i).getTeam1().getId())
				{
					matches.get(i).setWinner(matches.get(i).getTeam1());
				}
				else
				{
					matches.get(i).setWinner(matches.get(i).getTeam2());
				}
				matches.get(i).setFase(fase);
				matchRepo.insertar(matches.get(i));
				System.out.println("Inserte matches");
			}
			matchRepo.GenerateMatches1(matches);
			System.out.println("Cree las estadisticas");
		}
		fase++;
		if (winteam.size()>1) { GenerateMatchesMode1(winteam, fase, TournamentId); }
		return winteam.get(0).getName();
	}
}
