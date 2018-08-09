package com.SpringRestMongoDB.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringRestMongoDB.WS.Soap;
import com.SpringRestMongoDB.model.Historique;
import com.SpringRestMongoDB.model.Mail;
import com.SpringRestMongoDB.model.Test;
import com.SpringRestMongoDB.model.Test_date;
import com.SpringRestMongoDB.repo.TestRepository;
import com.SpringRestMongoDB.service.EmailService;
import com.SpringRestMongoDB.service.UserService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class TestController {
	
	//@Value("${ENDPOINT_ADDRESS_PROPERTY}")
	private String URL="http://localhost:27917/Conversion_Monnaie/Converion_Monnaie?WSDL";
	
	
	//public static final String ENDPOINT_ADDRESS_PROPERTY="http://localhost:8080/Conversion_Monnaie/Converion_Monnaie?WSDL";
	public static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	HistoriqueController controller_hist;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	TestRepository repository;
	
	@PostMapping("/test_date_lunch")
	public List<String> get_last_lunch(@RequestBody Test test) throws ParseException {
	
		System.out.println("******"+test.getURL());
		List<Historique> list=controller_hist.findByNom(test.getNom());
		List<Historique> list2=new ArrayList();
		
	//	System.out.println(list.size());
		List<String> f=new ArrayList();
	if(list.size()!=0)	
	{
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 //  java.util.Date date_max = new java.util.Date() ;
	      //  dd = formatter.parse(date_string);
		   
		
		   
    	Date  date_max= formatter.parse(list.get(0).getDate());
 	
	 // System.out.println(formatter.format(date_max));
	  
	  
		for(Historique h:list) {
			
			 Date 	date_cour=formatter.parse(h.getDate());
			 if(date_cour.after(date_max))
				 date_max=date_cour;
				 
		}
	
		
		for(Historique h:list) {
			
			 if( formatter.parse(h.getDate()).equals(date_max))
				{
				 list2.add(h);
				
				}
				 
		}
		 System.out.println(list2.get(0).getTime());
		 
	//	 Date date1 = dtf2.parse(time1);
		 Date time_max =format.parse(list2.get(0).getTime());
		 
		
		
		for(Historique h:list2) {
			 Date time_cour = format.parse(h.getTime());
			if(time_cour.getTime()>time_max.getTime())
				time_max=time_cour;
			
				 
		}
		//System.out.println(dtf2.format(time_max));
		
		f.add(formatter.format(date_max));
		f.add(format.format(time_max));
		
	}
	else
	{
		f.add("");
		f.add("");
		
	}
	return f;
	}
	

	
	
	@GetMapping("/test_date")
	public List<Test_date> getAllTests_date() throws ParseException {
	
		List<Test> tests = new ArrayList<>();
		List<Test_date> tests2 = new ArrayList<>();
		
		repository.findAll().forEach(tests::add);
		
		//System.out.println(get_last_lunch(tests.get(0)));
		
		for(Test t:tests) {
			//System.out.println(get_last_lunch(t));
			
	tests2.add(new Test_date(t.getId(),t.getNom(),t.getURL(),t.getURL(), t.getParametre(),t.getResultat_attendu(),
		t.getEmails(),get_last_lunch(t).get(0),get_last_lunch(t).get(1)));

		}
		
		
		return tests2;
	}
 

	
	@GetMapping("/test")
	public List<Test> getAllTests() {
	
		List<Test> tests = new ArrayList<>();
		repository.findAll().forEach(tests::add);
		
		
		
		return tests;
	}
 

	@PostMapping("/test/create")
	public Test postTests(@RequestBody Test test) {
 
		Test _test = repository.save(new Test(test.getNom(), test.getURL(),test.getParametre(),test.getTemps_rep(),test.getResultat_attendu(),test.getEmails()));
		return _test;
	
	}
	
	@DeleteMapping("/test/delete/{id}")
	public ResponseEntity<String> deleteTestid(@PathVariable("id") String id) {
	
		repository.deleteById(id);
 
		return new ResponseEntity<>("test has been deleted!", HttpStatus.OK);
	}
 
	/*
	@DeleteMapping("/test/delete")
	public ResponseEntity<String> deleteAllTests() {
 
		repository.deleteAll();
 
		return new ResponseEntity<>("All test have been deleted!", HttpStatus.OK);
	}
 */
	@GetMapping("/test/url/{url}")
	public List<Test> findByURL(@PathVariable String url) {
 
		List<Test> tests = repository.findAll();
		return tests;
	}
	
	
	@GetMapping("/test/{id}")
	public Test findById(@PathVariable String id) {
 
		Optional<Test> tests = repository.findById(id);
		return tests.get();
	}
/*	@PutMapping("/test/{id}")
	
	public void update(@RequestBody Test test) {
        repository.save(test);
    }*/
	
	@PutMapping("/test/{id}")
	public ResponseEntity<Test> updatetest(@PathVariable("id") String id, @RequestBody Test test) {
		System.out.println("Update Test with ID = " + id + "...");
 
		Optional<Test> testData = repository.findById(id);
 
		if (testData.isPresent()) {
			
			Test _test = testData.get();
			_test.setNom(test.getNom());
			_test.setURL(test.getURL());
			_test.setParametre(test.getParametre());
			_test.setTemps_rep(test.getTemps_rep());
			_test.setEmails(test.getEmails());
			return new ResponseEntity<>(repository.save(_test), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
	@Autowired
    private EmailService emailService;

	
 

public  Test SendMail( String id) throws Exception {
	 
	
	Optional<Test> h = repository.findById(id);


    Logger log = LoggerFactory.getLogger(MailController.class);

    

   
        log.info("Spring Mail - Sending Simple Email with JavaMailSender Example");

        Mail mail = new Mail();
        mail.setFrom("maissa1922@gmail.com");
        
        mail.setTo(h.get().getEmails());
        String [] res = h.get().getEmails().split("\\s");
        for(int i=0; i<res.length;i++)
  
        {
        	System.out.println(res[i]);
            mail.setTo(res[i]);
            mail.setSubject("Sending Simple Email with JavaMailSender Example");
           mail.setContent("cbn ! :: Email d echec"+ h.get().getNom()+"est échoué "); 
           emailService.sendSimpleMessage(mail);
        }
       

       
		return null; 

    }
	
	////////////////lancer web service
   // @DateTimeFormat(pattern = "yyyy-MM-dd")
    
	@PostMapping("/test/lunch")
	public HashMap<String, Object> lunch_ws(@RequestBody Test test) throws Exception {

		
        Soap soap=new Soap() ;
	
		String resultat="";
		SOAPMessage message = null;
		
		 message =  soap.createSOAPMessageFromFile(this.getClass().getResource("SoapTest.xml"));
		 logger.debug( this.getClass().getResource("SoapTest.xml").toString());
		
		 SOAPMessage message_response= soap.submitRequestMessageToSoapWebService(message,test.getURL());
		 
		 long time_spent=soap.spentTime;
		 
		 
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.now();
			 DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");
			ZonedDateTime zdt = ZonedDateTime.now();
			
	  
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
		 try {
				message_response.writeTo(out);
			} catch (SOAPException e) {
				
				logger.error(e.toString());
			} catch (IOException e) {
				
				logger.error(e.toString());
			}
		     String response_string = new String(out.toByteArray());
		     
       if(response_string.equals(test.getResultat_attendu()))
	        resultat="succees";
       else 
	        resultat="echec";
    
       Historique h=new Historique(test.getNom(), test.getURL(), test.getParametre(),test.getParametre(), test.getResultat_attendu(),
   			test.getEmails(), Long.toString(time_spent),dtf.format(localDate),dtf2.format(zdt),resultat);
       
       
       controller_hist.postTests(h);
       
       HashMap<String, Object> rtn = new LinkedHashMap<String, Object>();
       rtn.put("resultat", resultat);
       rtn.put("date", dtf.format(localDate));
       rtn.put("time", dtf2.format(zdt));
   if(resultat.equals("echec"))
       controller_hist.SendMail(h,resultat,dtf.format(localDate),dtf2.format(zdt));
   
   else if(Integer.parseInt(h.getTemps_rep_reel())>Integer.parseInt(test.getTemps_rep()))
	   controller_hist.SendMail(h,resultat,dtf.format(localDate),dtf2.format(zdt));
	   
    

       return rtn;
     
      // return JSONObject.quote(resultat);   
	}
	
	
}
