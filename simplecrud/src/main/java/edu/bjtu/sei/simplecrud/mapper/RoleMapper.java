package edu.bjtu.sei.simplecrud.mapper;

//import java.util.List;

import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.mapping.FetchType;
//import org.apache.ibatis.annotations.Results;
//import org.apache.ibatis.annotations.Result;
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Options;
//import org.apache.ibatis.annotations.Param;

import edu.bjtu.sei.simplecrud.domain.Role;

@Mapper
public interface RoleMapper {
	@Select("select * from role where id = #{rid}")
    Role findById(String rid);
}
