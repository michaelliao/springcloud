package com.feiyangedu.springcloud.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * For receive message.
 * 
 * @author liaoxuefeng
 */
public interface VoteSink {

	String VOTE_INPUT = "voteInput";
	String VOTE_LOG_INPUT = "voteLogInput";

	@Input(VOTE_INPUT)
	SubscribableChannel voteInput();

	@Input(VOTE_LOG_INPUT)
	SubscribableChannel logInput();
}
