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
import pe.backend.entities.Team;
import pe.backend.exception.ModeloNotFoundException;
import pe.backend.services.TeamService;

@RestController
@RequestMapping(value="/api/team")
@Api(value="REST API de team")
@CrossOrigin
public class TeamController{
	@Autowired
	TeamService teamService;
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Listado de todos los equipos de la BD")
	public ResponseEntity<List<Team>> listar() {
		try {
			List<Team> teams = teamService.listarTodas();
			
			return new ResponseEntity<List<Team>>(teams, HttpStatus.OK);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			
			return new ResponseEntity<List<Team>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Registro de un nuevo equipo")
	public ResponseEntity insertar(@Valid @RequestBody Team entity){
		
		try {
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(this.teamService.insertar(entity));
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en insertar, teamcontroller");
		}
	}
	
	@ApiOperation(value="Eliminar un equipo")
	@DeleteMapping(value="/{id}")
	public ResponseEntity eliminar(@PathVariable int id){
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(teamService.eliminar(id));				
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en eliminar, teamcontroller");
		}
	}
	
	@PutMapping
	@ApiOperation(value="Actualizar un equipo")
	public ResponseEntity actualizar(@RequestBody Team entity){
		
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(teamService.actualizar(entity));
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en actualizar, teamcontroller");
		}
	}
	
	@GetMapping(value="/{id}")
	@ApiOperation(value="Obtener un torneo en base a su ID")
	public ResponseEntity<Team> buscarPorID(@PathVariable int id){
		try {
			Optional<Team> entity = teamService.buscarPorID(id);
			if(entity.isPresent()){
				return new ResponseEntity<Team>(entity.get(), HttpStatus.OK);				
			}else {
				throw new ModeloNotFoundException("Torneo no encontrado");
			}			
		} catch (Exception e) {
			throw new ModeloNotFoundException("Torneo no encontrado");
		}
	}
	
	@GetMapping(value="/tournament/{id}")
	public ResponseEntity<List<Team>> buscarPlayersPorTeamID(@PathVariable int id){
		try {
			List<Team> entity = teamService.getTeamsByTournamentId(id);
			if(entity.isEmpty() == false){
				return new ResponseEntity<List<Team>>(entity, HttpStatus.OK);		
			}else {
				throw new ModeloNotFoundException("Jugador no encontrado");
			}			
		} catch (Exception e) {
			throw new ModeloNotFoundException("Jugador no encontrado");
		}
	}
}
