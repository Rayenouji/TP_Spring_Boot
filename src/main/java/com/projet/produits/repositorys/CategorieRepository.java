package com.projet.produits.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projet.produits.entities.Categorie;
@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
	

}
