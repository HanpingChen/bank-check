package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.service.UserManagerService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-26
 * Time:16:21
 */
@Service
public class UserManagerServiceImpl implements UserManagerService {

    @Autowired
    IdentityService identityService;
    @Override
    public void addUserToGroup(String userId, String groupId) {
        identityService.createMembership(userId, groupId);
    }

    @Override
    public void createGroup(String groupId) {
        Group group = identityService.newGroup(groupId);
        identityService.saveGroup(group);
    }

    @Override
    public void createUser(String uerId) {
        User user = identityService.newUser(uerId);
        identityService.saveUser(user);
    }

    @Override
    public void addUsersToGroup(List<String> userIds, String gruopId) {
        for(String userId : userIds){
            identityService.createMembership(userId, gruopId);
        }
    }

    @Override
    public void createUsers(List<String> userIds) {
        for (String userId: userIds){
            User user = identityService.newUser(userId);
            identityService.saveUser(user);
        }
    }
}
