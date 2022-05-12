package tn.esprit.spring;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.IEntrepriseService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@SpringBootTest
@Slf4j
public class EntrepriseServiceImplTest {

	@Autowired
	IEntrepriseService entrepriseService;
	
	@Autowired
	EntrepriseRepository entrepriseRepo;
	
	@Autowired
	DepartementRepository departementRepo;
    private int idEntreprise;
	private int idDepartement;
	
	
	@Test
	public void testAjouterEntreprise() {
    	log.info("/************Test add Enterprise**************/");
		Entreprise entreprise = new Entreprise();
		entreprise.setName("Vermeg");
		entreprise.setRaisonSocial("raisonSocial");
		int savedEntrepriseID= entrepriseService.ajouterEntreprise(entreprise);
		assertNotEquals(0, savedEntrepriseID);
		
	}
	
	
	
	
	@Test
	public void testAjouterDepartement() {
    	log.info("/************Test add  Department**************/");
		Departement departement= new Departement();
		departement.setName("Support");
		int savedDepartementID= entrepriseService.ajouterDepartement(departement);
		assertNotEquals(0,savedDepartementID);
		
	}
	
	
	
	
	
	
	 
	 @Test
		public void getEntrepriseById() {
			Entreprise entreprise = new Entreprise("test","test2");

			entrepriseRepo.save(entreprise);
			int entreId= entreprise.getId();
			
			Entreprise findentreprise = entrepriseRepo.findById(entreId).get();
			assertEquals(entreId, findentreprise.getId());
	 }
	
	
	@Test
	public void testGetAllDepartementsNamesByEntreprise() {
    	log.info("/************Add Enterprise to Department**************/");

		Entreprise entreprise = new Entreprise();
		entreprise.setName("Vermeg");
		entreprise.setRaisonSocial("raisonSocial");
		int savedEntrepriseID= entrepriseService.ajouterEntreprise(entreprise);
		Departement departement= new Departement();
		departement.setName("Support");
		int savedDepartementID= entrepriseService.ajouterDepartement(departement);
		entrepriseService.affecterDepartementAEntreprise(savedDepartementID,savedEntrepriseID);
	
//**************GetAllDepartementsNamesByEntreprise****************//
		List<String> departmentsNames = entrepriseService.getAllDepartementsNamesByEntreprise(savedEntrepriseID);
		assertNotNull(departmentsNames);
    	log.info("/************delete  Enterprise & Department**************/");
		entrepriseService.deleteDepartementById(savedDepartementID);
		entrepriseService.deleteEntrepriseById(savedEntrepriseID);	
}
	@Test
	public void deleteEntrepriseById() {
		log.info("*****Test Delete Entreprise by id *****");
		
		
		Entreprise entreprise = new Entreprise("test1","test2");
		entrepriseRepo.save(entreprise);
		assertTrue(entrepriseRepo.findById(entreprise.getId()).isPresent());
		Entreprise entrepriseManagedEntity = entrepriseRepo.findById(entreprise.getId()).get();
		entrepriseRepo.delete(entrepriseManagedEntity);
		assertFalse(entrepriseRepo.findById(entreprise.getId()).isPresent());
	}
	
	
	
	
	}
	
	

