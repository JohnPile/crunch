package crunch.service.impl;

import crunch.model.Company;
import crunch.service.KnownCompanies;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KnownCompaniesImplTester {

    @Test
    public void testBasics() {
        KnownCompanies knownCompanies=new KnownCompaniesImpl();
        knownCompanies.put(new Company("name1", "website1"));
        knownCompanies.put(new Company("name2", "website2"));
        Company expected=new Company("name1", "website1");
        Company unknown=new Company("name0", "unknown");
        assertEquals(expected, knownCompanies.lookup(expected.getName()));
        assertEquals(knownCompanies.defaultCompany(), knownCompanies.lookup(unknown.getName()));
    }
}
