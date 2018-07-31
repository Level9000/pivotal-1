package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {

    TimeEntry create(TimeEntry timeEntry);
    TimeEntry find(long val);
    List<TimeEntry> list();
    TimeEntry update(long val, TimeEntry timeEntry);
    void delete(long val);
}
