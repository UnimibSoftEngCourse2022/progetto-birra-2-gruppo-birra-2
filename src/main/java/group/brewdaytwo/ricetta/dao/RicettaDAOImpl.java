package group.brewdaytwo.ricetta.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import group.brewdaytwo.ricetta.model.Ricetta;

public class RicettaDAOImpl implements RicettaDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public RicettaDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int save(Ricetta r) {
		String sql = "INSERT INTO progetto_brewday.recipes (nome, procedimento, descrizione, autore)"
				+ " VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, r.getNome(), r.getProcedimento(), r.getDescrizione(), r.getAutore());
		sql = "Select max(ID) as ID from recipes where autore=\"" + r.getAutore() + "\"";
		return jdbcTemplate.query(sql, new ResultSetExtractor<Integer>() {

			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if (rs.next()) {
					return rs.getInt("ID");
				}
				return 0;
			}
		});
	}

	@Override
	public void update(Ricetta r) {
		String sql = "UPDATE progetto_brewday.recipes SET nome=?, procedimento=?, descrizione=? "
				+ "WHERE ID=?";
		jdbcTemplate.update(sql, r.getNome(), r.getProcedimento(), r.getDescrizione(), r.getID());

	}

	@Override
	public void delete(int ricettaID) {
		String sql = "DELETE FROM progetto_brewday.recipes WHERE ID=?";
		jdbcTemplate.update(sql, ricettaID);
	}

	@Override
	public Ricetta get(int ricettaID) {
		String sql = "SELECT * FROM progetto_brewday.recipes WHERE ID=\"" + ricettaID + "\"";
		return jdbcTemplate.query(sql, new ResultSetExtractor<Ricetta>() {

			@Override
			public Ricetta extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if (rs.next()) {
					Ricetta ricetta = new Ricetta(rs.getInt("ID"),rs.getString("nome"),rs.getString("procedimento"), rs.getString("descrizione"), rs.getString("autore"));
					return ricetta;
				}
				return null;
			}
			
		});
	}

}
