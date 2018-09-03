package br.com.hranalytics.HrAnalytics.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.hranalytics.HrAnalytics.entity.Personalidade;
import br.com.hranalytics.HrAnalytics.funcionalidades.AnalisarPerfil;
import twitter4j.TwitterException;

@RestController
@RequestMapping("/personalidade")
public class PersonalidadeRest {
	
	@RequestMapping(value="/analisa/{perfil}", method = RequestMethod.GET)
	@ResponseBody()
	public Personalidade analisaPerfil(@PathVariable("perfil") String perfil) {
		Personalidade personalidade = new Personalidade();
		
		try {
			personalidade = AnalisarPerfil.analisar(perfil);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		return personalidade;
	}

}
