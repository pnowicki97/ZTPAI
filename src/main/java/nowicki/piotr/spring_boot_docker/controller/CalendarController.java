package nowicki.piotr.spring_boot_docker.controller;

import nowicki.piotr.spring_boot_docker.model.Event;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class CalendarController {


    @GetMapping("/events")
    public List<Map<String, Object>> getEvents() {
        List<Map<String, Object>> events = new ArrayList<>();

        // Example events
        Map<String, Object> event1 = new HashMap<>();
        event1.put("title", "Meeting");
        event1.put("start", LocalDateTime.now().toString());
        event1.put("end", LocalDateTime.now().plusHours(1).toString());
        event1.put("color", "blue");
        events.add(event1);

        Map<String, Object> event2 = new HashMap<>();
        event2.put("title", "Birthday");
        event2.put("start", LocalDateTime.now().plusDays(3).toString().substring(0, 10)); // Date only
        events.add(event2);

        return events;
    }

}
