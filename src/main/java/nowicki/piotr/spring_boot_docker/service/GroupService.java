package nowicki.piotr.spring_boot_docker.service;

import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.mapper.GroupMapper;
import nowicki.piotr.spring_boot_docker.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    public GroupDto saveGroup(GroupDto dto){
        var group = groupMapper.toGroup(dto);
        groupRepository.save(group);
        return dto;
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
