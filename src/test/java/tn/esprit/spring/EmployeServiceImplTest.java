package tn.esprit.spring;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.IEmployeService;

import java.util.Calendar;
import java.util.Date;


@SpringBootTest
@Slf4j
public class EmployeServiceImplTest {

    @Autowired
    MissionRepository missionRepository;
    @Autowired
    DepartementRepository deptRepoistory;
    @Autowired
    TimesheetRepository timesheetRepository;
    @Autowired
    EmployeRepository employeRepository;
    @Autowired
	public IEmployeService iEmployeService; 
	@Autowired
	ContratRepository contratRepoistory;

    
	//Test Ajout de Contrat
	@Test
	public void testAjoutContrat() {
		log.info("***Test Add Contract ***");
		Contrat contract = new Contrat(new Date(Calendar.DAY_OF_MONTH),"CDD",2900);
		contratRepoistory.save(contract);
		assertTrue(contratRepoistory.findById(contract.getReference()).isPresent());
	  }
	
	//Test Affect contrat a Employe
	@Test
	public void testAffecterContratAEmploye() {
		log.info("*****Test Affecter Contrat A Employe *****");
		Employe employe = new Employe("Farah","Mokchaha","mokchaha.farah@esprit.tn",true,tn.esprit.spring.entities.Role.INGENIEUR);
		Contrat contrat= new Contrat(new Date(Calendar.DAY_OF_MONTH),"CDD",2900);
		log.info("******Employe ajoutéé*****");
		employeRepository.save(employe);
		int employeId= employe.getId();
		assertTrue(employeRepository.findById(employe.getId()).isPresent());
		log.info("*******Contrat ajouté*****");
		contratRepoistory.save(contrat);
		int reference = contrat.getReference();
		assertTrue(contratRepoistory.findById(contrat.getReference()).isPresent());
		log.info("****Contrat affecté à employé****");
		log.info(reference+"    "+employeId);
		Contrat contratManagedEntity = contratRepoistory.findById(reference).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		contratManagedEntity.setEmploye(employeManagedEntity);
		contratRepoistory.save(contratManagedEntity);
		assertEquals(contratRepoistory.findById(reference).get().getEmploye().getId(),employeId);		
	}
	
	//Test Delete Contrat by id
	@Test
	public void testdeleteContratById() {
		log.info("*****Test Delete Contrat by id *****");
		Contrat contrat= new Contrat(new Date(Calendar.DAY_OF_MONTH),"CDD",2900);
		contratRepoistory.save(contrat);
		assertTrue(contratRepoistory.findById(contrat.getReference()).isPresent());
		Contrat contratManagedEntity = contratRepoistory.findById(contrat.getReference()).get();
		contratRepoistory.delete(contratManagedEntity);
		assertFalse(contratRepoistory.findById(contrat.getReference()).isPresent());
	}










}
