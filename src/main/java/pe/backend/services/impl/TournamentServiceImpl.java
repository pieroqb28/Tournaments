package pe.backend.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.backend.entities.Team;
import pe.backend.entities.Tournament;
import pe.backend.repositories.TournamentRepository;
import pe.backend.services.ModeService;
import pe.backend.services.TeamService;
import pe.backend.services.TournamentService;

@Service
public class TournamentServiceImpl implements TournamentService {
	@Autowired
	TournamentRepository tournamentRepo;	
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	ModeService modeService;

	@Override
	public boolean insertar(Tournament objTournament) {
		boolean flag = false;
		
		if (tournamentRepo.FindTournamentByName(objTournament.getName()).isEmpty())
		{
			if (tournamentRepo.findByHostId(objTournament.getPlayer().getId()) != null)
			{
				System.out.print("Host ya tiene un torneo");
			}
			else
			{					
				try {
					if(tournamentRepo.save(objTournament) != null) {				
						flag = true;			
					}			
				}
				catch (Exception e) {
					System.out.print(e.getMessage());
					return flag;
				}
			}
		}
		else {System.out.println("EXISTE TORNEO CON EL MISMO NOMBRE");}
		
		return flag;
	}

	@Override
	public List<Tournament> listarTodas() {
		List<Tournament> aux = tournamentRepo.findAll();
		return aux;
	}

	@Override
	public Optional<Tournament> buscarPorID(int id) {
		Optional<Tournament> objTournament = null;
		try {
			objTournament = tournamentRepo.findById(id);
			objTournament.get().getPlayer().setTeam(null);
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		System.out.println(objTournament.get().getDate());
		return objTournament;
	}

	@Override
	public boolean actualizar(Tournament objTournament) {
		boolean flag = false;
		try {
			if( objTournament.getId() >=1) { 
				if(tournamentRepo.save(objTournament) != null) {
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
				tournamentRepo.deleteById(id);
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
	public boolean CanGenerate(int id)
	{
		boolean can = false;
		Tournament result = new Tournament();
		try
		{
			result = tournamentRepo.findById(id).get();
			if (result.getWinner() == null || result.getWinner() == "") can = true;
			if (result.getNTeams() % 4 != 0 && result.getNTeams()<2) can = false;
		}
		catch (Exception e)
		{
			System.out.print("Crash CanGenerate");
		}
		return can;
	}
	
	@Override
	public boolean Handler(int tournamentId)
	{
		String winner;
		if (CanGenerate(tournamentId))
		{
			winner = this.generateMatches(tournamentId, 1);
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println(winner);
			System.out.println("Pase generateMatches");
			Tournament auxTournament = tournamentRepo.findById(tournamentId).get();
			auxTournament.setWinner(winner);
			this.actualizar(auxTournament);
			return true;
		}
		return false;
	}
	
	@Override
	public String generateMatches(int tournamentId, int fase)
	{
		Tournament tournament = this.buscarPorID(tournamentId).get();
		List<Team> auxTeams = teamService.getTeamsByTournamentId(tournamentId);
		
		switch (tournament.getMode().getId())
		{
			case 1:
			{
				System.out.print("Caso 1");
				return modeService.GenerateMatchesMode1(auxTeams, fase, tournamentId);
			}	
			default:
			{
				return modeService.GenerateMatchesMode1(auxTeams, fase, tournamentId);
			}
		
		}
	}

	@Override
	public List<Tournament> FindTournamentByName(String name) {
		List<Tournament> tournaments = null;
		tournaments = tournamentRepo.FindTournamentByName(name);
		return tournaments;
	}

	@Override
	public Tournament findByHostId(int id) {
		Tournament tournament = new Tournament();
		tournament = tournamentRepo.findByHostId(id);
		return tournament;
	}

}
