package group.brewdaytwo.services.dao.ricetta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
		String[] args = {ricettaID+""};
		String sql = "SELECT * FROM progetto_brewday.recipes WHERE ID=?";
		return jdbcTemplate.query(sql,args, new ResultSetExtractor<Ricetta>() {

			@Override
			public Ricetta extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if (rs.next()) {
					return new Ricetta(rs.getInt("ID"),rs.getString("nome"),rs.getString("procedimento"), rs.getString("descrizione"), rs.getString("autore"));
				}
				return null;
			}
		});
	}
	
	@Override
	public List<Ricetta> list(String nome,String autore) {
		String[] args = {nome,autore};
		String sql = "SELECT * FROM recipes where nome LIKE \"%\"?\"%\" AND autore = ?";
		return jdbcTemplate.query(sql,args, new RowMapper<Ricetta>() {

			@Override
			public Ricetta mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Ricetta(rs.getInt("ID"), rs.getString("nome"), null, rs.getString("descrizione"),autore);
			}
		});
	}
	
	@Override
	public Ricetta getCDPO(String autore) {
		String[] args = {autore,autore,autore,autore};
		String sql = "select distinct r1.r"
				+ " from"
				+ " (select distinct ricetta as r"
				+ " from components"
				+ " group by r"
				+ " order by count(ingrediente) desc, sum(quantita) desc) as r1"
				+ " join"
				+ " (SELECT ID FROM recipes"
				+ " WHERE autore = ?"
				+ " AND"
				+ " ID NOT IN"
				+ " (SELECT DISTINCT ricetta FROM components WHERE ingrediente NOT IN"
				+ " (SELECT ingrediente FROM warehouses WHERE birraio = ?))"
				+ " AND"
				+ " ID NOT IN"
				+ " (SELECT DISTINCT ricetta"
				+ " FROM recipes_equipments as RE"
				+ " join tools as t1 on t1.id = RE.strumento"
				+ " WHERE t1.nome NOT IN"
				+ " (SELECT t2.nome FROM"
				+ " (Select max(e.qnt) as num"
				+ " from"
				+ " (select sum(quantita) as qnt"
				+ " from brewers_equipments as bes"
				+ " join tools on tools.id = bes.strumento"
				+ " where tools.nome = t1.nome and birraio = ?"
				+ " group by tools.nome) as e) as c,"
				+ " brewers_equipments as BE join tools as t2 on t2.id = BE.strumento"
				+ " WHERE birraio = ? AND RE.quantita <= c.num))) as r2"
				+ " on r1.r=r2.ID"
				+ " limit 1;";
		int idricetta = jdbcTemplate.query(sql,args, new ResultSetExtractor<Integer>() {

			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if (rs.next()) {
					return rs.getInt("r");
				}
				return 0;
			}
			
		});
		
		return get(idricetta);
		
	}
	
	@Override
	public String getQuantita(String autore, int ricettaID) 
	{
		String[] args = {ricettaID+"",autore};
		String sql ="select min(t1.v1/t2.v2) as min"
				+ " from"
				+ " (select ingrediente,quantita as v2 "
				+ " from components "
				+ " where ricetta =? and ingrediente <> \"Acqua\") as t2"
				+ " join"
				+ " (select ingrediente,quantita as v1"
				+ " from warehouses"
				+ " where birraio =? and ingrediente <> \"Acqua\") as t1"
				+ " on t1.ingrediente = t2.ingrediente;";
		
		double qtnNoAcqua = jdbcTemplate.query(sql,args, new ResultSetExtractor<Double>() {

			@Override
			public Double extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if (rs.next()) {
					double r = rs.getDouble("min");
					if(r == 0.0)
						r = 100.0;
					return r;
				}
				return 100.0;
			}
		});
		
		sql = "select min(t1.v1/(t2.v2/100)) as min"
				+ " from "
				+ " (select ingrediente,quantita as v2 "
				+ " from components "
				+ " where ricetta =? and ingrediente = \"Acqua\") as t2"
				+ " join"
				+ " (select ingrediente,quantita as v1 "
				+ " from warehouses "
				+ " where birraio =? and ingrediente = \"Acqua\") as t1"
				+ " on t1.ingrediente = t2.ingrediente;";
		
		double qtnAcqua = jdbcTemplate.query(sql,args, new ResultSetExtractor<Double>() {

			@Override
			public Double extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if (rs.next()) {
					double r = rs.getDouble("min");
					if(r == 0.0)
						r = 100.0;
					return r;
				}
				return 100.0;
			}
		});
		
		double qtn = Math.min(qtnNoAcqua, qtnAcqua);
		
		sql ="select t2.nome as nome, t2.capacita_max as capMax, t2.quantita as qtn"
				+ " from"
				+ " (select * from tools "
				+ " join recipes_equipments as RE on tools.id = RE.strumento where ricetta = ?) as t1"
				+ " join "
				+ " (select * from tools "
				+ " join brewers_equipments as BE on tools.id = BE.strumento where birraio = ?) as t2"
				+ " on t1.nome = t2.nome"
				+ " order by nome desc,capMax desc;";
		
		List<String> userTools = jdbcTemplate.query(sql,args, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("nome") + " - " + rs.getDouble("capMax") + " - " + rs.getString("qtn");
			}
		});
		
		String[] arg = {ricettaID+""};
		sql ="select nome, quantita from tools"
				+ " join recipes_equipments as RE on tools.id = RE.strumento where ricetta = ?"
				+ " order by nome desc;";
		
		List<String> recTools = jdbcTemplate.query(sql,arg, new RowMapper<String>() {
			
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("nome") + " - " + rs.getString("quantita");
			}
		});
		
		double mincap= 50.0;
		
		for(int i=0; i<recTools.size();i++)
		{
			boolean flag = true;
			String nomeRt= recTools.get(i).split(" - ")[0];
			int qtnAttRt = Integer.parseInt(recTools.get(i).split(" - ")[1]);
			for(int j=0; j<userTools.size() && flag; j++)
			{
				String nomeUt = userTools.get(j).split(" - ")[0];
				double capMax = Double.parseDouble(userTools.get(j).split(" - ")[1]);
				int qtnattUt = Integer.parseInt(userTools.get(j).split(" - ")[2]);
				if(nomeRt.equals(nomeUt))
					qtnAttRt -= qtnattUt;
				if(qtnAttRt <= 0)
					{
					flag = false;
					mincap = Math.min(capMax, mincap);
					}	
			}
		}
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		
		return df.format(Math.min(mincap, qtn));
	}

}
