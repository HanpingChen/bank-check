package com.cmb.bankcheck.mapper;

import com.cmb.bankcheck.entity.ProcessEntity;
import org.apache.ibatis.annotations.*;

import java.util.Date;
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
    @Insert("insert into process" +
            "(user_id, process_id,remark, create_time, status, parent_id, system_id, work_id) values " +
            "(#{userId}, #{processId},#{remark},#{createTime},#{status},#{parentId},#{systemId},#{workId})")
    int insertProcess(@Param("userId") String userId, @Param("processId") String processId, @Param("status") int status, @Param("remark") String remark,
                      @Param("createTime") Date createTime, @Param("systemId") String systemId,@Param("workId") String workId, @Param("parentId") String parentId
                      );

    /**
     * 根据instanceId，查询得到userId等相关信息
     * @param processId
     * @return
     */
    @Select("select * from process where process_id=#{processId}")
    public List<ProcessEntity> queryProcessByProcessId(@Param("processId") String processId);

    /**更新process表中用户的任务状态
     * 1,审批不通过时，需更新process表为不同过的状态
     * 2，审批结束时，需要更新用户的 状态为任务完成状态
     */
    @Update("update process set remark=#{remark},complete_time=#{completeTime}, status=#{status} where process_id=#{processId}")
    public  void updateProcessByProcessId(@Param("processId") String processId,@Param("remark") String remark,@Param("completeTime") Date completeTime,
                               @Param("status") int status);

}
