package com.SpringRestMongoDB.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "historique")
//@TypeAlias("historique")
public class Historique extends Test { 
	private String  temps_rep_reel;
 //   @DateTimeFormat(pattern = "yyyy/MM/dd")
	private String date;
	private String resultat;
	
		
	

	public Historique() {
		super();
		
	}

	public Historique(String nom, String uRL, String parametre, String temps_rep, String resultat_attendu,
			String emails) {
		super(nom, uRL, parametre, temps_rep, resultat_attendu, emails);
	
	}

	public Historique(String nom, String URL, String parametre, String temps_rep, String resultat_attendu,
			String emails,String temps_rep_reel,String date,String resultat) {
		super(nom, URL, parametre, temps_rep, resultat_attendu, emails);
		this.temps_rep_reel=temps_rep_reel;
		this.date=date;
		this.resultat=resultat;
		
	}

	public String getTemps_rep_reel() {
		return temps_rep_reel;
	}
	public void setTemps_rep_reel(String temps_rep_reel) {
		this.temps_rep_reel = temps_rep_reel;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getResultat() {
		return resultat;
	}
	public void setResultat(String resultat) {
		this.resultat = resultat;
	}
	
	
	
}
