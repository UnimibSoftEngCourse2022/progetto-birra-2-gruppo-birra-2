package group.brewdaytwo.services.dao.ricetta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import group.brewdaytwo.domain.model.ricetta.Ricetta;

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
	
	@Override
	public List<Ricetta> list(String nome,String autore) {
		String sql = "SELECT * FROM recipes where nome LIKE \"%"+nome+"%\" AND autore = \"" + autore + "\"";
		List<Ricetta> listRicette = jdbcTemplate.query(sql, new RowMapper<Ricetta>() {

			@Override
			public Ricetta mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ricetta ricetta = new Ricetta(rs.getInt("ID"), rs.getString("nome"), null, rs.getString("descrizione"),autore);
				return ricetta;
			}
		});
		
		return listRicette;
	}
	
	@Override
	public Ricetta getCDPO(String autore) {
		String sql = "select ricetta "
				+ "from components as c1 "
				+ "join recipes on recipes.id = c1.ricetta "
				+ "join "
				+ "(select distinct ricetta as r "
				+ "from components as c3 "
				+ "join "
				+ "(SELECT ID FROM recipes "
				+ "WHERE autore = \""+ autore +"\""
						+ " AND"
						+ " ID NOT IN"
						+ " (SELECT DISTINCT ricetta FROM components WHERE ingrediente NOT IN"
						+ " (SELECT ingrediente FROM warehouses WHERE birraio = \""+ autore +"\"))"
								+ " AND"
								+ " ID NOT IN"
								+ " (SELECT DISTINCT ricetta "
								+ " FROM recipes_equipments as RE "
								+ " join tools as t1 on t1.id = RE.strumento"
								+ " WHERE t1.nome NOT IN"
								+ " (SELECT t2.nome FROM "
								+ " (Select max(e.qnt) as num"
								+ " from "
								+ " (select sum(quantita) as qnt"
								+ " from brewers_equipments as bes"
								+ " join tools on tools.id = bes.strumento"
								+ " where tools.nome = t1.nome and birraio = \""+ autore +"\""
										+ " group by tools.nome) as e) as c,"
										+ " brewers_equipments as BE join tools as t2 on t2.id = BE.strumento "
										+ " WHERE birraio = \""+ autore +"\" AND RE.quantita <= c.num))) as recok on recok.ID = c3.ricetta"
												+ " having (select count(*) as num"
												+ " from components "
												+ " where ricetta=r) in (select max(num) as max"
												+ " from (select count(*) as num"
												+ " from components "
												+ " group by ricetta) as tmax)) as c2 on c1.ricetta = c2.r"
												+ " where autore=\""+ autore+"\""
														+ " group by ricetta"
														+ " order by sum(quantita) desc"
														+ " limit 1;";
		int idricetta = jdbcTemplate.query(sql, new ResultSetExtractor<Integer>() {

			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if (rs.next()) {
					int r = rs.getInt("ricetta");
					return r;
				}
				return 0;
			}
			
		});
		
		return get(idricetta);
		
	}

}
