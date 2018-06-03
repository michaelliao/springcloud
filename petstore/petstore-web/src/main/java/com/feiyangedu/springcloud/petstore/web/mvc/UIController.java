package com.feiyangedu.springcloud.petstore.web.mvc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UIController {

	@GetMapping("/")
	public ModelAndView index() {
		return mv("index");
	}

	ModelAndView mv(String view) {
		return mv(view, null);
	}

	ModelAndView mv(String view, Map<String, Object> model) {
		if (model == null) {
			model = new HashMap<>();
		}
		model.put("__time__", System.currentTimeMillis());
		return new ModelAndView(view, model);
	}
}
