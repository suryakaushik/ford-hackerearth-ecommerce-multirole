package com.kaushik.sparepart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaushik.sparepart.exception.ResourceNotFoundException;
import com.kaushik.sparepart.models.CreateOrderRequest;
import com.kaushik.sparepart.models.Orders;
import com.kaushik.sparepart.models.Spareparts;
import com.kaushik.sparepart.repository.OrdersRepository;
import com.kaushik.sparepart.repository.SparepartsRepository;

@Service
public class SparepartsService {
	@Autowired
	SparepartsRepository sprepo;

	@Autowired
	OrdersRepository orepo;
	
	public Spareparts findPartById(int partId) {
		Optional<Spareparts> part=sprepo.findById(partId);
	   	part.orElseThrow(() -> new ResourceNotFoundException("Not found: " + partId));
        return part.get();
	}

	public List<CreateOrderRequest> getOrderRequest(int orderId) {
        Optional<Orders> order=orepo.findById(orderId);
    	order.orElseThrow(() -> new ResourceNotFoundException("Not found: " + orderId));
        return order.get().getOrderRequest();
	}

}
