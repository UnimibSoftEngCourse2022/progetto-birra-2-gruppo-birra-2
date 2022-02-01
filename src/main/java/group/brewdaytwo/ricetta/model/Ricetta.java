package group.brewdaytwo.ricetta.model;

import java.util.Objects;

public class Ricetta {
	
	private int ID;
	private String nome;
	private String procedimento;
	private String descrizione;
	private String autore;
	
	public Ricetta(int iD, String nome, String procedimento, String descrizione, String autore) {
		super();
		ID = iD;
		this.nome = nome;
		this.procedimento = procedimento;
		this.descrizione = descrizione;
		this.autore = autore;
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
	public String getProcedimento() {
		return procedimento;
	}
	public void setProcedimento(String procedimento) {
		this.procedimento = procedimento;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getAutore() {
		return autore;
	}
	public void setAutore(String autore) {
		this.autore = autore;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID, autore, descrizione, nome, procedimento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ricetta other = (Ricetta) obj;
		return ID == other.ID && Objects.equals(autore, other.autore) && Objects.equals(descrizione, other.descrizione)
				&& Objects.equals(nome, other.nome) && Objects.equals(procedimento, other.procedimento);
	}

	@Override
	public String toString() {
		return "Ricetta [ID=" + getID() + ", Nome=" + getNome() + ", Procedimento=" + getProcedimento()
				+ ", Descrizione=" + getDescrizione() + ", Autore=" + getAutore() + "]";
	}
	
	

}
