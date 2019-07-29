package com.cmb.bankcheck.service;

import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-26
 * Time:16:19
 */
public interface UserManagerService {

    void addUserToGroup(String userId, String groupId);

    void createGroup(String groupId);

    void createUser(String uerId);

    void addUsersToGroup(List<String> userIds, String gruopId);

    void createUsers(List<String> userIds);
}
