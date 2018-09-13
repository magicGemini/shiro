package com.cec6.demo.realm.service;


import com.cec6.demo.realm.dao.PermissionDao;
import com.cec6.demo.realm.dao.impl.PermissionDaoImpl;
import com.cec6.demo.realm.domain.Permission;

public class PermissionServiceImpl implements PermissionService {

    private PermissionDao permissionDao = new PermissionDaoImpl();

    @Override
    public Permission createPermission(Permission permission) {
        return permissionDao.createPermission(permission);
    }

    @Override
    public void deletePermission(Long permissionid) {
        permissionDao.deletePermission(permissionid);
    }

}