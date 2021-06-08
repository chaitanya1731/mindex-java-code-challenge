package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;

    /*
     * Service Method Implementation for Creating Compensation for the specified employee
     * */
    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation for employee id [{}]", compensation.getEmployee().getEmployeeId());

        compensationRepository.save(compensation);
        return compensation;
    }

    /*
     * Service Method Implementation for Reading Compensation for the specified employeeId
     * */
    @Override
    public Optional<Compensation> read(String employeeId) {
        LOG.debug("Reading compensation for employee id [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if(employee == null) {
            throw new RuntimeException("Employee with id: [" + employeeId + "] Not Found");
        }

        Optional<Compensation> compensation = compensationRepository.findById(employee);
        if (compensation == null) {
            throw new RuntimeException("Compensation not found for employee id: [" + employeeId + "]");
        }
        return compensation;
    }
}
