package com.SpringRestMongoDB.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.soap.SOAPMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.SpringRestMongoDB.model.Historique;
import com.SpringRestMongoDB.model.Mail;
import com.SpringRestMongoDB.model.Test;
import com.SpringRestMongoDB.repo.HistoriqueRepository;
import com.SpringRestMongoDB.service.EmailService;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")

public class HistoriqueController {
	@Autowired
	HistoriqueRepository repository;

	
	
	@GetMapping("/historique")
	public List<Historique> getAllHistorique() {
		
		List<Historique> tests = new ArrayList<>();
		List<Historique> tests_non_nuls = new ArrayList<>();
		
		repository.findAll().forEach(tests::add);
	/*	for(Historique h:tests)
		{	
		    System.out.print(h.getNom()+"  "+h.getTemps_rep_reel());

    if(h.getResultat()!=null && h.getDate()!=null &&  String.valueOf(h.getTemps_rep_reel())!= null ) 
    tests_non_nuls.add(h);
    	
		
		}
    	
		return tests_non_nuls;
	
		*/
		return tests ;
	}
 

	@PostMapping("/historique/create")
	public Historique postTests(@RequestBody Historique test) {
		Historique h=new Historique(test.getNom(), test.getURL(),test.getParametre(),test.getTemps_rep(),test.getResultat_attendu(),test.getEmails(),test.getTemps_rep_reel(),test.getDate(),test.getTime(),test.getResultat());
		Historique _test = repository.save(h);
		
		return _test;
	
	}
	
	
	
	@GetMapping("/historique/{nom}")
	public List<Historique> findByNom(@PathVariable String nom) {
 
		List<Historique> hists = repository.findByNom(nom);
		return hists;
	}
	@GetMapping("/historique/{url}")
	public List<Historique> findByURL(@PathVariable String url) {
 
		List<Historique> hists = repository.findByURL(url);
		return hists;
	}
	
	

	
	
	@GetMapping("/historique/{nom}/{dateD}/{dateF}")
	public List<String> getHistorique(@PathVariable String nom,@PathVariable String dateD,@PathVariable String dateF) throws ParseException {
		int nbechec =0;
		int nbretard =0;
		int temps =0 ;
		List<Historique> tests = new ArrayList<>();
		List<Historique> tt = new ArrayList<>();
		List<String> res = new ArrayList<>();

		repository.findByNom(nom).forEach(tests::add);
		for(Historique h:tests)
		{		try { 
		    System.out.print(h.getNom()+"  "+h.getTemps_rep_reel() + "  " +h.getDate());
             String date = h.getDate(); 
             SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  	       java.util.Date datee = new java.util.Date() ;
  	        datee = formatter.parse(date);
  	        
		    
	   String date_string = dateD ;
	       java.util.Date dd = new java.util.Date() ;
	        dd = formatter.parse(date_string);
	        
	        String date_string2 = dateF ;
	       java.util.Date df = new java.util.Date() ;
	        df = formatter.parse(date_string2);
		    
		   int resD = datee.compareTo(dd);
		   int resF = datee.compareTo(df);
     if (resD > 0 && resF <0 || resD ==0 || resF==0)

  		    
     {    tt.add(h);
       temps+=Integer.parseInt(h.getTemps_rep_reel());
     if ( h.getResultat().equals("echec"))
    	 nbechec ++ ;

     else 
     
    	 if (Integer.parseInt(h.getTemps_rep_reel())>Integer.parseInt(h.getTemps_rep()))
    		 nbretard++;
     
     }
		}catch(ParseException ex) {
			System.out.println(ex.getMessage());
		}
	
       
		
		}
		res.add(""+tt.get(0).getNom());
		res.add(""+tt.size());
		res.add(""+nbechec);
		res.add(""+nbretard);
		res.add(""+temps/tt.size());
	
		System.out.println(res.get(0));
		
		
		return res;
	}
 

	
	@GetMapping("/historique/echec/{dateD}")
	public List<Historique> getEchec(@PathVariable String dateD) throws ParseException {
		
		List<Historique> tests = new ArrayList<>();
		List<Historique> tt = new ArrayList<>();
		repository.findAll().forEach(tests::add);

		for(Historique h:tests)
		{		
			try { 
		    System.out.print(h.getNom()+"  "+h.getTemps_rep_reel() + "  " +h.getDate());
		    
             String date = h.getDate(); 
             SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  	       java.util.Date datee = new java.util.Date() ;
  	        datee = formatter.parse(date);
             
		    
	      String date_string = dateD ;
	        java.util.Date dd = new java.util.Date() ;
	        dd = formatter.parse(date_string);
	        int resD = datee.compareTo(dd);
	        
	        if (resD > 0 && h.getResultat().equals("echec")|| resD ==0  && h.getResultat().equals("echec"))
	        tt.add(h);
		
			}catch(ParseException ex) {
			System.out.println(ex.getMessage());
		}
	
		}
		
	return tt ;
	}
	
	
	@Autowired
    private EmailService emailService;

	
   

public  Historique SendMail( Historique h,String resultat ,String date,String heure) throws Exception {
	 
	
	//Optional<Historique> h = repository.findById(id);


    Logger log = LoggerFactory.getLogger(MailController.class);

    

   
        log.info("Spring Mail - Sending Simple Email with JavaMailSender Example");

        Mail mail = new Mail();
        mail.setFrom("harb.nihel55@gmail.com");
        
        //mail.setTo(h.get().getEmails());
        String [] res =h.getEmails().split("\\s");
        for(int i=0; i<res.length;i++)
  
        {
        	System.out.println(res[i]);
            mail.setTo(res[i]);
            
            if(resultat.equals("echec"))
            {   	 mail.setSubject(" échec d'éxécution  du test");
           mail.setContent("Echec de test "+h.getNom()+" éffectué à "+date+"à l'heure "+heure);}
            else
            { 	
        mail.setSubject("Retard d'éxécution du test");
        mail.setContent("Retard d'éxecution :temps de réponse réél est "+h.getTemps_rep_reel()+" et le temps de réponse attendu = est:"+h.getResultat_attendu());}
           emailService.sendSimpleMessage(mail);
        }
       

       
		return null; 

    }
   
}
