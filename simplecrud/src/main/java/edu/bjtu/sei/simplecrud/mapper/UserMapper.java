package edu.bjtu.sei.simplecrud.mapper;

//import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import edu.bjtu.sei.simplecrud.domain.User;

@Mapper
public interface UserMapper {

	  @Select("select * from user u, role r, users_roles s where email = #{email} and u.id = s.user_id and r.id = s.role_id")
	  @Results(id = "userMap",value = {
      //id表示主键v
      @Result(id = true,column = "id",property = "id"),
      @Result(column = "email",property = "email"),
      @Result(column = "first_name",property = "firstName"),
      @Result(column = "last_name",property = "lastName"),
      @Result(column = "password",property = "password"),
      //定义一对多的关系映射，实现对account的封装
      //        @Many注解用于一对多或多对多查询使用
      //        select属性指定内容：查询用户的唯一标识
      //        column属性指定内容：用户根据id查询账户是所需要的参数
      //        fetchType属性指定内容:指定延时查询FetchType.LAZY或立即查询FetchType.EAGER
      @Result(property = "roles",column = "id",many = @Many(select = "edu.bjtu.sei.simplecrud.mapper.RoleMapper.findById",fetchType = FetchType.LAZY))})
	  User findByEmail(String email);

	  @Insert("insert into user values(null,#{user.email},#{user.firstName},#{user.lastName},#{user.password})")
	  @Options(keyProperty="user.id",useGeneratedKeys=true)
	  void save(@Param("user")User user);
}
