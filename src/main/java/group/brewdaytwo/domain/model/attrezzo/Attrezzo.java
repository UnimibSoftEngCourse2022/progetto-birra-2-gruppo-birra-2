package group.brewdaytwo.domain.model.attrezzo;

import java.util.Objects;

public class Attrezzo {

	private int iD;
	private String nome;
	private double capacitaMax;
	
	public Attrezzo(int iD, String nome, double capacitaMax) {
		super();
		this.iD = iD;
		this.nome = nome;
		this.capacitaMax = capacitaMax;
	}
	public int getID() {
		return iD;
	}
	public void setID(int iD) {
		this.iD = iD;
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
		return Objects.hash(iD, capacitaMax, nome);
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
		return iD == other.iD && Double.doubleToLongBits(capacitaMax) == Double.doubleToLongBits(other.capacitaMax)
				&& Objects.equals(nome, other.nome);
	}
	
	@Override
	public String toString() {
		return "Attrezzo [ID=" + getID() + ", Nome=" + getNome() + ", CapacitaMax=" + getCapacitaMax()
				+ "]";
	}
}
