package pe.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.backend.entities.Player;
import pe.backend.entities.Team;
import pe.backend.repositories.PlayerRepository;
import pe.backend.services.PlayerService;
import pe.backend.services.TeamService;


@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	PlayerRepository playerRepo;
	
	@Autowired
	TeamService teamService;

	@Override
	public boolean insertar(Player entity) {
		boolean flag = false;
		if (playerRepo.findPlayerByName(entity.getName())==null)
		{
			try {
				if(playerRepo.save(entity) != null) {
					flag = true;
					Team team = new Team();
					team = teamService.buscarPorID(entity.getTeam().getId()).get();
					team.setNMembers(team.getNMembers()+1);
					teamService.actualizar(team);			
					System.out.println(team.getNMembers());	
				}			
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {System.out.println("Jugador con el mismo nombre");}
		
		return flag;
	}

	@Override
	public List<Player> listarTodas() {
		List<Player> aux = playerRepo.findAll();	
		return aux;
	}

	@Override
	public Optional<Player> buscarPorID(int id) {
		Optional<Player> entity = null;
		try {
			entity = playerRepo.findById(id);
			entity.get().getTeam().getTournament().getPlayer().setTeam(null);
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return entity;
	}
	
	
	@Override
	public boolean actualizar(Player entity) {
		boolean flag = false;
		
		Integer teamId = null;
		if (entity.getTeam() != null)
		{
			teamId = entity.getTeam().getId(); 
		}

		System.out.println(teamId);
		Integer auxId = null;
		
		if (this.buscarPorID(entity.getId()).get().getTeam() != null)
		{
			auxId = this.buscarPorID(entity.getId()).get().getTeam().getId(); 
		}

		System.out.println(auxId);
		
		try {
			if (entity.getId() >= 1) {
				if (playerRepo.save(entity) != null) {
					flag = true;
					if (auxId != null) {
						Team oldTeam = new Team();
						oldTeam = teamService.buscarPorID(auxId).get();
						oldTeam.setNMembers(oldTeam.getNMembers() - 1);
						teamService.actualizar(oldTeam);
					}
					System.out.println(entity.getTeam().getId());
					Integer newId = entity.getTeam().getId();
					if (newId != auxId) {
						//System.out.println("Entreeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
						Team newTeam = new Team();
						newTeam = teamService.buscarPorID(entity.getTeam().getId()).get();
						newTeam.setNMembers(newTeam.getNMembers() + 1);
						teamService.actualizar(newTeam);
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
		int auxId = this.buscarPorID(id).get().getTeam().getId();
		try {
			if(id>1) { 
				playerRepo.deleteById(id);
				
				Team team = new Team();
				team = teamService.buscarPorID(auxId).get();
				team.setNMembers(team.getNMembers()-1);
				teamService.actualizar(team);
				
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
	public List<Player> getPlayersFromTeamId(int id)
	{
		List<Player> players = playerRepo.getPlayersFromTeamId(id);
		return players;
	}

	@Override
	public Player findPlayerByName(String name) {
		Player player = new Player();
		player = playerRepo.findPlayerByName(name);
		return player;
	}
	
	
}
