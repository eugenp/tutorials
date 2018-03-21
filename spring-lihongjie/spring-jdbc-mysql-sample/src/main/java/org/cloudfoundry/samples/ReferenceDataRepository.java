package org.cloudfoundry.samples;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ReferenceDataRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	@Inject
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public String getDbInfo() {
		DataSource dataSource = jdbcTemplate.getDataSource();
		if (dataSource instanceof BasicDataSource) {
			return ((BasicDataSource) dataSource).getUrl();
		}
		else if (dataSource instanceof SimpleDriverDataSource) {
			return ((SimpleDriverDataSource) dataSource).getUrl();
		}
		return dataSource.toString();
	}
	
	public List<State> findAll() {
		return this.jdbcTemplate.query("select * from current_states", new RowMapper<State>() {
				public State mapRow(ResultSet rs, int rowNum) throws SQLException {
					State s = new State();
					s.setId(rs.getLong("id"));
					s.setStateCode(rs.getString("state_code"));
					s.setName(rs.getString("name"));
					return s;
				}
			});
	}

}
