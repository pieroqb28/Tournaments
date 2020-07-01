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
import pe.backend.entities.Statistics;
import pe.backend.exception.ModeloNotFoundException;
import pe.backend.services.StatisticsService;

@RestController
@RequestMapping(value="/api/statistics")
@Api(value="REST API de statistics")
@CrossOrigin
public class StatisticsController{
	@Autowired
	StatisticsService statisticsService;
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Listado de todas las estadisticas de la BD")
	public ResponseEntity<List<Statistics>> listar() {
		try {
			List<Statistics> statistics = statisticsService.listarTodas();
			
			return new ResponseEntity<List<Statistics>>(statistics, HttpStatus.OK);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			
			return new ResponseEntity<List<Statistics>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Registro de estadisticas")
	public ResponseEntity<Statistics> insertar(@Valid @RequestBody Statistics entity){
		
		try {
			boolean flag = statisticsService.insertar(entity);
			if(flag) {
				return new ResponseEntity<Statistics>(HttpStatus.OK);
			}else{
				return new ResponseEntity<Statistics>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new ResponseEntity<Statistics>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value="Eliminar estadisticas")
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Statistics> eliminar(@PathVariable int id){
		try {
			boolean flag = statisticsService.eliminar(id);
			if(flag){
				return new ResponseEntity<Statistics>(HttpStatus.OK);				
			}else {
				return new ResponseEntity<Statistics>(HttpStatus.NOT_FOUND);
			}			
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new ResponseEntity<Statistics>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value="/{id}")
	@ApiOperation(value="Obtener una estadistica en base a su ID")
	public ResponseEntity<Statistics> buscarPorID(@PathVariable int id){
		try {
			Optional<Statistics> entity = statisticsService.buscarPorID(id);
			if(entity.isPresent()){
				return new ResponseEntity<Statistics>(entity.get(), HttpStatus.OK);				
			}else {
				throw new ModeloNotFoundException("Estadistica no encontrado");
			}			
		} catch (Exception e) {
			throw new ModeloNotFoundException("Estadistica no encontrado");
		}
	}
	
	@GetMapping(value="/player/{id}")
	@ApiOperation(value="Obtener estadistica de un jugador en base a su ID")
	public ResponseEntity<List<Statistics>> StatisticsPorPlayerID(@PathVariable int id){
		try {
			List<Statistics> entity = statisticsService.StatisticsPorPlayerID(id);
			if(!entity.isEmpty()){
				return new ResponseEntity<List<Statistics>>(entity, HttpStatus.OK);				
			}else {
				throw new ModeloNotFoundException("Estadisticas no encontradas");
			}			
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new ResponseEntity<List<Statistics>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/match/{id}")
	@ApiOperation(value="Obtener estadistica de un match en base a su ID")
	public ResponseEntity<List<Statistics>> statisticsByMatchId(@PathVariable int id){
		try {
			List<Statistics> entity = statisticsService.statisticsByMatchId(id);
			if(!entity.isEmpty()){
				return new ResponseEntity<List<Statistics>>(entity, HttpStatus.OK);				
			}else {
				throw new ModeloNotFoundException("Estadisticas no encontradas");
			}			
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return new ResponseEntity<List<Statistics>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
