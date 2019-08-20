package com.cmb.bankcheck.mapper;

import com.cmb.bankcheck.entity.ApplyEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-09
 * Time:16:23
 */
@Mapper
public interface ApplyMapper {


    @Select("select apply.* from apply, process where apply.apply_id = process.apply_id and process.process_id = #{processId}")
    public ApplyEntity queryApplyByProcessId(@Param("processId") String processId);

    @Insert("insert into apply(apply_id, user_id, username,msg, process_key,starter,xmtype,amt,record,cor,analyse,situation, branch,subbranch, discount_type) " +
            "values(#{applyId}, #{userId}, #{username}, #{msg}, #{processKey},#{starter},#{xmtype},#{amt},#{record},#{cor},#{analyse},#{situation},#{branch},#{subbranch},#{discountType})")
    public int insertApply(ApplyEntity entity);
}
