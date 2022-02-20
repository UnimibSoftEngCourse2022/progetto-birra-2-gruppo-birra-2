package group.brewdaytwo.services.dao.utente;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import group.brewdaytwo.domain.model.utente.Utente;

public class UtenteDAOImpl implements UtenteDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public UtenteDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(Utente u) {
		String sql = "INSERT INTO progetto_brewday.users (nickname, email, password)"
					+ " VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, u.getNickname(), u.getEmail().toLowerCase(),u.getPassword());
	}
	
	@Override
	public void update(Utente u) {
		String sql = "UPDATE progetto_brewday.users SET email=?, password=?, "
					+ "WHERE nickname=?";
		jdbcTemplate.update(sql, u.getEmail().toLowerCase(), u.getPassword(), u.getNickname());

	}

	@Override
	public void delete(String utenteNick) {
		String sql = "DELETE FROM progetto_brewday.users WHERE nickname=?";
		jdbcTemplate.update(sql, utenteNick);

	}

	@Override
	public Utente check(String utenteNick,String utentePwd) {
		String[] args = {utenteNick, utentePwd};
		String sql = "SELECT * FROM progetto_brewday.users WHERE nickname=? AND password=?";
		return jdbcTemplate.query(sql,args, new ResultSetExtractor<Utente>() {

			@Override
			public Utente extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if (rs.next()) {
					Utente utente = new Utente(rs.getString("nickname"),rs.getString("email"),rs.getString("password"));
					return utente;
				}
				return null;
			}
			
		});
	}

}
