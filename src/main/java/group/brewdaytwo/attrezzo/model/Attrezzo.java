package group.brewdaytwo.attrezzo.model;

import java.util.Objects;

public class Attrezzo {

	private int ID;
	private String nome;
	private double capacitaMax;
	
	public Attrezzo(int iD, String nome, double capacitaMax) {
		super();
		ID = iD;
		this.nome = nome;
		this.capacitaMax = capacitaMax;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getCapacitaMax() {
		return capacitaMax;
	}
	public void setCapacitaMax(double capacitaMax) {
		this.capacitaMax = capacitaMax;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(ID, capacitaMax, nome);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attrezzo other = (Attrezzo) obj;
		return ID == other.ID && Double.doubleToLongBits(capacitaMax) == Double.doubleToLongBits(other.capacitaMax)
				&& Objects.equals(nome, other.nome);
	}
	
	@Override
	public String toString() {
		return "Attrezzo [ID=" + getID() + ", Nome=" + getNome() + ", CapacitaMax=" + getCapacitaMax()
				+ "]";
	}
}
