package com.projet.produits.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projet.produits.entities.Categorie;
import com.projet.produits.entities.Produit;
import com.projet.produits.service.CategorieService;
import com.projet.produits.service.ProduitService;

@Controller
public class ProduitController {
@Autowired
ProduitService produitService;
@Autowired
CategorieService categorieService;
@RequestMapping("/showCreate")
public String showCreate(ModelMap modelMap)
{
    List<Categorie> cats = categorieService.findAll();
    Produit prod = new Produit();
    Categorie cat = new Categorie();
    cat = cats.get(0); // prendre la première catégorie de la liste
    prod.setCategorie(cat); //affedter une catégorie par défaut au produit pour éviter le pb avec une catégorie null
    modelMap.addAttribute("produit",prod);
    modelMap.addAttribute("mode", "new");
    modelMap.addAttribute("categories", cats);
    return "formProduit";
}
@RequestMapping("/saveProduit")
public String saveProduit(@Valid Produit produit,BindingResult bindingResult)

{
if (bindingResult.hasErrors()) return "formProduit";
produitService.saveProduit(produit);
return "formProduit";
}
@RequestMapping("/ListeProduits")
public String listeProduits(ModelMap modelMap,

@RequestParam (name="page",defaultValue = "0") int page,
@RequestParam (name="size", defaultValue = "5") int size)

{
Page<Produit> prods = produitService.getAllProduitsParPage(page, size);
modelMap.addAttribute("produits", prods);

modelMap.addAttribute("pages", new int[prods.getTotalPages()]);

modelMap.addAttribute("currentPage", page);
return "listeProduits";
}

@RequestMapping("/supprimerProduit")
public String supprimerProduit(@RequestParam("id") Long id,

ModelMap modelMap,
@RequestParam (name="page",defaultValue = "0") int page,
@RequestParam (name="size", defaultValue = "2") int size)

{
produitService.deleteProduitById(id);
Page<Produit> prods = produitService.getAllProduitsParPage(page,size);

modelMap.addAttribute("produits", prods);
modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
modelMap.addAttribute("currentPage", page);
modelMap.addAttribute("size", size);
return "listeProduits";
}
@RequestMapping("/modifierProduit")
public String editerProduit(@RequestParam("id") Long id,ModelMap modelMap)
{
	List<Categorie> cats = categorieService.findAll();
    System.out.println(cats);
    Produit prod = new Produit();
    Categorie cat = new Categorie();
    cat = cats.get(0); // 
    prod.setCategorie(cat);
    modelMap.addAttribute("produit",prod);
    modelMap.addAttribute("mode", "edit");
    modelMap.addAttribute("categories", cats);
    return "formProduit";
}

@RequestMapping("/updateProduit")
public String updateProduit(@ModelAttribute("produit") Produit produit,
@RequestParam("date") String date,

ModelMap modelMap) throws ParseException
{
SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
Date dateCreation = dateformat.parse(String.valueOf(date));
produit.setDateCreation(dateCreation);
produitService.updateProduit(produit);
List<Produit> prods = produitService.getAllProduits();
modelMap.addAttribute("produits", prods);

return "listeProduits";
}

}