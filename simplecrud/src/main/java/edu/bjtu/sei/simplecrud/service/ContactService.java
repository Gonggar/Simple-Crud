/**
 * Spring Boot + Thymeleaf Example 
 */
package edu.bjtu.sei.simplecrud.service;

import edu.bjtu.sei.simplecrud.domain.Contact;
import edu.bjtu.sei.simplecrud.exception.BadResourceException;
import edu.bjtu.sei.simplecrud.exception.ResourceAlreadyExistsException;
import edu.bjtu.sei.simplecrud.exception.ResourceNotFoundException;
import edu.bjtu.sei.simplecrud.mapper.ContactMapper;
//import edu.bjtu.sei.simplecrud.repository.ContactRepository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ContactService {
    
    @Autowired
    //private ContactRepository contactRepository;
    private ContactMapper contactRepository;
    
    private boolean existsById(Long id) {
    	Contact contact = (Contact) contactRepository.find(id);
        if (contact==null) {
            return false;
        }
        else return true;    
    }
    
    public Contact findById(Long id) throws ResourceNotFoundException {
        Contact contact = (Contact) contactRepository.find(id);
        if (contact==null) {
            throw new ResourceNotFoundException("Cannot find Contact with id: " + id);
        }
        else return contact;
    }
    
    public List<Contact> findAll(int pageNumber, int rowPerPage) {
        List<Contact> contacts = new ArrayList<>();
//        Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage, 
//                Sort.by("id").ascending());
        contactRepository.findAll().forEach(contacts::add);
		int size = contacts.size();
		int fromidx = (pageNumber-1)*rowPerPage;
		if (fromidx>size) fromidx = 0;
		int toidx = fromidx + rowPerPage;
		if (toidx > size) toidx = size;
		
		return contacts.subList(fromidx, toidx);

//        return contacts;
    }
    
    @SuppressWarnings("deprecation")
	public void save(Contact contact) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(contact.getName())) {
            if (contact.getId() != null && existsById(contact.getId())) { 
                throw new ResourceAlreadyExistsException("Contact with id: " + contact.getId() +
                        " already exists");
            }
            contactRepository.save(contact);
            //return contact;
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save contact");
            exc.addErrorMessage("Contact is null or empty");
            throw exc;
        }
    }
    
    @SuppressWarnings("deprecation")
	public void update(Contact contact) 
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(contact.getName())) {
            if (!existsById(contact.getId())) {
                throw new ResourceNotFoundException("Cannot find Contact with id: " + contact.getId());
            }
            contactRepository.update(contact);
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
            contactRepository.delete(id);
        }
    }
    
    public int count() {
    	
        return contactRepository.findAll().size();
    }
}
