package com.oocl.springbootemployee.repo;

import com.oocl.springbootemployee.pojo.Company;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyRepository {
    public List<Company> findAll() {
        return List.of(
                new Company(1, "Company A"),
                new Company(2, "Company B"),
                new Company(3, "Company C"),
                new Company(4, "Company D")
        );
    }
}
