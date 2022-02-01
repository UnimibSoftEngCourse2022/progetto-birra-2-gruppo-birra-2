package group.brewdaytwo.utente.model;

import java.util.Objects;

public class Utente {
	private String nickname;
	private String email;
	private String password;
	
	public Utente(String nickname, String email, String password) {
		super();
		this.nickname = nickname;
		this.email = email;
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email, nickname, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utente other = (Utente) obj;
		return Objects.equals(email, other.email) && Objects.equals(nickname, other.nickname)
				&& Objects.equals(password, other.password);
	}
	
	@Override
	public String toString() {
		return "Utente [getNickname()=" + getNickname() + ", getEmail()=" + getEmail() + ", getPassword()="
				+ getPassword() + "]";
	}

}
