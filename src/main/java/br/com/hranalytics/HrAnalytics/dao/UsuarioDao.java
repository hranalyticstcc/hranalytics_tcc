package br.com.hranalytics.HrAnalytics.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.hranalytics.HrAnalytics.entity.Usuario;

public interface UsuarioDao extends CrudRepository<Usuario, Long> {

}
