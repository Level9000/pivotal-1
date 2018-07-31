package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

   // @Autowired
   // private TimeEntryRepository timeEntryRepository;

    ArrayList<TimeEntry> timeEntries = new ArrayList<>();

    long id = 1;

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(id);
        id++;
        timeEntries.add(timeEntry);
        return timeEntry;
    }

    public TimeEntry find(long val) {
        for(TimeEntry value : timeEntries) {
            if(value.getId() == val) {
                return value;
            }
        }
        return null;
    }

    public TimeEntry update(long val, TimeEntry timeEntry) {
        timeEntry.setId(val);

        for(TimeEntry timeEntry1 : timeEntries) {
            if(timeEntry1.getId() == val) {
                timeEntries.remove(timeEntry1);
                timeEntries.add(timeEntry);
                return timeEntry;
            }
        }
        return null;
    }

    public void delete(long val) {
        ArrayList<TimeEntry> updatedList = new ArrayList<>();
        for(TimeEntry timeEntry1 : timeEntries) {
            if(timeEntry1.getId() == val) {
            } else {
                updatedList.add(timeEntry1);
            }
        }
        timeEntries = updatedList;
    }

    public List<TimeEntry> list() {
        return timeEntries;
    }

}
