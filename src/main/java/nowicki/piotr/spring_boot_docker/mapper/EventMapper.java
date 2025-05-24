package nowicki.piotr.spring_boot_docker.mapper;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.DutyDto;
import nowicki.piotr.spring_boot_docker.dto.EventDto;
import nowicki.piotr.spring_boot_docker.model.Duty;
import nowicki.piotr.spring_boot_docker.model.Event;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventMapper {
    public Event toEvent(EventDto dto){
        Event event = new Event();
        event.title = dto.title();
        event.setPhoto_url(dto.photoUrl());
        event.setStart(dto.start());
        event.setEnd(dto.end());
        return event;
    }
    public EventDto toEventDto(Event event){
        return new EventDto(event.getTitle(), event.getPhoto_url(), event.getStart(), event.getEnd());
    }
}
