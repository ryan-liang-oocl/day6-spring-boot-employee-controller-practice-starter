package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.pojo.Employee;
import com.oocl.springbootemployee.pojo.Gender;
import com.oocl.springbootemployee.repo.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> getAll() {
        return employeeRepository.getAll();
    }

    @GetMapping("{id}")
    public Employee getById(@PathVariable int id) {
        return employeeRepository.get(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getByGender(@RequestParam Gender gender) {
        return employeeRepository.getByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) {
        return employeeRepository.create(employee);
    }

    @PutMapping("{id}")
    public Employee update(@PathVariable int id, @RequestBody Employee employee) {
        return employeeRepository.update(id, employee);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {
        employeeRepository.delete(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getByPage(@RequestParam int page, @RequestParam int pageSize) {
        return employeeRepository.getByPage(page, pageSize);
    }

}
