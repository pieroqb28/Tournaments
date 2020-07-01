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
import pe.backend.entities.Tournament;
import pe.backend.exception.ModeloNotFoundException;
import pe.backend.services.TournamentService;

@RestController
@RequestMapping(value="/api/tournament")
@Api(value="REST API de tournament")
@CrossOrigin
public class TournamentController{
	@Autowired
	TournamentService serviceTournament;
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Listado de todas los torneos de la BD")
	public ResponseEntity<List<Tournament>> listar() {
		try {
			List<Tournament> tournaments = serviceTournament.listarTodas();
			
			return new ResponseEntity<List<Tournament>>(tournaments, HttpStatus.OK);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			
			return new ResponseEntity<List<Tournament>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Registro de un nuevo torneo")
	public ResponseEntity insertar(@Valid @RequestBody Tournament objTournament){

		try {
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(this.serviceTournament.insertar(objTournament));
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de insertar en el controlador de torneo");
		}
	}
	
	@ApiOperation(value="Eliminar un torneo")
	@DeleteMapping(value="/{id}")
	public ResponseEntity eliminar(@PathVariable int id){
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(serviceTournament.eliminar(id));			
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de eliminar en el controlador de torneo");
		}
	}
	
	@PutMapping
	@ApiOperation(value="Actualizar un torneo")
	public ResponseEntity actualizar(@RequestBody Tournament objTournament){
		
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(serviceTournament.actualizar(objTournament));
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de actualizar en el controlador del torneo");
		}
	}
	
	@GetMapping(value="/{id}")
	@ApiOperation(value="Obtener un torneo en base a su ID")
	public ResponseEntity<Tournament> buscarPorID(@PathVariable int id){
		try {
			Optional<Tournament> objTournament = serviceTournament.buscarPorID(id);
			if(objTournament.isPresent()){
				objTournament.get().getPlayer().setTeam(null);
				return new ResponseEntity<Tournament>(objTournament.get(), HttpStatus.OK);				
			}else {
				throw new ModeloNotFoundException("Torneo no encontrado");
			}			
		} catch (Exception e) {
			throw new ModeloNotFoundException("Torneo no encontrado");
		}
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity Handler(@PathVariable int id)
	{
		try {
			System.out.print("Pase el handler de controller");
			System.out.println("Funciono!!!!!");
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(serviceTournament.Handler(id));
		} catch (Exception e)
		{
			System.out.print(e.getMessage());
			System.out.print("Problema con el Controller");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el handler, controlador de tournament");
		}	
	}
	
}
