package tn.esprit.spring;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;

import java.util.Collections;
import java.util.Date;


@SpringBootTest
class TimesheetServiceImplTest {

    @Autowired
    MissionRepository missionRepository;
    @Autowired
    DepartementRepository deptRepoistory;
    @Autowired
    TimesheetRepository timesheetRepository;
    @Autowired
    EmployeRepository employeRepository;


    @Test
    void testAjouterMission() {
        Mission mission = new Mission("mission", "12345");
        missionRepository.save(mission);
        assertTrue(missionRepository.findById(mission.getId()).isPresent());
    }

    @Test
    void testAffecterMissionADepartement() {
        Mission mission = new Mission("mission", "12345");
        Departement dep = new Departement("deaprtment");
        mission.setDepartement(dep);
        deptRepoistory.save(dep);
        missionRepository.save(mission);
        assert missionRepository.findById(mission.getId()).isPresent();
        assertEquals(missionRepository.findById(mission.getId()).get().getDepartement().getId(), dep.getId());
    }

    @Test
    void testAjouterTimesheet() {
        TimesheetPK timesheetPK = new TimesheetPK();
        Employe emp = new Employe("emp", "ben emp", "emp@emp.com", false, Role.CHEF_DEPARTEMENT);
        employeRepository.save(emp);
        Mission mission = new Mission("mission", "12345");
        missionRepository.save(mission);
        Date date = new Date();
        timesheetPK.setDateDebut(date);
        timesheetPK.setDateFin(date);
        timesheetPK.setIdEmploye(emp.getId());
        timesheetPK.setIdMission(mission.getId());

        Timesheet timesheet = new Timesheet();
        timesheet.setTimesheetPK(timesheetPK);
        timesheet.setValide(false); //par defaut non valide
        timesheetRepository.save(timesheet);
        assertFalse(timesheetRepository.getTimesheetsByMissionAndDate(emp, mission, date, date).isEmpty());

    }

    @Test
    void testValiderTimeSheet() {
        Departement depart = new Departement();
        deptRepoistory.save(depart);
        Employe validateur = new Employe("emp1", "ben emp1", "emp1@emp.com", false, Role.CHEF_DEPARTEMENT);
        validateur.setDepartements(Collections.singletonList(depart));
        employeRepository.save(validateur);

        Mission mission = new Mission("mission", "12345");
        mission.setDepartement(depart);
        missionRepository.save(mission);

        assertEquals(Role.CHEF_DEPARTEMENT, validateur.getRole());

        //verifier s'il est le chef de departement de la mission en question
        assertEquals(depart.getId(), mission.getDepartement().getId());

    }


}
