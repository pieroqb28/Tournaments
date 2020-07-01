package pe.backend.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pe.backend.entities.Player;
import pe.backend.exception.ModeloNotFoundException;
import pe.backend.services.PlayerService;

@RestController
@RequestMapping(value="/api/player")
@Api(value="REST API de player")
@CrossOrigin
public class PlayerController{
	@Autowired
	PlayerService playerService;
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Listado de todos los jugadores de la BD")
	public ResponseEntity<List<Player>> listar() {
		try {
			List<Player> players = playerService.listarTodas();
			
			return new ResponseEntity<List<Player>>(players, HttpStatus.OK);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			
			return new ResponseEntity<List<Player>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Registro de un nuevo jugador")
	public ResponseEntity insertar(@Valid @RequestBody Player entity){
		
		try {
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(this.playerService.insertar(entity));
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en insertar, player controller");
		}
	}
	
	@DeleteMapping(value="/{id}")
	@ApiOperation(value="Eliminar un jugador")
	public ResponseEntity eliminar(@PathVariable int id){
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(playerService.eliminar(id));		
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en eliminar, player controller");
		}
	}
	
	@PutMapping(value="/put")
	@ApiOperation(value="Actualizar un jugador")
	public ResponseEntity actualizar(@RequestBody Player entity){
		
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(playerService.actualizar(entity));
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en actualizar, player controller");
		}
	}
	
	@GetMapping(value="/{id}")
	@ApiOperation(value="Obtener un jugadr en base a su ID")
	public ResponseEntity<Player> buscarPorID(@PathVariable int id){
		try {
			Optional<Player> entity = playerService.buscarPorID(id);
			if(entity.isPresent()){
				return new ResponseEntity<Player>(entity.get(), HttpStatus.OK);				
			}else {
				throw new ModeloNotFoundException("Jugador no encontrado");
			}			
		} catch (Exception e) {
			throw new ModeloNotFoundException("Jugador no encontrado");
		}
	}
	
	@GetMapping(value="/team/{id}")
	public ResponseEntity<List<Player>> buscarPlayersPorTeamID(@PathVariable int id){
		try {
			List<Player> entity = playerService.getPlayersFromTeamId(id);
			if(entity.isEmpty() == false){
				return new ResponseEntity<List<Player>>(entity, HttpStatus.OK);		
			}else {
				throw new ModeloNotFoundException("Jugador no encontrado");
			}			
		} catch (Exception e) {
			throw new ModeloNotFoundException("Jugador no encontrado");
		}
	}
	
}
