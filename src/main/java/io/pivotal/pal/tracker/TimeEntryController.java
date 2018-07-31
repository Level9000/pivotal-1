package io.pivotal.pal.tracker;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ResponseBody
@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @RequestMapping(value = "/time-entries" , method = RequestMethod.POST)
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {
        TimeEntry timeEntryResp = timeEntryRepository.create(timeEntry);
        ResponseEntity responseEntity = new ResponseEntity(timeEntryResp, new HttpHeaders(), HttpStatus.CREATED);
        return responseEntity;
    }

    @RequestMapping (value = "/time-entries/{id}" , method = RequestMethod.GET)
    public ResponseEntity<TimeEntry> read(@PathVariable("id") Long val) {
        ResponseEntity responseEntity;
        TimeEntry timeEntry = timeEntryRepository.find(val);
        if(timeEntry == null ) {
            responseEntity = new ResponseEntity(timeEntry, new HttpHeaders(), HttpStatus.NOT_FOUND);

        } else {
            responseEntity = new ResponseEntity(timeEntry, new HttpHeaders(), HttpStatus.OK);
        }

        return responseEntity;
    }

    @RequestMapping (value = "/time-entries" , method = RequestMethod.GET)
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> returnList = timeEntryRepository.list();

        ResponseEntity<List<TimeEntry>> responseEntity = new ResponseEntity(returnList, new HttpHeaders(), HttpStatus.OK);

        return responseEntity;
    }

    @RequestMapping  (value = "/time-entries/{id}" , method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") Long val, @RequestBody TimeEntry timeEntry) {
        ResponseEntity responseEntity;
        TimeEntry timeEntryResp = timeEntryRepository.update(val, timeEntry);
        if(timeEntryResp == null ) {
            responseEntity = new ResponseEntity(timeEntryResp, new HttpHeaders(), HttpStatus.NOT_FOUND);

        } else {
            responseEntity = new ResponseEntity(timeEntryResp, new HttpHeaders(), HttpStatus.OK);
        }

        return responseEntity;

    }

    @RequestMapping(value = "/time-entries/{id}" , method = RequestMethod.DELETE)
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") Long val) {
        timeEntryRepository.delete(val);
        ResponseEntity<TimeEntry> responseEntity = new ResponseEntity("", new HttpHeaders(), HttpStatus.NO_CONTENT);

        return responseEntity;
    }
}
