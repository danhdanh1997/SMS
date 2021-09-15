package com.xuandanh.sms.repository;

import com.xuandanh.sms.domain.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff,String> {
    @Query("select st from Staff st where st.firstName like %:firstName% ")
    Page<Staff>findStaffByFirstName(Pageable pageable,String firstName);
}
