package pe.backend.services;

import java.util.List;
import java.util.Optional;

import pe.backend.entities.Tournament;


public interface TournamentService {

	public boolean insertar(Tournament entity);
	
	public List<Tournament> listarTodas();
	
	public Optional<Tournament> buscarPorID(int id);
	
	public boolean actualizar(Tournament entity);
	
	public boolean eliminar(int id);

	public boolean CanGenerate(int id);

	public boolean Handler(int tournamentId);

	public String generateMatches(int tournamentId, int fase);	
	
	public List<Tournament> FindTournamentByName(String name);
	
	public Tournament findByHostId(int id);
	
}
