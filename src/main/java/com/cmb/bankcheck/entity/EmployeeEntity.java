package com.cmb.bankcheck.entity;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-27
 * Time:12:21
 * 员工实体类
 */
public class EmployeeEntity {

    // 员工id
    private String id;
    // 员工姓名
    private String name;
    // 员工的角色
    private String role;
    // 部门名称
    private String apart;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getApart() {
        return apart;
    }

    public void setApart(String apart) {
        this.apart = apart;
    }

    @Override
    public String toString() {
        return this.getId()+" "+this.getApart()+" "+this.getRole();
    }
}
