package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.entity.EmployeeEntity;
import com.cmb.bankcheck.mapper.EmployeeMapper;
import com.cmb.bankcheck.service.ImportRoleInfoService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-08-07
 * Time:08:52
 * 导入员工信息表到角色表
 */
@Service
public class ImportRoleInfoServiceImpl implements ImportRoleInfoService {

    @Autowired
    EmployeeMapper mapper;

    @Autowired
    IdentityService identityService;

    public int importInfo(){
        // 查询所有的position
        List<String> positions = mapper.queryAllPosition();
        // 根据position创建组
        for (String position: positions){
            Group group = identityService.newGroup(position);
            identityService.saveGroup(group);
        }
        // 查询所有的员工id
        List<EmployeeEntity> employees = mapper.queryAll();
        // 创建用户并加入组
        for (EmployeeEntity employee: employees){
            User user = identityService.newUser(employee.getId());
            identityService.saveUser(user);
            identityService.createMembership(employee.getId(), employee.getPosition());
        }

        return 0;
    }
}


