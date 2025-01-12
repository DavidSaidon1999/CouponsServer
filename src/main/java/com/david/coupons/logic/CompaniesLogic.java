package com.david.coupons.logic;

import com.david.coupons.dal.ICompaniesDal;
import com.david.coupons.dto.Company;
import com.david.coupons.entities.CompanyEntity;
import com.david.coupons.enums.ErrorTypes;
import com.david.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompaniesLogic {
    private ICompaniesDal companiesDal;


    @Autowired
    public CompaniesLogic(ICompaniesDal companiesDal) {
        this.companiesDal = companiesDal;

    }

    public void createCompanies(Company company, String userType) throws ServerException {

        if (companiesDal.isCompanyNameDontUnique(company.getName())) {
            throw new ServerException(ErrorTypes.INVALID_COMPANY_NAME, company.toString());
        }
        validateCompanies(company, userType);
        CompanyEntity companyEntity = convertCompanyToCompanyEntity(company);
        companiesDal.save(companyEntity);
    }

    private CompanyEntity convertCompanyToCompanyEntity(Company company) {
        CompanyEntity companyEntity = new CompanyEntity(company.getId(),
                company.getName(), company.getAddress(), company.getPhone());
        return companyEntity;
    }

    public void updateCompany(Company company, String userType) throws ServerException {
        validateCompanies(company, userType);

        CompanyEntity companyEntityToUpdate = companiesDal.findById(company.getId()).get();

        //you must a unique name
        if (!companyEntityToUpdate.getName().equals(company.getName())) {
            if (companiesDal.isCompanyNameDontUnique(company.getName())) {
                throw new ServerException(ErrorTypes.INVALID_COMPANY_NAME, company.toString());
            }
        }
        CompanyEntity companyEntity = convertCompanyToCompanyEntity(company);
        companiesDal.save(companyEntity);
    }

    public Page<Company> getCompanies(int page, int size) throws ServerException {
        Pageable pageable = PageRequest.of(page , size);

        return this.companiesDal.allCompany(pageable);
    }



    private Company convertCompanyEntityToCompany(CompanyEntity companyEntity) {
        Company company = new Company(companyEntity.getId(),
                companyEntity.getName(), companyEntity.getAddress(), companyEntity.getPhone());
        return company;
    }

    public Company getCompany(int companyId) throws ServerException {
        CompanyEntity companyEntity = companiesDal.findById(companyId).get();
        Company company = convertCompanyEntityToCompany(companyEntity);
        return company;
    }

    public void deleteCompany(int id, String userType) throws ServerException {

        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorTypes.ADMIN_ONLY_ACCESS);
        }
        companiesDal.deleteById(id);
    }

    private void validateCompanies(Company company, String userType) throws ServerException {

        if (company.getName() == null && company.getName().length() > 45) {
            throw new ServerException(ErrorTypes.INVALID_NAME, company.toString());
        }

        if (company.getPhone() != null && (company.getPhone().length() < 9 || company.getPhone().length() > 15)) {
            throw new ServerException(ErrorTypes.INVALID_PHONE, company.toString());
        }

        if (company.getAddress() != null && company.getAddress().length() > 45) {
            throw new ServerException(ErrorTypes.INVALID_ADDRESS, company.toString());
        }
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorTypes.ADMIN_ONLY_ACCESS);
        }
    }

    //    private List<Company> ConvertListCompanyEntityToListCompany(List<CompanyEntity> companyEntityList) {
//        List<Company> companyList = new ArrayList<>();
//
//        for (CompanyEntity companyEntity : companyEntityList) {
//            Company company = convertCompanyEntityToCompany(companyEntity);
//            companyList.add(company);
//        }
//        return companyList;
//    }
}
