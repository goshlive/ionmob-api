package com.ionmob.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Integer id;
	
	@Getter
	@Setter
	private String username;
	
	@Getter
	@Setter
	private String password;
	
	@Getter
	@Setter
	private String email;
	
	@Getter
	@Setter
	private String roles;
	
	@Getter
	@Setter
	@Column(name = "is_active")
	private Integer isActive = 1;
	
	@Getter
	@Setter
	@Column(name = "owner_id")
	private Integer ownerId = 0;

	@Getter
	@Setter
	@Column(name = "create_dt")
	private Date createDt;

	@Getter
	@Setter
	@Column(name = "updateDt")
	private Date updateDt;

}