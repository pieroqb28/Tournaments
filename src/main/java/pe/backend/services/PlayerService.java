package pe.backend.services;

import java.util.List;
import java.util.Optional;

import pe.backend.entities.Player;

public interface PlayerService {
	
	public boolean insertar(Player entity);
	
	public List<Player> listarTodas();
	
	public Optional<Player> buscarPorID(int id);
	
	public boolean actualizar(Player entity);
	
	public boolean eliminar(int id);

	public List<Player> getPlayersFromTeamId(int id);
	
	public Player findPlayerByName(String name);

}
