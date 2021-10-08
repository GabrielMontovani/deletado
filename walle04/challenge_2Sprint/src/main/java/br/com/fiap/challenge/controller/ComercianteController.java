package br.com.fiap.challenge.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.challenge.model.Comerciante;
import br.com.fiap.challenge.repository.ComercianteRepository;

@Controller
public class ComercianteController {
	
	@Autowired
	private ComercianteRepository repository;
	
	@Autowired
	private MessageSource message;
	
	@GetMapping("/comerciante")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("comerciantes");
		List<Comerciante> comerciantes = repository.findAll();
		modelAndView.addObject("comerciantes", comerciantes);
		return modelAndView;
	}
	
	@PostMapping("/comerciante")
	public String save(@Valid Comerciante comerciante, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) return "comerciante-form";
		repository.save(comerciante);
		redirect.addFlashAttribute("message", message.getMessage("comerciante.new.success", null, LocaleContextHolder.getLocale()));
		return "redirect:comerciante";
	}
	
	@RequestMapping("/comerciante/new")
	public String create(Comerciante comerciante) {
		return "comerciante-form";
	}
	
}
