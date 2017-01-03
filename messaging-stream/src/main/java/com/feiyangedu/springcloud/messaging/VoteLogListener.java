package com.feiyangedu.springcloud.messaging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * Handle message.
 * 
 * @author liaoxuefeng
 */
@Component
public class VoteLogListener {

	final Log log = LogFactory.getLog(getClass());

	@StreamListener(VoteSink.VOTE_LOG_INPUT)
	public void processVote(Vote vote) {
		log.info("LOG Listener => Vote for: " + vote.getName().name());
	}

}
