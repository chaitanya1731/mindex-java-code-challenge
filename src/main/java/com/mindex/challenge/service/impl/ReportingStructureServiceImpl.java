package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    /*
     * Service Method Implementation for Generating ReportingStructure for the specified employeeId
     * */
    @Override
    public ReportingStructure generateReportingStructure(String employeeId) {
        LOG.debug("Generated reporting structure for employee id [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee == null) {
            throw new RuntimeException("Employee with id: [" + employeeId + "] Not Found");
        }
        int numberOfReports = getNumberOfReports(employee);
        return new ReportingStructure(employee, numberOfReports);
    }

    /*
    * Helper Function to calculate number of reports for specific employee
    * */
    private int getNumberOfReports(Employee employee) {
        if(employee.getDirectReports() == null) return 0;

        return employee.getDirectReports().size() +
                employee.getDirectReports().stream()
                        .map(e -> employeeRepository.findByEmployeeId(e.getEmployeeId()))
                        .filter(e -> e.getDirectReports() != null)
                        .map(e -> getNumberOfReports(e))
                        .reduce(0, (sum, e) -> sum + e);
    }
}
