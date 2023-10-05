package com.kaushik.sparepart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaushik.sparepart.models.CreateOrderRequest;
import com.kaushik.sparepart.models.Orders;

public interface CreateOrderRequestRepository extends JpaRepository<CreateOrderRequest, Integer> {
}
