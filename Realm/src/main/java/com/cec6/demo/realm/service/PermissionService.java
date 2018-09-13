package com.cec6.demo.realm.service;


import com.cec6.demo.realm.domain.Permission;

public interface PermissionService {

    public Permission createPermission(Permission permission);
    public void deletePermission(Long permissionid);
}
