package ru.k0r0tk0ff.spring.db;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class JmsRepository {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JmsRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void writeMessageToDb(TextMessage textMessage) {
        String sql = "INSERT into msg (message) values (?)";
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(
            connection -> {PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);
            try {
                ps.setString(1, textMessage.getText());
            } catch (Exception e) {
                LOGGER.error("Error (insert message body) in JmsRepository class!", e);
            }
            return ps;
        }, holder);

        try {
            insertHeader(holder.getKey().intValue(), textMessage.getJMSType());
        } catch (JMSException e) {
            LOGGER.error("Error insertHeader() in JmsRepository class!", e);
        }
    }

    private void insertHeader(int recordId, String value) {
        String sql = "INSERT into headers (headers_id, head) values (?,?)";
        jdbcTemplate.update(sql, recordId, value);
    }

    public void showDataInDb(){
        System.out.println("------------- Show result in database");
        String sql = "SELECT msg.message, headers.head " +
                " FROM msg" +
                " JOIN headers" +
                " ON msg.msg_id = headers.headers_id;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        while(rowSet.next()) {
            System.out.println(rowSet.getString(1) + "  " + rowSet.getString(2));
        }
    }
}
