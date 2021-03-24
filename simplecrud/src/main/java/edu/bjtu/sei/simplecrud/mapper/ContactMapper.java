package edu.bjtu.sei.simplecrud.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import edu.bjtu.sei.simplecrud.domain.Contact;

@Mapper
public interface ContactMapper {
	
		void save(Contact contact);
		
		void delete(Long id);
		
		void update(Contact contact);
		
		Contact find(Long id);
		
		List<Contact> findAll();

		boolean existsById(Long id);
		
}
