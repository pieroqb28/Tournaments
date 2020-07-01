package pe.backend.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="modes")
@ApiModel(value="Representa la tabla modos.")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","inspection"})
public class Mode{	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(value="Es la PK de la tabla, entero, auto incremental")
	private int Id;
	
	@NotNull(message = "El Formato no puede ser nulo")
	@Size(min = 3, max = 75, message = "El formato debe estar entre 3 y 75 caracteres")
	private String Format;
	
	@JsonIgnoreProperties("mode")
	@OneToMany(mappedBy="mode", fetch=FetchType.LAZY)
	private List<Tournament> tournaments;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	@JsonBackReference(value="mode-tournament")
	public List<Tournament> getTournaments() {
		return tournaments;
	}

	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}
	
	

}