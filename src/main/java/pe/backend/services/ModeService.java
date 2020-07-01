package pe.backend.services;

import java.util.List;
import java.util.Optional;

import pe.backend.entities.Mode;
import pe.backend.entities.Team;


public interface ModeService {

	public boolean insertar(Mode entity);
	
	public List<Mode> listarTodas();
	
	public Optional<Mode> buscarPorID(int id);
	
	public boolean actualizar(Mode entity);
	
	public boolean eliminar(int id);

	public Team TrueResults(List<Team> equipos);

	public String GenerateMatchesMode1(List<Team> equipos, int fase, int TournamentId);

	public List<Team> randomTeams(List<Team> equipos);	
	
}
