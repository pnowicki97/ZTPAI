package nowicki.piotr.spring_boot_docker.mapper;

import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.model.Group;
import org.springframework.stereotype.Service;

@Service
public class GroupMapper {

    public Group toGroup(GroupDto dto){
        var group = new Group();
        group.name = dto.name();
        return group;
    }

    public GroupDto toGroupDto(Group group){
        return new GroupDto(group.name);
    }
}
