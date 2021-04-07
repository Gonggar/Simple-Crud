package edu.bjtu.sei.simplecrud.controller.restcontroller;

import edu.bjtu.sei.simplecrud.domain.Contact;
import edu.bjtu.sei.simplecrud.exception.ResourceNotFoundException;
import edu.bjtu.sei.simplecrud.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
//@CrossOrigin(maxAge = 3600)
//@RequestMapping("/api")
@Tag(name = "contact", description = "The Contact API")

public class RestContactController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Value("${msg.rows_per_page}")
    private String ROW_PER_PAGE;
    
    @Autowired
    private ContactService contactService;

    @Value("${msg.title}")
    private String title;

    
    @GetMapping(value = "/api/contacts")
    public List<Contact> getContacts(
            @RequestParam(value = "page", defaultValue = "1") int pageNumber, HttpServletRequest request) {
    	
        @SuppressWarnings("unchecked")
        
		List<String> logs = (List<String>) request.getSession().getAttribute("LOGS_SESSION");
        //check if notes is present in session or not
        if (logs == null) {
            logs = new ArrayList<>();
            // if notes object is not present in session, set notes in the request session
            request.getSession().setAttribute("LOGS_SESSION", logs);
        }

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        int rows = Integer.parseInt(ROW_PER_PAGE);

        //String log = request.getUserPrincipal().getName() + "/" + timeStamp + "/" + "list contacts";
        String log = timeStamp + "/" + "list contacts";
        logs.add(log);
        request.getSession().setAttribute("LOGS_SESSION", logs);

        List<Contact> contacts = contactService.findAll(pageNumber, rows);
        return contacts;
    }

    @Operation(summary = "Find A Contact by ID", description = "Query a single contact by its id", tags = { "contact" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Query success",
                content = @Content(schema = @Schema(implementation = Contact.class))),
        @ApiResponse(responseCode = "4xx", description = "Contact not found") })

    @GetMapping(value = "/api/contacts/{contactId}")
    public ResponseEntity<Contact> getContactById(
    		@Parameter(description="Contact Id. Cannot be empty.", required=true)
    		@PathVariable long contactId) {
        Contact contact = null;
        try {
            contact = contactService.findById(contactId);
            
        } catch (ResourceNotFoundException ex) {
        	  
        	return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(contact);
    }

    @Operation(summary = "Add a new Contact", description = "", tags = { "contact" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Contact created",
                content = @Content(schema = @Schema(implementation = Contact.class))), 
        @ApiResponse(responseCode = "4xx", description = "Invalid input"), 
        @ApiResponse(responseCode = "5xx", description = "Contact already exists") })	

    @PostMapping(value = "/api/contacts/add")
    public ResponseEntity<String> addContact(
    		@Parameter(description="Contact to add. Cannot null or empty.", required=true, schema=@Schema(implementation = Contact.class))
            @RequestBody Contact contact) {        
        try {
            Contact newContact = contactService.save(contact);
            return ResponseEntity.ok().body("Create a new contact successful:" + newContact.toString());
        } catch (Exception ex) {
            // log exception first, 
            // then show error
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Create new contact failed due to bad request contents.");
        }        
    }

    @Operation(summary = "Update a Contact", description = "", tags = { "contact" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Employee updated",
                content = @Content(schema = @Schema(implementation = Contact.class))), 
        @ApiResponse(responseCode = "4xx", description = "Invalid input"), 
        @ApiResponse(responseCode = "5xx", description = "Update Failed, because of bad request contents.") })	

    @PutMapping(value = {"/api/contacts/{contactId}"})
    public ResponseEntity<String> updateContact(
    		@Parameter(description="Contact Id. Cannot be empty.", required=true)
            @PathVariable long contactId,
            @Parameter(description="Contact to update. Cannot be null or empty.", required=true, schema=@Schema(implementation = Contact.class))
            @RequestBody Contact contact) {        
        try {
            contact.setId(contactId);
            Contact newContact = contactService.update(contact);
            return ResponseEntity.ok().body("Update contact successful:" + newContact.toString());
        } catch (Exception ex) {
            // log exception first, 
            // then show error
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Update contact failed due to bad request contents:" + contact.toString());
        }
    }

    @Operation(summary = "输入Contact Id, 删除相应的Contact记录", description = "Delete a contact by its contact id.", tags = { "contact" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation",
                content = @Content(schema = @Schema(implementation = Contact.class))),
        @ApiResponse(responseCode = "5xx", description = "Delete operation failed due to Contact not found") })

    @DeleteMapping(value = {"/api/contacts/{contactId}"})
    public ResponseEntity<String> deleteContactById(
    		@Parameter(description="Contact Id. Cannot be empty.", required=true)
            @PathVariable long contactId) {
        try {
            contactService.deleteById(contactId);
            return ResponseEntity.ok().body("Delete contact successful: deleted contact id = " +  contactId);
        } catch (ResourceNotFoundException ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Delete failed due to bad request contact id :" + contactId);
        }
    }
}
