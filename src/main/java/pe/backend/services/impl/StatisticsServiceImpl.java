package pe.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.backend.entities.Statistics;
import pe.backend.repositories.StatisticsRepository;
import pe.backend.services.StatisticsService;


@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	StatisticsRepository statisticsRepo;
	
	@Override
	public boolean insertar(Statistics entity) {
		boolean flag = false;
		try {
			if(statisticsRepo.save(entity) != null) {
				flag = true;
			}			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return flag;
	}

	@Override
	public List<Statistics> listarTodas() {
		return statisticsRepo.findAll();
	}

	@Override
	public Optional<Statistics> buscarPorID(int id) {
		Optional<Statistics> entity = null;
		try {
			entity = statisticsRepo.findById(id);
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return entity;
	}

	@Override
	public boolean actualizar(Statistics entity) {
		boolean flag = false;
		try {
			if( entity.getId() >1) { 
				if(statisticsRepo.save(entity) != null) {
					flag = true;
				}	
			}					
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return flag;
	}

	@Override
	public boolean eliminar(int id) {
		boolean flag = false;
		try {
			if(id>1) { 
				statisticsRepo.deleteById(id);
				flag = true;
			}else {
				flag = false;
			}			
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return flag;
	}

	@Override
	public List<Statistics> StatisticsPorPlayerID(int id) {
		List<Statistics> statistics = null;
		statistics = statisticsRepo.StatisticsPorPlayerID(id);
		return statistics;
	}

	@Override
	public List<Statistics> statisticsByMatchId(int id) {
		List<Statistics> statistics = null;
		statistics = statisticsRepo.statisticsByMatchId(id);
		return statistics;
	}

}
