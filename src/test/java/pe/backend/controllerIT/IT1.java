package pe.backend.controllerIT;

import static org.junit.Assert.assertTrue;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import pe.backend.BackendApplication;
import pe.backend.entities.Player;
import pe.backend.entities.Team;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)


public class IT1 {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void Pruebas() throws JSONException {
		//next
		 int playerID = 54;
		 int teamID = 20;
		 
	//INSERTAR PLAYER
		Player p = new Player();
		
		p.setName("Prueba");
		p.setGamePreferences("Examples");
		HttpEntity<Player> entity = new HttpEntity<Player>(p, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/player/"),
				HttpMethod.POST, entity, String.class);
		assertTrue(response.getStatusCode()==HttpStatus.CREATED);

	
	String SplayerID = Integer.toString(playerID);
	
	//BUSCAR PLAYER POR ID
		
		HttpEntity<String> entity2 = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response2 = restTemplate.exchange(
				createURLWithPort("/api/player/"+ SplayerID),
				HttpMethod.GET, entity2, String.class);
		
		String expected2 = "{\"name\":\"Prueba\",\"id\":"+SplayerID+",\"team\":null,\"gamePreferences\":\"Examples\"}";

		JSONAssert.assertEquals(expected2, response2.getBody(), false);
	
	

	//LISTAR PLAYERS

		HttpEntity<String> entity3 = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> response3 = restTemplate.exchange(
				createURLWithPort("/api/player/"),
				HttpMethod.GET, entity3, String.class);
		String expected3 = "[{\"name\":\"admin\",\"id\":1,\"team\":null,\"gamePreferences\":null},{\"name\":\"obispo1\",\"id\":51,\"team\":{\"name\":\"los mockitos\",\"id\":19,\"tournament\":{\"mode\":{\"id\":1,\"format\":\"Triangular de 5\"},\"name\":\"torneo fast por cuarentena\",\"id\":1,\"date\":\"2020-05-10T05:00:00.000+0000\",\"player\":{\"name\":\"admin\",\"id\":1,\"team\":null,\"gamePreferences\":null},\"nteams\":3,\"winner\":null,\"game\":\"doto\"},\"nmembers\":3},\"gamePreferences\":\"doto2\"},{\"name\":\"obispo666\",\"id\":52,\"team\":{\"name\":\"los mockitos\",\"id\":19,\"tournament\":{\"mode\":{\"id\":1,\"format\":\"Triangular de 5\"},\"name\":\"torneo fast por cuarentena\",\"id\":1,\"date\":\"2020-05-10T05:00:00.000+0000\",\"player\":{\"name\":\"admin\",\"id\":1,\"team\":null,\"gamePreferences\":null},\"nteams\":3,\"winner\":null,\"game\":\"doto\"},\"nmembers\":3},\"gamePreferences\":\"doto1\"},{\"name\":\"Prueba\",\"id\":"+SplayerID+",\"team\":null,\"gamePreferences\":\"Examples\"},{\"name\":\"Solano\",\"id\":53,\"team\":null,\"gamePreferences\":\"chess\"}]";
		JSONAssert.assertEquals(expected3, response3.getBody(), false);
	
	 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 
	// INSERTAR TEAM

		Team t = new Team();
		 
		
		t.setId(teamID);
		t.setName("Team Prueba");
		t.setNMembers(0);

		HttpEntity<Team> entity4 = new HttpEntity<Team>(t, headers);

		ResponseEntity<String> response4 = restTemplate.exchange(
				createURLWithPort("/api/team/"),
				HttpMethod.POST, entity4, String.class);
		assertTrue(response4.getStatusCode()==HttpStatus.CREATED);


	//ACTUALIZAR PLAYER
		Player p2 = new Player();
		p2.setName("Ahora tengo team");
		p2.setGamePreferences("chess");
		p2.setId(53);
		p2.setTeam(t);
		
		HttpEntity<Player> entity5 = new HttpEntity<Player>(p2, headers);

		ResponseEntity<String> response5 = restTemplate.exchange(
				createURLWithPort("/api/player/put/"),
				HttpMethod.PUT, entity5, String.class);
		assertTrue(response5.getStatusCode()==HttpStatus.OK);
		
		
	//BUSCAR PLAYERS POR TEAM ID
		
		String SteamID = Integer.toString(teamID);
		
        HttpEntity<String> entity6 = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> response6 = restTemplate.exchange(
				createURLWithPort("/api/player/team/"+SteamID),
				HttpMethod.GET, entity6, String.class);

		String expected6 = "[{\"name\":\"Ahora tengo team\",\"id\":53,\"team\":{\"name\":\"Team Prueba\",\"id\":"+SteamID+",\"tournament\":null,\"nmembers\":1},\"gamePreferences\":\"chess\"}]";
		JSONAssert.assertEquals(expected6, response6.getBody(), false);
	}
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
