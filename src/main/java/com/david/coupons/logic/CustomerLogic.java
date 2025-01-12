package com.david.coupons.logic;
import com.david.coupons.dal.ICustomersDal;
import com.david.coupons.dto.Customer;
import com.david.coupons.dto.CustomerDetails;
import com.david.coupons.entities.CustomerEntity;
import com.david.coupons.entities.UserEntity;
import com.david.coupons.enums.ErrorTypes;
import com.david.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.MessagingException;

@Service
public class CustomerLogic {
    private UserLogic userLogic;
    private ICustomersDal customersDal;

    @Autowired
    public CustomerLogic(UserLogic userLogic, ICustomersDal customersDal) {
        this.userLogic = userLogic;
        this.customersDal = customersDal;
    }

    @Transactional
    public void createCustomer(Customer customer) throws ServerException, MessagingException {
        validateCustomer(customer);
        customer.getUser().setUserType("Customer");
        customer.getUser().setCompanyId(null);
        UserEntity userEntity = this.userLogic.createUser(customer.getUser());
        CustomerEntity customerEntity = convertCustomerToCustomerEntity(customer);
        customerEntity.setUser(userEntity);
        this.customersDal.save(customerEntity);
    }

    private CustomerEntity convertCustomerToCustomerEntity(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity(customer.getId(),
                customer.getName(),
                customer.getAddress(), customer.getAmountOfKids(),
                customer.getPhone());
        return customerEntity;
    }

    public void updateCustomer(Customer customer) throws ServerException {

        validateCustomer(customer);
        CustomerEntity customerEntity = convertCustomerToCustomerEntity(customer);
        this.customersDal.save(customerEntity);
    }

    public void deleteCustomer(int customerId) throws ServerException {
        this.userLogic.deleteMyUser(customerId);
    }

    public void deleteCustomerByAdmin(int customerId, String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorTypes.ADMIN_ONLY_ACCESS);
        }
        this.userLogic.deleteMyUser(customerId);
    }

    private void validateCustomer(Customer customer) throws ServerException {

        if(this.customersDal.IsPhoneExist(customer.getPhone())){
            throw new ServerException(ErrorTypes.PHONE_ALREADY_EXISTS);
        }

        if (!customer.getUser().getUserType().equals("Customer")) {
            throw new ServerException(ErrorTypes.INVALID_CUSTOMER);
        }

        if (customer.getName() == null && customer.getName().length() > 45) {
            throw new ServerException(ErrorTypes.INVALID_NAME, customer.toString());
        }

        if (customer.getPhone() != null && (customer.getPhone().length() < 9 || customer.getPhone().length() > 45)) {
            throw new ServerException(ErrorTypes.INVALID_PHONE, customer.toString());
        }

        if (customer.getAddress() != null && customer.getAddress().length() > 45) {
            throw new ServerException(ErrorTypes.INVALID_ADDRESS, customer.toString());
        }
    }

    public Page<CustomerDetails> getCustomers(String userType, int page, int size) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorTypes.PERMISSION_DENIED);
        }
        Pageable pageable = PageRequest.of(page , size);

        return this.customersDal.getCustomersDetails(pageable);
    }

    public CustomerDetails getCustomer(int id) throws ServerException {
        return this.customersDal.getCustomerDetailsById(id);
    }

    public CustomerDetails getCustomerByAdmin(int id, String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorTypes.PERMISSION_DENIED);
        }
            return this.customersDal.getCustomerDetailsById(id);
    }
}
