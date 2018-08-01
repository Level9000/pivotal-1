package io.pivotal.pal.tracker;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcTimeEntryRepository implements TimeEntryRepository {
    private long id = 1;

    private final JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        long entryId = id;
        timeEntry.setId(entryId);
        id++;
        long project_id = timeEntry.getProjectId();
        long user_id = timeEntry.getUserId();
        LocalDate date = timeEntry.getDate();
        int hours = timeEntry.getHours();
        String valueString = "VALUES (" + entryId + ", " + project_id + ", " +
                user_id + ", '" + date + "', " + hours + ")";

        this.jdbcTemplate.update("INSERT INTO time_entries (id, project_id, user_id, date, hours) " +
                valueString);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long val) {
        Map<String, Object> foundEntry;
        TimeEntry timeEntry = new TimeEntry();
        try {
            foundEntry = jdbcTemplate.queryForMap("SELECT id, project_id, user_id, date, hours from time_entries where id = ?", val);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }


        timeEntry.setId(Long.parseLong(foundEntry.get("id").toString()));
        timeEntry.setProjectId(Long.parseLong(foundEntry.get("project_id").toString()));
        timeEntry.setUserId(Long.parseLong(foundEntry.get("user_id").toString()));
        timeEntry.setDate(((Date)foundEntry.get("date")).toLocalDate());
        timeEntry.setHours(Integer.parseInt(foundEntry.get("hours").toString()));

        return timeEntry;
    }

    @Override
    public List<TimeEntry> list() {
        ArrayList<TimeEntry> returnList = new ArrayList<>();

        List<Map<String, Object>> queryList = jdbcTemplate.queryForList("Select * from time_entries");
        for (Map<String, Object> query : queryList) {
            TimeEntry timeEntry = new TimeEntry();
            timeEntry.setId(Long.parseLong(query.get("id").toString()));
            timeEntry.setProjectId(Long.parseLong(query.get("project_id").toString()));
            timeEntry.setUserId(Long.parseLong(query.get("user_id").toString()));
            timeEntry.setDate(((Date)query.get("date")).toLocalDate());
            timeEntry.setHours(Integer.parseInt(query.get("hours").toString()));
            returnList.add(timeEntry);
        }

        return returnList;
    }

    @Override
    public TimeEntry update(long val, TimeEntry timeEntry) {
        Map<String, Object> foundEntry = jdbcTemplate.queryForMap("Select * from time_entries where id = ?", val);
        TimeEntry timeEntryRevised = new TimeEntry();
        timeEntryRevised.setId(val);
        timeEntryRevised.setProjectId(timeEntry.getProjectId());
        timeEntryRevised.setUserId(timeEntry.getUserId());
        timeEntryRevised.setDate(timeEntry.getDate());
        timeEntryRevised.setHours(timeEntry.getHours());

        if(foundEntry != null) {
            delete(val);
        }
        long entryId    = timeEntryRevised.getId();
        long project_id = timeEntryRevised.getProjectId();
        long user_id = timeEntryRevised.getUserId();
        LocalDate date = timeEntryRevised.getDate();
        int hours = timeEntryRevised.getHours();
        String valueString = "VALUES (" + entryId + ", " + project_id + ", " +
                user_id + ", '" + date + "', " + hours + ")";

        this.jdbcTemplate.update("INSERT INTO time_entries (id, project_id, user_id, date, hours) " +
                valueString);


        return timeEntryRevised;
    }

    @Override
    public void delete(long val) {
        jdbcTemplate.update("DELETE FROM time_entries WHERE id = ?", val);

    }
}
