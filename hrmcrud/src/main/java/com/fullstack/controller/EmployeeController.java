package com.fullstack.controller;

import com.fullstack.exception.RecordNotFoundException;
import com.fullstack.model.Employee;
import com.fullstack.service.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @PostMapping("/save")
    public ResponseEntity<Employee> save(@RequestBody Employee employee) {

        log.info("@@@@@@@@@Trying to save data for Employee: "+ employee.getEmpName());
        return ResponseEntity.ok(employeeService.save(employee));
    }

    @GetMapping("/findbyid/{empId}")
    public ResponseEntity<Optional<Employee>> findBytId(@PathVariable int empId) {
        return ResponseEntity.ok(employeeService.findById(empId));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }


    @PutMapping("/update/{empId}")
    public ResponseEntity<Employee> update(@PathVariable int empId, @RequestBody Employee employee)  {

        Employee employee1 = employeeService.findById(empId).orElseThrow(() -> new RecordNotFoundException("Employee #ID Does Not Exist"));

        employee1.setEmpName(employee.getEmpName());
        employee1.setEmpAddress(employee.getEmpAddress());
        employee1.setEmpSalary(employee.getEmpSalary());
        employee1.setEmpContactNumber(employee.getEmpContactNumber());
        employee1.setEmpDOB(employee.getEmpDOB());

        return ResponseEntity.ok(employeeService.update(employee1));



    }

    @DeleteMapping("/deletebyid/{empId}")
    public ResponseEntity<String> deleteById(@PathVariable int empId) {
        employeeService.deleteById(empId);
        return ResponseEntity.ok("Data Deleted Successfully");
    }

    @GetMapping("/sortBySalary")
    public ResponseEntity<List<Employee>> sortbysalary()
    {
        return ResponseEntity.ok(employeeService.findAll().stream()
                .sorted(Comparator.comparing(Employee::getEmpSalary).reversed()).toList());

    }


}
