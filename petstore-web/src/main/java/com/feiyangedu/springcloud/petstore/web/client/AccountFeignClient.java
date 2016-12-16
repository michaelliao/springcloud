package com.feiyangedu.springcloud.petstore.web.client;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.feiyangedu.springcloud.petstore.common.rest.RestFeignClientConfiguration;

import io.swagger.api.client.account.AccountClient;

@FeignClient(name = AccountClient.NAME, configuration = RestFeignClientConfiguration.class)
public interface AccountFeignClient extends AccountClient {

}
