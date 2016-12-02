package com.feiyangedu.springcloud.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.feiyangedu.springcloud.data.domain.User;

@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
