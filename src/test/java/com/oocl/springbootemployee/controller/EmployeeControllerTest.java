package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.pojo.Employee;
import com.oocl.springbootemployee.pojo.Gender;
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

import java.math.BigDecimal;
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
    void should_get_all_employees_when_call_getAll_given_employees() throws Exception {
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
    void should_get_right_employee_when_call_getById_given_employee_id() throws Exception {
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

    @Test
    void should_get_right_employees_when_call_getByGender_given_gender() throws Exception {
        //Given
        List<Employee> expectList = employeeRepository.getByGender(Gender.MALE);
        //When
        //Then
        String employeesJSONString = client.perform(MockMvcRequestBuilders.get("/employee")
                .param("gender", "MALE" ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(expectList.size())))
                .andReturn().getResponse().getContentAsString();

        List<Employee> employeeList = listJson.parseObject(employeesJSONString);
        assertThat(employeeList)
                .usingRecursiveComparison()
                .isEqualTo(expectList);
    }

    @Test
    void should_create_employee_when_call_create_given_employee() throws Exception {
        //Given
        Employee employee = new Employee(4, 18, "Amy", Gender.FEMALE, new BigDecimal(8000));
        //When
        //Then
        String employeeJson = client.perform(MockMvcRequestBuilders.post("/employee")
                .contentType("application/json")
                .content(json.write(employee).getJson()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Employee returnedEmployee = json.parseObject(employeeJson);
        assertThat(returnedEmployee)
                .usingRecursiveComparison()
                .isEqualTo(employee);
    }


}