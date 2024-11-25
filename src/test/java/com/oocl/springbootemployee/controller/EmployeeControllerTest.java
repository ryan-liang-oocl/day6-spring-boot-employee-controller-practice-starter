package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.pojo.Employee;
import com.oocl.springbootemployee.repo.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;


@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc client;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JacksonTester<List<Employee>> listJson;

    @Autowired
    private JacksonTester<Employee> json;


    @Test
    void should_get_all_employees_when_call_getAllEmployees_given_employees() throws Exception {
        List<Employee> expectEmployeeList = employeeRepository.getAll();
        String employeesJSONString = client.perform(MockMvcRequestBuilders.get("/employee/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andReturn().getResponse().getContentAsString();

        List<Employee> employeeList = listJson.parseObject(employeesJSONString);
        assertThat(employeeList)
                .usingRecursiveComparison()
                .isEqualTo(expectEmployeeList);
    }

    @Test
    void should_get_right_employee_when_call_getEmployee_given_employee_id() throws Exception {
        //Given
        final List<Employee> givenEmployees = employeeRepository.getAll();
        final Integer employeeId = givenEmployees.get(0).getId();
        //When
        //Then
        String employeeJson = client.perform(MockMvcRequestBuilders.get("/employee/" + employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Employee returnedEmployee = json.parseObject(employeeJson);
        assertThat(returnedEmployee.getId()).isEqualTo(employeeId);
    }


}