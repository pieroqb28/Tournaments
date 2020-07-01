package pe.backend.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="teams")
@NamedQueries({
	@NamedQuery(
			name = "Team.getTeamsByTournamentId",
			query = "select t from Team t where t.Tournament.Id = ?1"
			),
	@NamedQuery(
			name = "Team.getPlayerId",
			query = "select t from Team t join Player p on t.Id = p.Team where p.Id = ?1"
			),
	@NamedQuery(
			name = "Team.findTeamsWithPartOfName",
			query = "SELECT t FROM Team t WHERE t.Name =:name"
	)
	})
@ApiModel(value="Representa la tabla team.")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","inspection"})
public class Team{	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(value="Es la PK de la tabla, entero, auto incremental")
	private int Id;
	
	@NotNull(message = "El valor no puede ser nulo")
	@Size(min = 3, max = 75, message = "El nombre debe estar contenido entre 3 y 75 caracteres")
	private String Name;
	
	@NotNull(message = "El valor no puede ser nulo")
	@Min(value = 0, message = "La cantidad de miembros no debe ser menor a 0")
	private int NMembers;	
		
	
	@ManyToOne(fetch = FetchType.LAZY)	
	private Tournament Tournament;
		
	
	@JsonIgnoreProperties("team")
	@OneToMany(mappedBy="Team", fetch=FetchType.LAZY)
	private List<Player> Players;	
	

	public int getId() {
		return Id;
	}


	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getNMembers() {
		return NMembers;
	}

	public void setNMembers(int nMembers) {
		NMembers = nMembers;
	}

	public Tournament getTournament() {
		return Tournament;
	}

	public void setTournament(Tournament Tournament) {
		this.Tournament = Tournament;
	}

	
	@JsonBackReference(value="player-team")
	public List<Player> getPlayers() {
		return Players;
	}

	public void setPlayers(List<Player> players) {
		Players = players;
	}

}