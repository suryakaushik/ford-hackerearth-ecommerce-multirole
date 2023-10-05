package com.kaushik.sparepart.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.JoinColumn;

@Entity
@Table(name = "Orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private AccountEntity account;
    
    public AccountEntity getAccount() {
		return account;
	}

	public void setAccount(AccountEntity account) {
		this.account = account;
	}

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
//    private List<CreateOrderRequest> orderRequest = new ArrayList<>();
	 @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	 @JoinColumn(name = "request_id")
     private List<CreateOrderRequest> orderRequest = new ArrayList<>();


	public Orders() {
    }
    
    public List<CreateOrderRequest> getOrderRequest() {
		return orderRequest;
	}

	public void setOrderRequest(List<CreateOrderRequest> orderRequest) {
		this.orderRequest = orderRequest;
	}

	public void addOrderRequest(CreateOrderRequest orderRequest) {
		this.orderRequest.add(orderRequest);
//		orderRequest.setOrder(this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
