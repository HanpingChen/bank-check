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

    // 职位
    private String position;

    // 机构城市
    private String city;

    // 机构代码
    private String branch;

    // 处理业务类型，只有业务管理部门的员工有，其他员工均为null
    private String type;

    // 支行名称
    private String subbranch;

    public String getSubbranch() {
        return subbranch;
    }

    public void setSubbranch(String subbranch) {
        this.subbranch = subbranch;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

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
