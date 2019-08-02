package com.cmb.bankcheck.mapper;

import com.cmb.bankcheck.entity.ProcessEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-31
 * Time:13:07
 * process表的数据访问接口
 * process表中存储了用户id以及processid，可通过用户id查询流程，也可以通过流程id查询他属于哪个用户
 */
@Mapper
public interface ProcessMapper {

    /**
     * 通过用户id查询拥有的流程
     * @param userId
     * @return
     */
    List<ProcessEntity> queryProcessByUser(String userId);

    /**
     * 插入一条用户、流程信息
     * @param userId
     * @param processId
     * @return
     */
    @Insert("insert into process(user_id, process_id,remark) values (#{userId}, #{processId},#{remark})")
    int insertProcess(@Param("userId") String userId, @Param("processId") String processId,@Param("status") int status,@Param("remark") String remark);

    /**
     * 根据instanceId，查询得到userId等相关信息
     * @param processId
     * @return
     */
    @Select("select * from process where processId=#{processId}")
    public List<ProcessEntity> querytProcessByinsId(@Param("processId") String processId);

}
