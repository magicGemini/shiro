package com.cec6.demo.realm.dao;


import com.cec6.demo.realm.domain.Permission;

public interface PermissionDao {

    public Permission createPermission(Permission permission);

    public void deletePermission(Long permissionId);

}
