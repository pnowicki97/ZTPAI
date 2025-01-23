package nowicki.piotr.spring_boot_docker.service;

import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.mapper.GroupMapper;
import nowicki.piotr.spring_boot_docker.model.User;
import nowicki.piotr.spring_boot_docker.repository.GroupRepository;
import nowicki.piotr.spring_boot_docker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final UserRepository userRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper,
                        UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.userRepository = userRepository;
    }

    public GroupDto saveGroup(GroupDto dto){
        var group = groupMapper.toGroup(dto);
        groupRepository.save(group);
        return dto;
    }
    public GroupDto saveGroup(GroupDto dto, List<String> userIds){
        var group = groupMapper.toGroup(dto);
        Set<User> users = new HashSet<>(userRepository.findAllById(userIds));
        group.setUsers(users);
        for (User user : users) {
            user.getGroups().add(group);
        }
        groupRepository.save(group);
        return dto;
    }

    public List<GroupDto> findAllByUserId(String userId){
        return groupRepository.findByUsers_Id(userId).stream().map(groupMapper::toGroupDto).collect(Collectors.toList());
    }

    public List<GroupDto> findAllGroups(){
        return groupRepository.findAll().stream().map(groupMapper::toGroupDto).collect(Collectors.toList());
    }

    public GroupDto findById(@PathVariable("group-id") String id){
        return groupRepository.findById(id).map(groupMapper::toGroupDto).orElse(null);
    }

    public void deleteById(@PathVariable("group-id") String id){
        groupRepository.deleteById(id);
    }
}
