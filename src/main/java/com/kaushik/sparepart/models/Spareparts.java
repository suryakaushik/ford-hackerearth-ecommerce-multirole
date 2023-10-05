package com.kaushik.sparepart.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Spareparts")		
public class Spareparts {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private String partName;
    private int availablePartCount;
    private int cost;


    public Spareparts() {
		super();
	}

    public Spareparts(String partName, int availablePartCount) {
		super();
		this.partName = partName;
		this.availablePartCount = availablePartCount;
	}

    public Spareparts(String partName, int availablePartCount, int cost) {
		super();
		this.partName = partName;
		this.availablePartCount = availablePartCount;
		this.cost=cost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public int getAvailablePartCount() {
		return availablePartCount;
	}

	public void setAvailablePartCount(int availablePartCount) {
		this.availablePartCount = availablePartCount;
	}
    
    public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}
