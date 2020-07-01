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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pe.backend.entities.Match;
import pe.backend.exception.ModeloNotFoundException;
import pe.backend.services.MatchService;

@RestController
@RequestMapping(value="/api/match")
@Api(value="REST API de match")
@CrossOrigin
public class MatchController{
	@Autowired
	MatchService matchService;
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Listado de todos los matches de la BD")
	public ResponseEntity<List<Match>> listar() {
		try {
			List<Match> matches = matchService.listarTodas();
			
			return new ResponseEntity<List<Match>>(matches, HttpStatus.OK);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			
			return new ResponseEntity<List<Match>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Registro de un nuevo Match")
	public ResponseEntity<Match> insertar(@Valid @RequestBody Match entity){
		
		try {
			boolean flag = matchService.insertar(entity);
			if(flag) {
				return new ResponseEntity<Match>(HttpStatus.OK);
			}else{
				return new ResponseEntity<Match>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new ResponseEntity<Match>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value="Eliminar un Match")
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Match> eliminar(@PathVariable int id){
		try {
			boolean flag = matchService.eliminar(id);
			if(flag){
				return new ResponseEntity<Match>(HttpStatus.OK);				
			}else {
				return new ResponseEntity<Match>(HttpStatus.NOT_FOUND);
			}			
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new ResponseEntity<Match>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/{id}")
	@ApiOperation(value="Obtener un torneo en base a su ID")
	public ResponseEntity<Match> buscarPorID(@PathVariable int id){
		try {
			Optional<Match> entity = matchService.buscarPorID(id);
			if(entity.isPresent()){
				return new ResponseEntity<Match>(entity.get(), HttpStatus.OK);				
			}else {
				throw new ModeloNotFoundException("Torneo no encontrado");
			}			
		} catch (Exception e) {
			throw new ModeloNotFoundException("Torneo no encontrado");
		}
	}
	@GetMapping(value="/tournament/{id}")
	@ApiOperation(value="Obtener las match de un torneo en base a su ID")
	public ResponseEntity<List<Match>> MatchesByTournamentID(@PathVariable int id){
		try {
			List<Match> entity = matchService.MatchesPorTournamentID(id);
			if(!entity.isEmpty()){
				return new ResponseEntity<List<Match>>(entity, HttpStatus.OK);				
			}else {
				throw new ModeloNotFoundException("Matches no encontradas");				
			}			
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new ResponseEntity<List<Match>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
