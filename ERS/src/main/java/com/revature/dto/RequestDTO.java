package com.revature.dto;

import java.sql.Blob;
import java.sql.Timestamp;

public class RequestDTO {

	private int id;
	private double amount;
	private String description;
	private Blob receipt;
	private Timestamp submitted;
	private Timestamp resolved;
	private String id_author;
	private String u_id_resolver;
	private String rt_type;
	private String rt_status;
	
	public RequestDTO(){};
	
	public RequestDTO(int id, double amount, String description, Blob receipt, Timestamp submitted, Timestamp resolved, String id_author,
			String u_id_resolver, String rt_type, String rt_status) {
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
	public String getId_author() {
		return id_author;
	}
	public void setId_author(String id_author) {
		this.id_author = id_author;
	}
	public String getU_id_resolver() {
		return u_id_resolver;
	}
	public void setU_id_resolver(String u_id_resolver) {
		this.u_id_resolver = u_id_resolver;
	}
	public String getRtType() {
		return rt_type;
	}
	public void setRt_type(String rt_type) {
		this.rt_type = rt_type;
	}
	public String getRt_status() {
		return rt_status;
	}
	public void setRt_status(String rt_status) {
		this.rt_status = rt_status;
	}
	
	
}
