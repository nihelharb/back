package com.SpringRestMongoDB.model;

public class Test_date extends Test{
	
	String date_last_lunch;
	String time_last_lunch;
	
	
	
	public Test_date() {
		super();
		
	}


	public Test_date(String id,String nom, String uRL, String parametre, String temps_rep, String resultat_attendu,
			String emails,String date,String time) {
		super(id,nom, uRL, parametre, temps_rep, resultat_attendu, emails);
		this.date_last_lunch=date;
		this.time_last_lunch=time;
	}

	
	public String getDate_last_lunch() {
		return date_last_lunch;
	}

	public void setDate_last_lunch(String date_last_lunch) {
		this.date_last_lunch = date_last_lunch;
	}
	public String getTime_last_lunch() {
		return time_last_lunch;
	}

	public void setTime_last_lunch(String time_last_lunch) {
		this.time_last_lunch = time_last_lunch;
	}



}
