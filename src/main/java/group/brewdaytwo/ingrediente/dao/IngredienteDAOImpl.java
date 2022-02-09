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
	
	@Override
	public List<String> getUserIngredients(String utente)
		{	String sql = "SELECT ingrediente,quantita,tipo FROM warehouses join ingredients on ingrediente=nome where birraio = \"" + utente + "\"";
			List<String> ingredients = jdbcTemplate.query(sql, new RowMapper<String>() {
	
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String i = rs.getString("ingrediente") + " - " + rs.getDouble("quantita") + " - " + rs.getString("tipo");
					return i;
				}
			});
		
			return ingredients;
		}
	
	@Override
	public void saveUserIng(String u, String i, double q)
		{
		String sql = "INSERT INTO progetto_brewday.warehouses (birraio, ingrediente, quantita)"
				+ " VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, u, i,q);
		}
	
	@Override
	public void deleteUserIng(String u){
		String sql = "DELETE FROM progetto_brewday.warehouses WHERE birraio = ?";
		jdbcTemplate.update(sql, u);
	}
	
	
	
	
	
}
