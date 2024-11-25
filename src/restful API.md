- GET obtain company list with response of id, name:  
  GET /companies
- GET obtain a certain specific company with response of id, name:  
  GET /companies/{id}
- GET obtain list of all employees under a certain specific company:  
  GET /companies/{id}/employees
- GET page query, page equals 1, size equals 5, it will return the data in company list from index 0 to index 4:  
  GET /companies?page=1&size=5
- PUT update an employee with company:  
  PUT /companies/{companyId}/employees/{employeeId}
- POST add a company:  
  POST /companies
- DELETE delete a company:
  DELETE /companies/{id}