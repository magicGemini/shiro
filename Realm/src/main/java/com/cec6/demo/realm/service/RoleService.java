package com.cec6.demo.realm.service;


import com.cec6.demo.realm.domain.Role;

public interface RoleService {

    public Role createRole(Role role);
    public void deleteRole(Long roleId);
    public void correlationPermissions(Long roleId, Long... permissionIds);
    public void uncorrelationPermissions(Long roleId, Long... permissionIds);

}
