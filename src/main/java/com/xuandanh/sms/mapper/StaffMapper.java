package com.xuandanh.sms.mapper;

import com.xuandanh.sms.domain.Staff;
import com.xuandanh.sms.dto.StaffDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Component
public class StaffMapper {
    public List<StaffDTO> staffToStaffDTO(List<Staff> staffList) {
        return staffList.stream().filter(Objects::nonNull).map(this::staffToStaffDTO).collect(Collectors.toList());
    }

    public StaffDTO staffToStaffDTO(Staff staff){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(staff,StaffDTO.class);
    }

    public List<Staff> staffDTOToStaff(List<StaffDTO> staffDTOList) {
        return staffDTOList.stream().filter(Objects::nonNull).map(this::staffDTOToStaff).collect(Collectors.toList());
    }

    public Staff staffDTOToStaff(StaffDTO staffDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(staffDTO , Staff.class);
    }
}
