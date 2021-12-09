package com.nttdata.breno.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ArCondicionado {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer Id;	

	
	@Column(nullable = false)
	private Integer temperatura;
		
	@Column(nullable = true)
	private String horario;
	
	@Column(nullable = true)
	private String programa;
	
	@Column(nullable = false)
	private String status;
	
	@Column(nullable = false)
	private boolean modoAuto;

	
	
	public boolean isModoAuto() {
		return modoAuto;
	}

	public void setModoAuto(boolean modoAuto) {
		this.modoAuto = modoAuto;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Integer temperatura) {
		this.temperatura = temperatura;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}
	
	
	
	

}
