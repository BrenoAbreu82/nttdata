package com.nttdata.breno.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// Classe Model

@Entity
public class ArCondicionado {
	
	//Primary Key
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer Id;	

	// Temperatura do Ar Condicionado
	@Column(nullable = false)
	private Integer temperatura;
		
	//Horário que será executando o agendamento liga/desliga
	@Column(nullable = true)
	private String horario;
	
	//função liga ou desliga que será utilizado junto com o horário
	@Column(nullable = true)
	private String programa;
	
	//status do Ar Condicionado: ligado ou desligado
	@Column(nullable = false)
	private String status;
	
	// modo Automático para o ligar/desligar automático de acordo com a temperatura local
	@Column(nullable = false)
	private boolean modoAuto;	
	
	
	//Construtores
	public ArCondicionado() {		
	}
	
	public ArCondicionado(Integer id, Integer temperatura, String horario, String programa, String status,
			boolean modoAuto) {
		super();
		Id = id;
		this.temperatura = temperatura;
		this.horario = horario;
		this.programa = programa;
		this.status = status;
		this.modoAuto = modoAuto;
	}

	//Setters and Getters
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
