package pe.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.backend.entities.Statistics;

@Repository
public interface StatisticsRepository 
				extends JpaRepository<Statistics, Integer> {
	
	public List<Statistics> StatisticsPorPlayerID(int id);
	
	public List<Statistics> statisticsByMatchId(int id);

}
