package com.feiyangedu.springcloud.messaging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * Handle message.
 * 
 * @author liaoxuefeng
 */
@Component
public class VoteListener {

	final Log log = LogFactory.getLog(getClass());

	@Autowired
	VoteHolder voteHolder;

	@StreamListener(VoteSink.VOTE_INPUT)
	public void processVote(Vote vote) {
		long n = voteHolder.addVote(vote);
		log.info(n + " votes for candidate: " + vote.getName().name());
	}

}
