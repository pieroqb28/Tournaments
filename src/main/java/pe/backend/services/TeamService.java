package pe.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import pe.backend.entities.Team;

public interface TeamService {
	
	public boolean insertar(Team entity);
	
	public List<Team> listarTodas();
	
	public Optional<Team> buscarPorID(int id);
	
	public boolean actualizar(Team entity);
	
	public boolean eliminar(int id);

	public List<Team> getTeamsByTournamentId(int id);	
	
	public List<Team> findTeamsWithPartOfName(@Param("name") String name);
	
	public List<Team> getPlayerId(int id);

}
