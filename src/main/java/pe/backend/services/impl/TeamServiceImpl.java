package pe.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.backend.entities.Team;
import pe.backend.entities.Tournament;
import pe.backend.repositories.TeamRepository;
import pe.backend.services.TeamService;
import pe.backend.services.TournamentService;

@Service
public class TeamServiceImpl implements TeamService {
	@Autowired
	TeamRepository teamRepo;
	
	@Autowired
	TournamentService tournamentService;

	@Override
	public boolean insertar(Team entity) {
		boolean flag = false;
		if (teamRepo.findTeamsWithPartOfName(entity.getName()).isEmpty())
		{
			try
			{
				if(teamRepo.save(entity) != null) 
				{
					Tournament tournament = new Tournament();
					tournament = tournamentService.buscarPorID(entity.getTournament().getId()).get();
					tournament.setNTeams(tournament.getNTeams()+1);
					tournamentService.actualizar(tournament);
				
					flag = true;
				}			
			} catch (Exception e) 
				{
					System.out.print(e.getMessage());
				}
		} else {System.out.println("Ese nombre ya existe");}
		return flag;
	}

	@Override
	public List<Team> listarTodas() {
		return teamRepo.findAll();
	}

	@Override
	public Optional<Team> buscarPorID(int id) {
		Optional<Team> entity = null;
		try {
			entity = teamRepo.findById(id);
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return entity;
	}

	@Override
	public boolean actualizar(Team entity) {
		boolean flag = false;
		
		Integer tournamentId = null;
		if (entity.getTournament() != null)
		{
			tournamentId = entity.getTournament().getId(); 
		}

		System.out.println(tournamentId);
		Integer auxId = null;
		
		if (this.buscarPorID(entity.getId()).get().getTournament() != null)
		{
			auxId = this.buscarPorID(entity.getId()).get().getTournament().getId(); 
		}
		
		try {
			if (entity.getId() >= 1) {
				if (teamRepo.save(entity) != null) {
					flag = true;
					if (auxId != null) {
						Tournament oldTournament = new Tournament();
						oldTournament = tournamentService.buscarPorID(auxId).get();
						oldTournament.setNTeams(oldTournament.getNTeams() - 1);
						tournamentService.actualizar(oldTournament);
					}
					System.out.println(entity.getTournament().getId());
					Integer newId = entity.getTournament().getId();
					if (newId != auxId) {
						//System.out.println("Entreeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
						Tournament newTeam = new Tournament();
						newTeam = tournamentService.buscarPorID(entity.getTournament().getId()).get();
						newTeam.setNTeams(newTeam.getNTeams() + 1);
						tournamentService.actualizar(newTeam);
					}
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
		int auxId = this.buscarPorID(id).get().getTournament().getId();
		try {
			if(id>1) { 
				teamRepo.deleteById(id);
				
				Tournament tournament = new Tournament();
				tournament = tournamentService.buscarPorID(auxId).get();
				tournament.setNTeams(tournament.getNTeams()-1);
				tournamentService.actualizar(tournament);
				
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
	public List<Team> getTeamsByTournamentId(int id)
	{
		List<Team> teams = teamRepo.getTeamsByTournamentId(id);
		return teams;	
	}

	@Override
	public List<Team> findTeamsWithPartOfName(String name) {
		List<Team> teams = null;
		teams = teamRepo.findTeamsWithPartOfName(name);
		return teams;
	}

	@Override
	public List<Team> getPlayerId(int id) {
		List<Team> t = null;
		t = teamRepo.getPlayerId(id);		
		return t;
	}


}
