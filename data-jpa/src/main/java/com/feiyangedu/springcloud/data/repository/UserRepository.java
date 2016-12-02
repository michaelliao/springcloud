package com.feiyangedu.springcloud.data.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.feiyangedu.springcloud.data.domain.User;

@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
