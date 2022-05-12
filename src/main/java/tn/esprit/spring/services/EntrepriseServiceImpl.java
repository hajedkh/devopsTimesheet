package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
@Slf4j
public class EntrepriseServiceImpl implements IEntrepriseService {

	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	
	public int ajouterEntreprise(Entreprise entreprise) {
		try {
			log.debug("Lancement de la méthode ajouterEntreprise");
			entrepriseRepoistory.save(entreprise);
			log.info("L'ajout est terminé avec succés!");
		} catch (Exception e) {
			log.error("Erreur dans la méthode ajouterEntreprise():" + e);
		} finally {
			log.info("La méthode ajouterEntreprise est terminée sans erreur");
		}
		return entreprise.getId();
	}

	
	
	public int ajouterDepartement(Departement dep) {
		
		
		try {
			log.debug("Lancement de la méthode ajouterDepartement");
			deptRepoistory.save(dep);
			log.info("L'ajout est terminé avec succés!");
		} catch (Exception e) {
			log.error("Erreur dans la méthode ajouterDepartement():" + e);
		} finally {
			log.info("La méthode ajouterDepartement est terminée sans erreur");
		}
		return dep.getId();
		
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
	
		try {
			log.debug("Lancement de la méthode affecterDepartementAEntreprise");
			Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).orElse(null);
			if (entrepriseManagedEntity==null) {
				log.error("Entreprise does not exist");
				return;}
			Departement depManagedEntity = deptRepoistory.findById(depId).orElse(null);
			if (depManagedEntity==null) {
				log.error("Departement does not exist");
				return;}
			depManagedEntity.setEntreprise(entrepriseManagedEntity);
			deptRepoistory.save(depManagedEntity);
		} catch (Exception e) {
			log.error("Erreur dans la méthode affecterDepartementAEntreprise():" + e);
		} finally {

			log.info("La méthode affecterDepartementAEntreprise est términé avec succés");
		}
	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		List<String> depNames = new ArrayList<>();
		try {
			log.debug("Lancement de la méthode getAllDepartementsNamesByEntreprise");
			Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).orElse(null);
			if (entrepriseManagedEntity==null) {
				log.error("Entreprise does not exist");
				return null;}
			for (Departement dep : entrepriseManagedEntity.getDepartements()) {
				depNames.add(dep.getName());
			}
		} catch (Exception e) {
			log.error("Erreur dans la méthode getAllDepartementsNamesByEntreprise:" + e);
		} finally {
			log.info("La méthode getAllDepartementsNamesByEntreprise est términé avec succés");
		}
		return depNames;

	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		log.debug("Lancement de la méthode deleteEntrepriseById");
		try {
			entrepriseRepoistory.delete(entrepriseRepoistory.findById(entrepriseId).orElse(null));
		} catch (Exception e) {
			log.error("Erreur dans la méthode deleteEntrepriseById:" + e);
		} finally {
			log.info("La méthode deleteEntrepriseById est términé avec succés");
		}
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		log.debug("Lancement de la méthode deleteDepartementById");
		try {
			deptRepoistory.delete(deptRepoistory.findById(depId).orElse(null));
		} catch (Exception e) {
			log.error("Erreur dans la méthode deleteDepartementById:" + e);
		} finally {
			log.info("La méthode deleteDepartementById est términé avec succés");
		}
	}


	public Entreprise getEntrepriseById(int entrepriseId) {
		log.info("fetching Entreprise of id: "+entrepriseId);
Entreprise entreprise=entrepriseRepoistory.findById(entrepriseId).orElse(null);
if (entreprise == null) {
    log.error("No mission Found with ID : " +entrepriseId);
}
		return entreprise;	
	}
	 
	

}
