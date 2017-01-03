package com.feiyangedu.springcloud.messaging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * VoteHolder.
 * 
 * @author Michael Liao
 */
@Component
public class VoteHolder {

	final Map<Vote.Name, AtomicLong> votes = new ConcurrentHashMap<>();

	@PostConstruct
	public void initVotes() {
		for (Vote.Name name : Vote.Name.values()) {
			votes.put(name, new AtomicLong(0L));
		}
	}

	public long addVote(Vote vote) {
		return votes.get(vote.getName()).incrementAndGet();
	}

	public Map<Vote.Name, AtomicLong> getVotes() {
		return this.votes;
	}
}
