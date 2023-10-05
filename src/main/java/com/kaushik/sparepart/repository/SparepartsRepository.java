package com.kaushik.sparepart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaushik.sparepart.models.Spareparts;

public interface SparepartsRepository extends JpaRepository<Spareparts, Integer> {
}
