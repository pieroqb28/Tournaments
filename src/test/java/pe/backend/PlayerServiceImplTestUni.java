package pe.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import pe.backend.entities.Player;
import pe.backend.entities.Team;
import pe.backend.repositories.PlayerRepository;
import pe.backend.services.TeamService;
import pe.backend.services.impl.PlayerServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerServiceImplTestUni {

	@Autowired
	private PlayerServiceImpl playerServiceImpl;
	
	@MockBean
	PlayerRepository playerRepo;

	@MockBean
	TeamService teamService;
	
	@Test
	public void listarTodos() {
		Mockito.when(playerRepo.findAll()).thenReturn(new ArrayList<Player>());

		List<Player> respuesta = playerServiceImpl.listarTodas();
		
		Assert.assertNotNull(respuesta);
	}
	
	@Test
	public void insertar() {
		Player jugador = new Player();
		jugador.setId(2);
		jugador.setName("Luis");
		jugador.setGamePreferences("Dota");
		
		Mockito.when(playerRepo.save(jugador)).thenReturn(jugador);

		boolean respuesta = playerServiceImpl.insertar(jugador);
		
		Assert.assertTrue(respuesta);
	}
	
	@Test
	public void buscarPorId() {
		int x = 2;
		
		Player jugador = new Player();
		jugador.setId(x);
		jugador.setName("Luis");
		jugador.setGamePreferences("Dota");
		
		Optional<Player> optjugador = Optional.of(jugador);
		
		Mockito.when(playerRepo.findById(x)).thenReturn(optjugador);

		Optional<Player> respuesta = playerServiceImpl.buscarPorID(x);
		
		Assert.assertNotNull(respuesta);
	}
	
	@Test
	public void actualizar() {
		int x = 2;
		
		Team team = new Team();
		team.setId(1);
		team.setName("Navi");
		team.setNMembers(5);
		
		Player jugador = new Player();
		jugador.setId(x);
		jugador.setName("Eduardo");
		jugador.setGamePreferences("Tf2");
		jugador.setTeam(team);
		
		Optional<Player> optjugador = Optional.of(jugador);
		
		Mockito.when(playerRepo.findById(x)).thenReturn(optjugador);
		Mockito.when(playerRepo.save(jugador)).thenReturn(jugador);

		boolean respuesta = playerServiceImpl.actualizar(jugador);
		
		Assert.assertTrue(respuesta);
	}
	
	@Test
	public void eliminar() {
		int x = 2;
		
		Team team = new Team();
		team.setId(x);
		team.setName("Navi");
		team.setNMembers(5);
		
		Player jugador = new Player();
		jugador.setId(x);
		jugador.setName("Eduardo");
		jugador.setGamePreferences("Tf2");
		jugador.setTeam(team);
		
		Optional<Player> optjugador = Optional.of(jugador);
		Optional<Team> optteam = Optional.of(team);
		
		Mockito.when(playerRepo.findById(x)).thenReturn(optjugador);
		Mockito.when(teamService.buscarPorID(x)).thenReturn(optteam);
		Mockito.when(teamService.actualizar(team)).thenReturn(true);
		boolean respuesta = playerServiceImpl.eliminar(x);
		
		Assert.assertTrue(respuesta);
	}

	@Test
	public void getPlayersFromTeamId() {
		int x = 2;
		
		Mockito.when(playerRepo.getPlayersFromTeamId(x)).thenReturn(new ArrayList<Player>());

		List<Player> respuesta = playerServiceImpl.getPlayersFromTeamId(x);
		
		Assert.assertNotNull(respuesta);
	}

	@Test
	public void findPlayerByName() {
		int x = 2;
		
		Player jugador = new Player();
		jugador.setId(x);
		jugador.setName("Luis");
		jugador.setGamePreferences("Dota");
				
		Mockito.when(playerRepo.findPlayerByName(jugador.getName())).thenReturn(jugador);

		Player respuesta = playerServiceImpl.findPlayerByName(jugador.getName());
		
		Assert.assertNotNull(respuesta);
	}
}
