package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.pojo.Company;
import com.oocl.springbootemployee.pojo.Employee;
import com.oocl.springbootemployee.repo.CompanyRepository;
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
public class CompanyControllerTest {
    @Autowired
    private MockMvc client;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JacksonTester<List<Company>> listJson;

    @Test
    void should_get_all_companies_when_getAll_given_companies() throws Exception {
        List<Company> expectCompanyList = companyRepository.findAll();
        String companiesJsonString = client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)))
                .andReturn().getResponse().getContentAsString();

        List<Company> companyList = listJson.parseObject(companiesJsonString);
        assertThat(companyList)
                .usingRecursiveComparison()
                .isEqualTo(expectCompanyList);
    }
}
