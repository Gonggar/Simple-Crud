/**
 * Spring Boot + JPA + Thymeleaf CRUD example
 */
package edu.bjtu.sei.simplecrud.service;

import edu.bjtu.sei.simplecrud.domain.Contact;
import edu.bjtu.sei.simplecrud.exception.BadResourceException;
import edu.bjtu.sei.simplecrud.exception.ResourceAlreadyExistsException;
import edu.bjtu.sei.simplecrud.exception.ResourceNotFoundException;
import edu.bjtu.sei.simplecrud.repository.ContactRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ContactService {
    
    @Autowired
    private ContactRepository contactRepository;
    
    
    private boolean existsById(Long id) {
        return contactRepository.existsById(id);
    }
    
    public Contact findById(Long id) throws ResourceNotFoundException {
        //Contact contact = (Contact) contactRepository.findById(id);
        Contact contact = contactRepository.findById(id).orElse(null);

        if (contact==null) {
            throw new ResourceNotFoundException("Cannot find Contact with id: " + id);
        }
        else return contact;
    }
    
    public List<Contact> findAll(int pageNumber, int rowPerPage) {
        List<Contact> contacts = new ArrayList<>();
        Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage, 
                Sort.by("id").ascending());
        //contactRepository.findAll(pageNumber, rowPerPage).forEach(contacts::add);
        contactRepository.findAll(sortedByIdAsc).forEach(contacts::add);

        return contacts;
    }
    
    public Contact save(Contact contact) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(contact.getName())) {
            if (contact.getId() != null && existsById(contact.getId())) { 
                throw new ResourceAlreadyExistsException("Contact with id: " + contact.getId() +
                        " already exists");
            }
            return contactRepository.save(contact);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save contact");
            exc.addErrorMessage("Contact is null or empty");
            throw exc;
        }
    }
    
    public void update(Contact contact) 
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(contact.getName())) {
            if (!existsById(contact.getId())) {
                throw new ResourceNotFoundException("Cannot find Contact with id: " + contact.getId());
            }
            contactRepository.save(contact);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save contact");
            exc.addErrorMessage("Contact is null or empty");
            throw exc;
        }
    }
    
    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) { 
            throw new ResourceNotFoundException("Cannot find contact with id: " + id);
        }
        else {
            contactRepository.deleteById(id);
        }
    }
    
    public Long count() {
        return contactRepository.count();
    }
}
