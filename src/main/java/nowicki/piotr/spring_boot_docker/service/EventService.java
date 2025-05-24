package nowicki.piotr.spring_boot_docker.service;

import nowicki.piotr.spring_boot_docker.dto.EventDto;
import nowicki.piotr.spring_boot_docker.mapper.EventMapper;
import nowicki.piotr.spring_boot_docker.model.Event;
import nowicki.piotr.spring_boot_docker.model.Group;
import nowicki.piotr.spring_boot_docker.model.User;
import nowicki.piotr.spring_boot_docker.repository.EventRepository;
import nowicki.piotr.spring_boot_docker.repository.GroupRepository;
import nowicki.piotr.spring_boot_docker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final EventMapper eventMapper;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository, GroupRepository groupRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.eventMapper = eventMapper;
    }

    public EventDto saveEvent(EventDto dto){
        Event event = eventMapper.toEvent(dto);
        eventRepository.save(event);
        return dto;
    }
    public EventDto saveEvent(EventDto dto, String userId, String groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        Event event = eventMapper.toEvent(dto);
        event.setUser(user);
        event.setGroup(group);
        eventRepository.save(event);

        return dto;
    }

    public EventDto saveEvent(EventDto dto, String userId, String groupId, String photoUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        Event event = eventMapper.toEvent(dto);
        event.setUser(user);
        event.setGroup(group);
        event.setPhoto_url(photoUrl);
        eventRepository.save(event);

        return dto;
    }

    public List<EventDto> findAllEvents(){
        return eventRepository.findAll().stream().map(eventMapper::toEventDto).collect(Collectors.toList());
    }
    public List<EventDto> findAllByUserId(String userId){
        return eventRepository.findByUser_Id(userId).stream().map(eventMapper::toEventDto).collect(Collectors.toList());
    }
    public List<EventDto> findAllByGroupId(String groupId){
        return eventRepository.findByGroup_Id(groupId).stream().map(eventMapper::toEventDto).collect(Collectors.toList());
    }
    public EventDto findById(String id){
        return eventRepository.findById(id).map(eventMapper::toEventDto).orElse(null);
    }
    public void deleteById(String id){
        eventRepository.deleteById(id);
    }
}
