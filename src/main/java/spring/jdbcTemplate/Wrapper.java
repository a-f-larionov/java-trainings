package spring.jdbcTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class Wrapper {

    @Autowired
    public JdbcTemplate jdbcTemplate;
}
