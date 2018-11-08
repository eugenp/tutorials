package com.baeldung.hexagonalarchitecture.service;

import com.baeldung.hexagonalarchitecture.domain.Agenda;
import com.baeldung.hexagonalarchitecture.port.AgendaService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaServiceImpl implements AgendaService {

    private final JdbcTemplate jdbcTemplate;

    public AgendaServiceImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Agenda agenda) {
        this.jdbcTemplate.update("Insert into AGENDA(NAME, PHONE_NUMBER) values (?,?)", agenda.getName(), agenda.getNumber());
    }

    public List<Agenda> list() {
        return this.jdbcTemplate.query("Select * from AGENDA", (resultSet, i) -> {
            return new Agenda(resultSet.getString("NAME"), resultSet.getInt("PHONE_NUMBER"));
        });
    }
}
