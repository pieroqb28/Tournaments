package pe.backend.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="matches")
@NamedQueries({
@NamedQuery(
		name="Match.MatchesPorTournamentID", 
		query="SELECT m FROM Match m join Tournament t on m.Tournament = t.Id where t.Id = ?1"
)
})
@ApiModel(value="Representa la tabla match.")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","inspection"})
public class Match{	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(value="Es la PK de la tabla, entero, auto incremental")
	private int Id;
	
	@JsonIgnoreProperties("match")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "winner")
	private Team Winner;
	
	private int Fase;
	
	@JsonIgnoreProperties("match")
	@NotNull(message = "El team no puede ser nulo")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team1")
	private Team Team1;
	
	@JsonIgnoreProperties("match")
	@NotNull(message = "El team no puede ser nulo")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team2")
	private Team Team2;
	
	@JsonIgnoreProperties("match")
	@NotNull(message = "El torneo no puede ser nulo")
	@ManyToOne(fetch=FetchType.LAZY)
	private Tournament Tournament;

	@JsonIgnoreProperties("match")
	@OneToMany(mappedBy="Match", fetch=FetchType.LAZY)
	private List<Statistics> Statistics;
	
	public int getId() {
		return Id;
	}

	@JsonBackReference(value="match-statistics")
	public List<Statistics> getStatistics() {
		return Statistics;
	}

	public void setStatistics(List<Statistics> statistics) {
		Statistics = statistics;
	}

	public void setId(int id) {
		Id = id;
	}

	public Team getWinner() {
		return Winner;
	}

	public void setWinner(Team winner) {
		Winner = winner;
	}

	public int getFase() {
		return Fase;
	}

	public void setFase(int fase) {
		Fase = fase;
	}

	public Team getTeam1() {
		return Team1;
	}

	public void setTeam1(Team team1) {
		Team1 = team1;
	}

	public Team getTeam2() {
		return Team2;
	}

	public void setTeam2(Team team2) {
		Team2 = team2;
	}
	
	
	public Tournament getTournament() {
		return Tournament;
	}

	public void setTournament(Tournament tournament) {
		Tournament = tournament;
	}
}