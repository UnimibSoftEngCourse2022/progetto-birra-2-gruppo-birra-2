package group.brewdaytwo.attrezzo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import group.brewdaytwo.attrezzo.model.Attrezzo;

public class AttrezzoDAOImpl implements AttrezzoDAO {
	
private JdbcTemplate jdbcTemplate;
	
	public AttrezzoDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Attrezzo> list() {
		String sql = "SELECT * FROM tools";
		List<Attrezzo> listAttrezzo = jdbcTemplate.query(sql, new RowMapper<Attrezzo>() {

			@Override
			public Attrezzo mapRow(ResultSet rs, int rowNum) throws SQLException {
				Attrezzo attrezzo = new Attrezzo(rs.getInt("ID"), rs.getString("nome"),rs.getDouble("capacita_max"));
				return attrezzo;
			}
		});
		
		return listAttrezzo;
	}

	@Override
	public void saveRecEquipment(String r, String id, int q) {
		String sql = "INSERT INTO progetto_brewday.recipes_equipments (ricetta, strumento, quantita)"
				+ " VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, r, id,q);

	}
	
	@Override
	public void deleteRecTool(String r) {
		String sql = "DELETE FROM progetto_brewday.recipes_equipments WHERE ricetta = ?";
		jdbcTemplate.update(sql, r);
	}

}
