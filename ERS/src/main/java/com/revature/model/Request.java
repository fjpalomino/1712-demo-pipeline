package com.revature.model;

import java.sql.Blob;
import java.sql.Timestamp;

public class Request {

	private int id;
	private double amount;
	private String description;
	private Blob receipt;
	private Timestamp submitted;
	private Timestamp resolved;
	private int id_author;
	private int u_id_resolver;
	private int rt_type;
	private int rt_status;
	
	public Request(){};
	
	public Request(int id, double amount, String description, Blob receipt, Timestamp submitted, Timestamp resolved, int id_author,
			int u_id_resolver, int rt_type, int rt_status) {
		super();
		this.id = id;
		this.amount = amount;
		this.description = description;
		this.receipt = receipt;
		this.submitted = submitted;
		this.resolved = resolved;
		this.id_author = id_author;
		this.u_id_resolver = u_id_resolver;
		this.rt_type = rt_type;
		this.rt_status = rt_status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Blob getReceipt() {
		return receipt;
	}
	public void setReceipt(Blob receipt) {
		this.receipt = receipt;
	}
	public Timestamp getSubmitted() {
		return submitted;
	}
	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}
	public Timestamp getResolved() {
		return resolved;
	}
	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}
	public int getId_author() {
		return id_author;
	}
	public void setId_author(int id_author) {
		this.id_author = id_author;
	}
	public int getU_id_resolver() {
		return u_id_resolver;
	}
	public void setU_id_resolver(int u_id_resolver) {
		this.u_id_resolver = u_id_resolver;
	}
	public int getRtType() {
		return rt_type;
	}
	public void setRt_type(int rt_type) {
		this.rt_type = rt_type;
	}
	public int getRt_status() {
		return rt_status;
	}
	public void setRt_status(int rt_status) {
		this.rt_status = rt_status;
	}
	
	
}
