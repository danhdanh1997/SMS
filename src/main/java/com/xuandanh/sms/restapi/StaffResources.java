package com.xuandanh.sms.restapi;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.xuandanh.sms.domain.Staff;
import com.xuandanh.sms.dto.StaffDTO;
import com.xuandanh.sms.dto.StoreDTO;
import com.xuandanh.sms.exception.ResponseMessage;
import com.xuandanh.sms.helper.CSVHelper;
import com.xuandanh.sms.mapper.StaffMapper;
import com.xuandanh.sms.repository.StaffRepository;
import com.xuandanh.sms.service.StaffService;
import com.xuandanh.sms.service.StoreService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StaffResources {
    private final StaffService staffService;
    private final StaffRepository staffRepository;
    private final StoreService storeService;
    private final StaffMapper staffMapper;
    private final Logger log = LoggerFactory.getLogger(StaffResources.class);

    @GetMapping("/store/{storeId}/staff/{staffId}/findOne")
    public ResponseEntity<?>getOne(@PathVariable(value = "storeId")int storeId,
                                   @PathVariable(value = "staffId")String staffId){
        Optional<StoreDTO>storeDTO = storeService.findOne(storeId);
        Optional<StaffDTO>staffDTO = staffService.findOne(staffId);
        Map<String,Boolean>response = new HashMap<>();
        if (storeDTO.isPresent() && staffDTO.isPresent()){
            log.info("information of a staff is:"+staffDTO);
            return ResponseEntity.ok(staffDTO);
        }
        response.put("staff with id:"+staffId+" not exist",Boolean.FALSE);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @GetMapping("/staff/findAll")
    public ResponseEntity<?>getAll(){
        Optional<List<StaffDTO>>staffDTO = staffService.findAll();
        Map<String,Boolean>response = new HashMap<>();
        int sizeStaff = staffDTO.map(List::size).orElse(0);
        if (sizeStaff == 0){
            log.error("List staff is Empty");
            response.put("List staff is Empty",Boolean.FALSE);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        log.info("List of staff is:"+staffDTO);
        return ResponseEntity.ok(staffDTO);
    }

    @PostMapping("/store/{storeId}/staff/create")
    public ResponseEntity<?>create(@PathVariable(value = "storeId")int storeId, @Valid @RequestBody Staff staff){
        Optional<StoreDTO>storeDTO = storeService.findOne(storeId);
        Map<String,Boolean>response = new HashMap<>();
        if (storeDTO.isEmpty()){
            log.error("store with id:"+storeId+" not exist");
            response.put("store with id:"+storeId+" not exist",Boolean.FALSE);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        log.info("create staff successfully");
        return ResponseEntity.ok(staffService.createStaff(staff,storeId));
    }

    @PutMapping("/store/{storeId}/staff/{staffId}/update")
    public ResponseEntity<?>update(@PathVariable(value = "storeId")int storeId,
                                   @PathVariable(value = "staffId")String staffId,
                                   @Valid @RequestBody Staff staff){
        Optional<StoreDTO>storeDTO = storeService.findOne(storeId);
        Optional<StaffDTO>staffDTO = staffService.findOne(staffId);
        Map<String,Boolean>response = new HashMap<>();
        if (storeDTO.isEmpty() && staffDTO.isEmpty()){
            response.put("store with id:"+storeId+" not exist"+" && "+"staff with id:"+staffId+" not exist",Boolean.FALSE);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        log.info("staff with id:"+staffId+" was updated successfully");
        return ResponseEntity.ok(staffService.updateStaff(storeId,staff));
    }

    @GetMapping("/store/{storeId}/staff/{staffId}/delete")
    public ResponseEntity<?>delete(@PathVariable(value = "storeId")int storeId,@PathVariable(value = "staffId")String staffId){
        Optional<StoreDTO>storeDTO = storeService.findOne(storeId);
        Optional<StaffDTO>staffDTO = staffService.findOne(staffId);
        Map<String,Boolean>response = new HashMap<>();
        if (storeDTO.isEmpty() && staffDTO.isEmpty()){
            response.put("store with id:"+storeId+" not exist"+" && "+"staff with id:"+staffId+" not exist",Boolean.FALSE);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        log.info("staff with id:"+staffId+" was deleted successfully");
        response.put("staff with id:"+staffId+" was deleted successfully",Boolean.TRUE);
        staffRepository.delete(staffMapper.staffDTOToStaff(staffDTO.get()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/staff/findByFirstName")
    public ResponseEntity<?>getStaffByFirstName(@RequestParam(value = "page")int page,
                                                @RequestParam(value = "size")int size,
                                                @RequestParam(required = false)String firstName){
        try {
                List<StaffDTO>staffDTOS = new ArrayList<StaffDTO>();
                Pageable pageable = PageRequest.of(page, size);
                Page<Staff>staffPage;
                if (firstName == null)
                    staffPage = staffRepository.findAll(pageable);
                else
                    staffPage = staffRepository.findStaffByFirstName(pageable,firstName);
                staffDTOS = staffMapper.staffToStaffDTO(staffPage.getContent());
                Map<String, Object> response = new HashMap<>();
                response.put("staffList", staffDTOS);
                response.put("currentPage", staffPage.getNumber());
                response.put("totalItems", staffPage.getTotalElements());
                response.put("totalPages", staffPage.getTotalPages());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/staffs/upload",consumes = "multipart/form-data")
    public ResponseEntity<com.xuandanh.sms.exception.ResponseMessage>uploadFile(@RequestParam("file") MultipartFile file){
        String message = "";
        if (CSVHelper.hasCSVFormat(file)){
            try {
                    staffService.save(file);
                    message = "Uploaded the file successfully: " + file.getOriginalFilename();
                    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/staffs/upload/")
                        .path(file.getOriginalFilename())
                        .toUriString();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,fileDownloadUri));
                }catch (Exception e){
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,""));
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,""));
    }

    @GetMapping("/export-staffs")
    public void exportCSV(HttpServletResponse response) throws Exception {
        //set file name and content type
        String filename = "staffs.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");
        //create a csv writer
        StatefulBeanToCsv<Staff> writer = new StatefulBeanToCsvBuilder<Staff>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();
        //write all users to csv file
        writer.write(staffRepository.findAll());
    }
}
