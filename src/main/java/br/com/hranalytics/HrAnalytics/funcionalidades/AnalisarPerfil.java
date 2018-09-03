package br.com.hranalytics.HrAnalytics.funcionalidades;

import java.util.ArrayList;
import java.util.List;

import br.com.hranalytics.HrAnalytics.entity.Dimensao;
import br.com.hranalytics.HrAnalytics.entity.DimensaoFilho;
import br.com.hranalytics.HrAnalytics.entity.Personalidade;
import br.com.hranalytics.HrAnalytics.twitter4j.TwitterAPI;
import br.com.hranalytics.HrAnalytics.utils.MontaJson;
import br.com.hranalytics.HrAnalytics.utils.Tradutor;
import br.com.hranalytics.HrAnalytics.watsonapi.WatsonPersonalityInsights;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.TwitterException;

public class AnalisarPerfil {

	public static Personalidade analisar(String perfil) throws TwitterException {
		List<String> tweets = new ArrayList<String>();

		TwitterAPI apiTwitter = new TwitterAPI();
		tweets = apiTwitter.pegaLinhaDoTempo(perfil);

		String json = new MontaJson().montaJsonStringBuffer(tweets);

		String perfilAnalisado = WatsonPersonalityInsights.analisarPerfil(json);
		
		return retornaPersonalidade(perfilAnalisado);
	}
	
	public static Personalidade retornaPersonalidade(String json) {
		Personalidade personalidade = new Personalidade();
		
		try {
			JSONObject jo = new JSONObject(json);
			JSONArray arrayPersonalidade = jo.getJSONArray("personality");

			
			List<Dimensao> bigFive = new ArrayList<>();

			for (int i = 0; i < arrayPersonalidade.length(); i++) {

				Dimensao dim = new Dimensao();

				JSONObject bigFiveJson = arrayPersonalidade.getJSONObject(i);
				dim.setNome(Tradutor.traduzir(bigFiveJson.getString("trait_id")));
				dim.setPorcentagem(bigFiveJson.getDouble("percentile"));

				JSONArray children = bigFiveJson.getJSONArray("children");
				List<DimensaoFilho> filhos = new ArrayList<>();

				for (int j = 0; j < children.length(); j++) {
					DimensaoFilho df = new DimensaoFilho();

					JSONObject childrenObject = children.getJSONObject(j);
					df.setNome(Tradutor.traduzir(childrenObject.getString("trait_id")));
					df.setPorcentagem(childrenObject.getDouble("percentile"));
					
					filhos.add(df);
				}
				
				dim.setFilhos(filhos);
				bigFive.add(dim);
			}
			
			personalidade.setBigFive(bigFive);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return personalidade;
	}

}
