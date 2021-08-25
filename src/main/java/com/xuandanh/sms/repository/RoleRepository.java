package com.xuandanh.sms.repository;

import com.xuandanh.sms.domain.ERole;
import com.xuandanh.sms.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    @Override
    Optional<Role> findById(Integer integer);
    Optional<Role> findByName(ERole roleName);
}
