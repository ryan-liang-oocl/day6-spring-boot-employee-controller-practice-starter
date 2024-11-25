package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.pojo.Employee;
import com.oocl.springbootemployee.repo.EmployeeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeRepository employeeRepository = new EmployeeRepository();

    @GetMapping("all")
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAll();
    }

    @GetMapping("{id}")
    public Employee getEmployee(@PathVariable int id) {
        return employeeRepository.get(id);
    }
}
