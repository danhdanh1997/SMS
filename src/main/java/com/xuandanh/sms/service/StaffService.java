package com.xuandanh.sms.service;

import com.xuandanh.sms.domain.Staff;
import com.xuandanh.sms.dto.StaffDTO;
import com.xuandanh.sms.helper.CSVHelper;
import com.xuandanh.sms.mapper.StaffMapper;
import com.xuandanh.sms.repository.StaffRepository;
import com.xuandanh.sms.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffMapper staffMapper;
    private final StaffRepository staffRepository;
    private final StoreRepository storeRepository;
    private final Logger log = LoggerFactory.getLogger(StaffService.class);

    public Optional<StaffDTO>findOne(String staffId){
        Optional<Staff>staff = staffRepository.findById(staffId);
        if (staff.isEmpty()){
            log.error("staff with id:"+staffId+" not exist");
            return Optional.empty();
        }
        return Optional.ofNullable(staffMapper.staffToStaffDTO(staff.get()));
    }

    public Optional<List<StaffDTO>>findAll(){
        return Optional.ofNullable(staffMapper.staffToStaffDTO(staffRepository.findAll()));
    }

    public Optional<StaffDTO> createStaff(Staff staff, int storeId){
        return storeRepository.findById(storeId)
                .map(store -> {
                    staff.setStoreId(store.getStoreId());
                    return staffMapper.staffToStaffDTO(staffRepository.save(staff));
                });
    }

    public Optional<StaffDTO>updateStaff(int storeId,Staff staff){
        return Optional.ofNullable(storeRepository
                .findById(storeId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(store -> {
                    Optional.ofNullable(
                            staffRepository.findById(staff.getStaffId()))
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .map(staff1 -> {
                                staff1.setStore(staff.getStore());
                                staff1.setLastUpdate(staff.getLastUpdate());
                                staff1.setActive(staff.isActive());
                                staff1.setEmail(staff.getEmail());
                                staff1.setLastUpdate(staff.getLastUpdate());
                                staff1.setFirstName(staff.getFirstName());
                                staff1.setImageUrl(staff.getImageUrl());
                                staff1.setPassword(staff.getPassword());
                                staff1.setUsername(staff.getUsername());
                                return staffMapper.staffToStaffDTO(staffRepository.save(staff1));
                            });
                    return staffMapper.staffToStaffDTO(staffRepository.save(staff));
                });
    }

    public void save(MultipartFile multipartFile){
        try {
            List<Staff>staffList = CSVHelper.csvToStaff(multipartFile.getInputStream());
            staffRepository.saveAll(staffList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream load(){
        List<Staff>staffList = staffRepository.findAll();
        return CSVHelper.staffsToCSV(staffList);
    }

}
