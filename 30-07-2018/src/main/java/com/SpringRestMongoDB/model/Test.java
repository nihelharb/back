package com.SpringRestMongoDB.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tests")
public class Test {
	@Id
	private String id;
	private String nom;
	private String URL;
	private String parametre;
	private String  temps_rep;
	private String resultat_attendu;
	private String emails;
	
	
	
	public Test() {
	
	}
	public Test(String nom, String uRL, String parametre, String temps_rep, String resultat_attendu,
			String emails) {
		
		
		this.nom = nom;
		URL = uRL;
		this.parametre = parametre;
		this.temps_rep = temps_rep;
		this.resultat_attendu = resultat_attendu;
		this.emails = emails;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getParametre() {
		return parametre;
	}
	public void setParametre(String parametre) {
		this.parametre = parametre;
	}
	public String getTemps_rep() {
		return temps_rep;
	}
	public void setTemps_rep(String temps_rep) {
		this.temps_rep = temps_rep;
	}
	public String getResultat_attendu() {
		return resultat_attendu;
	}
	public void setResultat_attendu(String resultat_attendu) {
		this.resultat_attendu = resultat_attendu;
	}
	public String getEmails() {
		return emails;
	}
	public void setEmails(String emails) {
		this.emails = emails;
	}
	
	
	
	
	
	
}
