package com.kaushik.sparepart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaushik.sparepart.models.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findByEmail(String email);
}
