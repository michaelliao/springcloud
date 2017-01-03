package com.feiyangedu.springcloud.messaging;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Vote controller.
 * 
 * @author Michael Liao
 */
@Controller
public class VoteController {

	final Log log = LogFactory.getLog(getClass());

	@Autowired
	VoteHolder voteHolder;

	@Autowired
	VoteSource voteSource;

	@GetMapping("/")
	@ResponseBody
	public String index() {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><body>").append("<h1>Votes</h1>");
		for (Map.Entry<Vote.Name, AtomicLong> entry : voteHolder.getVotes().entrySet()) {
			sb.append("<p>").append(entry.getKey().name()).append(": ").append(entry.getValue().longValue())
					.append("</p>");
		}
		sb.append("<form name=\"voteform\" action=\"/vote\" method=\"post\">") // form
				.append("<fieldset><legend>Vote for</legend>") // legend
				.append("<p><select name=\"candidate\">"); // select
		for (Vote.Name key : voteHolder.getVotes().keySet()) {
			sb.append("<option>").append(key.name()).append("</option>");
		}
		sb.append("</select></p>") // end select
				.append("<p><button type=\"submit\">Vote</button></p>") // submit
				.append("</fieldset></form></body></html>");
		return sb.toString();
	}

	@PostMapping("/vote")
	public String voteFor(@RequestBody MultiValueMap<String, String> form) {
		String name = form.getFirst("candidate");
		log.info("vote for " + name);
		// send message:
		Message<Vote> msg = MessageBuilder.withPayload(new Vote(name)).build();
		voteSource.output().send(msg);
		return "redirect:/";
	}

}
