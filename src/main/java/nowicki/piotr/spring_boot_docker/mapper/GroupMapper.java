package nowicki.piotr.spring_boot_docker.mapper;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.model.Group;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupMapper {
    public Group toGroup(GroupDto dto){
        Group group = new Group();
        group.name = dto.name();
        group.setId(dto.id());
        return group;
    }

    public GroupDto toGroupDto(Group group){
        return new GroupDto(group.name, group.getId());
    }
}
