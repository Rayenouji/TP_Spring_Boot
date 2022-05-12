package com.projet.produits.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.produits.entities.Categorie;
import com.projet.produits.repositorys.CategorieRepository;

@Service
public class CategorieServiceImpl implements CategorieService{
	@Autowired
	private CategorieRepository categorieRepository;

	@Override
	public List <Categorie> findAll() {
        return categorieRepository.findAll();
    }

	
	
}
