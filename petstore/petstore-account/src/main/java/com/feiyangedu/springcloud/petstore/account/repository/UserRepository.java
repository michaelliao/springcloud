package com.feiyangedu.springcloud.petstore.account.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.feiyangedu.springcloud.petstore.account.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, String> {

}
