package com.kaushik.sparepart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaushik.sparepart.models.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
}
