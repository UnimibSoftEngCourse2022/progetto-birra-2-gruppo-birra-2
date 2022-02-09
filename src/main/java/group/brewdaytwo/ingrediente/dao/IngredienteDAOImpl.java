package group.brewdaytwo.ingrediente.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import group.brewdaytwo.ingrediente.model.Ingrediente;

public class IngredienteDAOImpl implements IngredienteDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public IngredienteDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Ingrediente> list(String tipo) {
		String sql = "SELECT * FROM ingredients where tipo=\""+tipo+"\"";
		List<Ingrediente> listIngrediente = jdbcTemplate.query(sql, new RowMapper<Ingrediente>() {

			@Override
			public Ingrediente mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ingrediente ingrediente = new Ingrediente(rs.getString("nome"),rs.getString("tipo"));
				return ingrediente;
			}
		});
		
		return listIngrediente;
	}
	
	@Override
	public void saveComponent(String r, String i, double q){
		String sql = "INSERT INTO progetto_brewday.components (ricetta, ingrediente, quantita)"
					+ " VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, r, i,q);
	}
	@Override
	public void deleteComponent(String r) {
		String sql = "DELETE FROM progetto_brewday.components WHERE ricetta = ?";
		jdbcTemplate.update(sql, r);
	}
}
