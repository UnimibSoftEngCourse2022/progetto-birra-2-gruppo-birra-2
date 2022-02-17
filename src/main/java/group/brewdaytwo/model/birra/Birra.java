package group.brewdaytwo.model.birra;

import java.util.Objects;

public class Birra {
	
	private int ID; 
	private String note;  
	private double quantita;
	private String autore; 
	private int IDRicetta; 
	
	public Birra(int iD, String n, double q, String aut, int idr) {
		this.ID = iD;
		this.note = n; 
		this.quantita = q;
		this.autore = aut; 
		this.IDRicetta = idr; 
	}
	
	
	public int getID() {
		return this.ID; 
	}
	
	public int getIDRicetta() {
		return this.IDRicetta; 
	}
	
	public double getQuantita() {
		return this.quantita; 
	}
	
	public String getAutore() {
		return autore;
	}
	
	public String getNote() {
		return this.note; 
	}
	
	
	public void setID(int iD) {
		this.ID = iD;
	}
	
	public void setIDRicetta(int r) {
		this.IDRicetta = r; 
	}
	
	public void setQuantita(double q) {
		this.quantita = q; 
	}
	
	public void setAutore(String autore) {
		this.autore = autore;
	}
	
	public void setNote(String n){
		this.note=n; 
	}
	
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(ID, note, quantita, autore, IDRicetta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Birra other = (Birra) obj;
		return this.ID == other.ID && Objects.equals(this.IDRicetta, other.IDRicetta) && Objects.equals(this.quantita, other.quantita)
				&& Objects.equals(this.autore, other.autore) && Objects.equals(this.note, other.note); 
	}

	@Override
	public String toString() {
		return  " Birra [ID= " + this.getID() + ", ID Ricetta=" + this.getIDRicetta() + ", Autore birra=" +this.getAutore() + 
				", Quantità birra prodotta= "+ this.getQuantita()+ ", Note= "+ this.getNote()+ "]";
				
	}
	
}
