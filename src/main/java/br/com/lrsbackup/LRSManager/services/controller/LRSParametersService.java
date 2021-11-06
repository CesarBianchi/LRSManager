package br.com.lrsbackup.LRSManager.services.controller;

import java.util.ArrayList;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.lrsbackup.LRSManager.persistence.model.LRSParameter;
import br.com.lrsbackup.LRSManager.persistence.repository.LRSParameterRepository;
import br.com.lrsbackup.LRSManager.services.model.LRSParameterServiceModel;


@RestController
public class LRSParametersService {

	@Autowired
	private LRSParameterRepository parameterRepository;
	
	@RequestMapping("/parameters/getall")
    public LRSParameterServiceModel getAll() {
		
		List<LRSParameter> params = parameterRepository.findAll();
		
		LRSParameterServiceModel response = new LRSParameterServiceModel("/parameters/getall",params);
		
		return response;
		
    }
	
	
	@RequestMapping("/parameters/get")
    public LRSParameterServiceModel get(String name) {
		
		LRSParameter param = parameterRepository.findByname(name);
		List<LRSParameter> params = new ArrayList<>();
		params.add(param);
		
		LRSParameterServiceModel response = new LRSParameterServiceModel("/parameters/get",params);
		return response;
		
    }
	
}
