package edu.bjtu.sei.simplecrud.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;

@Validated
@Entity
@Table(name = "contact")
@Getter
@Setter
public class Contact implements Serializable {

    private static final long serialVersionUID = 4048798961366546485L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 100)
	@Schema(description = "Contact name.", example = "John Lee", required = true)
    private String name;
    
    //@Pattern(regexp ="^(0[0-9]{2,3}/-)?([2-9][0-9]{6,7})+(/-[0-9]{1,4})?$", message = "Phone number")
    @Size(max = 25)
	@Schema(description = "Contact phone", example = "Prefer mobile phone number,e.g,130-xxxx-xxxx", required = true)
    private String phone;
    
    @Email(message = "Email Address")
    @Size(max = 100)
	@Schema(description = "Contact email", example = "John_Lee@sei.bjtu.edu.cn", required = true)
    private String email;
    
    @Size(max = 50)
	@Schema(description = "Contact address", example = " ", required = true)
    private String address1;
    
    @Size(max = 50)
	@Schema(description = "Contact 2nd address", example = "", required = false)
    private String address2;
    
    @Size(max = 50)
	@Schema(description = "Contact additional address.", example = " ", required = false)
   private String address3;
    
    @Size(max = 20)
	@Schema(description = "Contact postcode", example = "100098 refers to BJTU of Haidian, Beijing", required = false)
   private String postalCode;
    
    @Column(length = 1024)
//    @Size(max = 1024)
	@Schema(description = "Contact additional information.", example = "His/her title, career introduction and so on.", required = false)
    private String note;
    
//    public void setContact(Long Id, String Name, String Phone, String Email, String Address1, String Address2, String Address3, String Postalcode, String Note) {
//           id = Id;
//           name = Name;
//           phone = Phone;
//           email = Email;
//           address1 = Address1;
//           address2 = Address2;
//           address3 = Address3;
//           postalCode = Postalcode;
//           note = Note;
//    }
}
