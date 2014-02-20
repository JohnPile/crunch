package crunch.service.impl;

import com.google.common.base.Objects;
import crunch.model.Company;
import crunch.service.KnownCompanies;

import java.util.HashMap;
import java.util.Map;

public class KnownCompaniesImpl implements KnownCompanies {

    private static final Company UNRECOGNIZED_COMPANY=new Company(null, null);
    private Map<String,Company> companyCache=new HashMap<String,Company>();

    public KnownCompaniesImpl() {
        //TODO: Wire up to some persistent storage
        //TODO: Fill with http://www.crunchbase.com/company/{companyname} or Factual.com data
    }

    @Override
    public Company lookup(String candidateName) {
        Company recognized=companyCache.get(normalizeName(candidateName));
        return Objects.firstNonNull(recognized, UNRECOGNIZED_COMPANY);
    }

    @Override
    public boolean includes(String candidateName) {
        return companyCache.containsKey(normalizeName(candidateName));
    }

    private String normalizeName(String name) {
        return name.trim().toLowerCase();
    }

    @Override
    public Company defaultCompany() {
        return UNRECOGNIZED_COMPANY;
    }

    public void put(Company company) {
        companyCache.put(normalizeName(company.getName()), company);
    }
}
