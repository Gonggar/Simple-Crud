package edu.bjtu.sei.simplecrud.repository;

import edu.bjtu.sei.simplecrud.domain.Contact;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ContactRepository {

	private List<Contact> lista = new ArrayList<Contact>();

	public ContactRepository() {
		Contact s = new Contact();
		//1
		s.setContact((long)1, "Isabella Zhu", "12101121121", "isabella.zhu@sei.bjtu.edu", "北京市海淀区上园村3号北京交通大学", "", "", "100044", "1982年毕业于北方交通大学应用数学物理系，清华大学物理系教授、博士生导师，长江学者特聘教授。");
        lista.add(s);
        //2
        s = new Contact();
        s.setContact((long)2, "Lucas. Wang", "12101123146", "lucas.wang@sei.bjtu.edu", "北京市海淀区上园村3号北京交通大学", "", "", "100044", "毕业于交通大学贵州分校，加州大学伯克利分校教授。");
        lista.add(s);
        //3
        s = new Contact();
		s.setContact((long)3, "Ethan.Du", "12101678934", "ethan.du@sei.bjtu.edu", null, null, null, null, null);
        lista.add(s);
        //4
        s = new Contact();
		s.setContact((long)4, "Muhammad.Jiang", "12101986743", "muhammad.jiang@sei.bjtu.edu", null, null, null, null, null);
        lista.add(s);
        //5
        s = new Contact();
		s.setContact((long)5, "Amelia.Xie", "12101129987", "amelia.x@sei.bjtu.edu", "北京市海淀区上园村3号北京交通大学", "", "", "100044", "");
        lista.add(s);
        //6
        s = new Contact();
		s.setContact((long)6, "Emma.Zhang", "12101897655", "emma.zhang@sei.bjtu.edu", null, null, null, null, null);
        lista.add(s);
        //7
        s = new Contact();
		s.setContact((long)7, "蒋玉荣", "12101345621", "yr.jiang@sei.bjtu.edu", null, null, null, null, null);
        lista.add(s);
        //8
        s = new Contact();
		s.setContact((long)8, "方中青", "12101188712", "zhongqing.fang@sei.bjtu.edu", null, null, null, null, null);
        lista.add(s);
        //9
        s = new Contact();
		s.setContact((long)9, "徐海冰", "12101334412", "haibing.xu@sei.bjtu.edu", "北京市海淀区上园村3号北京交通大学", "", "", "100044", "1982年毕业于北方交通大学机械系，曾任原铁道部党组成员、纪委书记、中国铁路总公司党组纪检组组长。");
        lista.add(s);
		
	}


	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		boolean isExist = false;
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(id)) {
				isExist = true;
				break;
			}
		}

		return isExist;
	}

	public Object findById(Long id) {
		// TODO Auto-generated method stub
		Contact s = null;
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(id)) {
				s = lista.get(i);
				break;
			}
		}
		return s;
	}

	public Contact save(Contact contact) {
		// TODO Auto-generated method stub
		contact.setId((long)lista.size());
		lista.add(contact);
		return contact;
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	public Long count() {
		// TODO Auto-generated method stub
		
		return (long) lista.size();
	}

	public Iterable<Contact> findAll() {
		// TODO Auto-generated method stub
		return lista;
	}


	public Iterable<Contact> findAll(int pageNumber, int rowPerPage) {
		// TODO Auto-generated method stub
		int size = lista.size();
		int fromidx = (pageNumber-1)*rowPerPage;
		if (fromidx>size) fromidx = 0;
		int toidx = fromidx + rowPerPage;
		if (toidx > size) toidx = size;
		
		return lista.subList(fromidx, toidx);
	}
}

