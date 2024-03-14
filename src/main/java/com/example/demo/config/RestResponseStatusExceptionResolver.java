package com.example.demo.config;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;

@Component
class RestResponseStatusExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) {
		ModelAndView model = new ModelAndView();
		model.setView(new MappingJackson2JsonView());

		if (exception instanceof ConstraintViolationException) {
			response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
			model.addObject("message", exception.getMessage());
			model.addObject("statusCode", HttpStatus.UNPROCESSABLE_ENTITY);
			model.addObject("stack", exception.getCause());
			return model;
		}
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addObject("message", exception.getMessage());
		model.addObject("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
		model.addObject("stack", exception.getStackTrace());
		return model;
	}

}