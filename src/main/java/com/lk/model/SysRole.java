package com.lk.model;


import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class SysRole extends AbstractPersistable<Long> implements Serializable{

	@Column
	private String roleName;
	
	@ManyToMany(cascade = CascadeType.REFRESH , fetch = FetchType.LAZY)
	@JoinTable(name = "user_role",
		joinColumns = {@JoinColumn(name="role_id" , referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name="user_id" , referencedColumnName = "id") })
	@JsonIgnore
	List<SysUser> users;
	
}
