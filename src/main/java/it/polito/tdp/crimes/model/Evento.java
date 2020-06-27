package it.polito.tdp.crimes.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{
	
	enum EventType{
		CRIMINE, ARRIVA_AGENTE, GESTITO;
	}

	private Event crimine;
	private LocalDateTime data;
	private EventType tipo;
	
	public Evento(Event crimine, LocalDateTime data, EventType tipo) {
		super();
		this.crimine = crimine;
		this.data = data;
		this.tipo = tipo;
	}
	public Event getCrimine() {
		return crimine;
	}
	public void setCrimine(Event crimine) {
		this.crimine = crimine;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public EventType getTipo() {
		return tipo;
	}
	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}
	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.data.compareTo(o.data);
	}
	
	
}
