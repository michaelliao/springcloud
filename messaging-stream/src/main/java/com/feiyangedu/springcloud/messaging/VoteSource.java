package com.feiyangedu.springcloud.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * For send message.
 * 
 * @author liaoxuefeng
 */
public interface VoteSource {

	String VOTE_OUTPUT = "voteOutput";

	@Output(VOTE_OUTPUT)
	MessageChannel output();

}
