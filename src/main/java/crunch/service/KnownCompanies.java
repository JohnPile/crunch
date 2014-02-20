package crunch.service;

import crunch.model.Company;

public interface KnownCompanies {

    Company lookup(String candidateName);

    boolean includes(String candidateName);

    Company defaultCompany();

    void put(Company company);

}
