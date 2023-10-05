package com.kaushik.sparepart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaushik.sparepart.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}