package com.xuandanh.sms.repository;

import com.xuandanh.sms.domain.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff,String> {
    Page<Staff>findStaffByFirstName(Pageable pageable,String staffName);
}
