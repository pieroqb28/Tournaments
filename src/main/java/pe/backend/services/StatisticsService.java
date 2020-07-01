package pe.backend.services;

import java.util.List;
import java.util.Optional;

import pe.backend.entities.Statistics;

public interface StatisticsService {
	
	public boolean insertar(Statistics entity);
	
	public List<Statistics> listarTodas();
	
	public Optional<Statistics> buscarPorID(int id);
	
	public boolean actualizar(Statistics entity);
	
	public boolean eliminar(int id);

	public List<Statistics> StatisticsPorPlayerID(int id);
	
	public List<Statistics> statisticsByMatchId(int id);
}
