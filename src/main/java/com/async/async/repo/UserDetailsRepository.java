package com.async.async.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.async.async.entity.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

}
