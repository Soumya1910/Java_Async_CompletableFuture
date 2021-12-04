package com.async.async.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_details_csv")
public class UserDetails {

	@Id
	@GeneratedValue
	private long id;
	private String first_name;
	private String email;
	private String gender;
}
