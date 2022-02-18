package group.brewdaytwo.domain.model.ingrediente;

import java.util.Objects;

public class Ingrediente {
	
	private String nome;
	private String tipo;
	
	public Ingrediente(String nome, String tipo) {
		super();
		this.nome = nome;
		this.tipo = tipo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(nome, tipo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingrediente other = (Ingrediente) obj;
		return Objects.equals(nome, other.nome) && Objects.equals(tipo, other.tipo);
	}
	
	@Override
	public String toString() {
		return "Ingrediente [Nome=" + getNome() + ", Tipo=" + getTipo() + "]";
	}

}
