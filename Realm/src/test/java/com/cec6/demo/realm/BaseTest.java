package com.cec6.demo.realm;

import com.cec6.demo.realm.domain.Permission;
import com.cec6.demo.realm.domain.Role;
import com.cec6.demo.realm.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cec6.demo.realm.service.*;
import com.cec6.demo.realm.utils.JdbcTemplateUtils;

public abstract class BaseTest {

    protected PermissionService permissionService = new PermissionServiceImpl();
    protected RoleService roleService = new RoleServiceImpl();
    protected UserService userService = new UserServiceImpl();

    protected Permission p1;
    protected Permission p2;
    protected Permission p3;
    protected Role r1;
    protected Role r2;
    protected User u1;
    protected User u2;
    protected User u3;
    protected User u4;

    @Before
    public void setUp() {
        JdbcTemplateUtils.jdbcTemplate().update("DELETE FROM sys_users");
        JdbcTemplateUtils.jdbcTemplate().update("DELETE FROM sys_users_roles");
        JdbcTemplateUtils.jdbcTemplate().update("DELETE FROM sys_roles");
        JdbcTemplateUtils.jdbcTemplate().update("DELETE FROM sys_roles_permissions");
        JdbcTemplateUtils.jdbcTemplate().update("DELETE FROM sys_permissions");

        // create permissions
        p1 = new Permission("user:create", "用户模块新增", Boolean.TRUE);
        p2 = new Permission("user:update", "用户模块修改", Boolean.TRUE);
        p3 = new Permission("menu:create", "菜单模块新增", Boolean.TRUE);
        permissionService.createPermission(p1);
        permissionService.createPermission(p2);
        permissionService.createPermission(p3);

        //create roles
        r1 = new Role("admin", "管理员", Boolean.TRUE);
        r2 = new Role("userAdmin", "用户管理员", Boolean.TRUE);
        roleService.createRole(r1);
        roleService.createRole(r2);
        roleService.correlationPermissions(r1.getId(), p1.getId());
        roleService.correlationPermissions(r1.getId(), p2.getId());
        roleService.correlationPermissions(r1.getId(), p3.getId());
        roleService.correlationPermissions(r2.getId(), p1.getId());
        roleService.correlationPermissions(r2.getId(), p2.getId());

        // create Users
        u1 = new User("zhang", "123456");
        u2 = new User("wang", "123456");
        u3 = new User("li", "123456");
        u4 = new User("zhao", "123456");
        u4.setLocked(Boolean.TRUE);
        userService.createUser(u1);
        userService.createUser(u2);
        userService.createUser(u3);
        userService.createUser(u4);
        userService.correlationRoles(u1.getId(), r1.getId());
    }

    @After
    public void after() {
        ThreadContext.unbindSubject();
    }

    private Logger logger = LoggerFactory.getLogger(BaseTest.class);

    protected void login(String file, String username, String password) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(file);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
        } catch (AuthenticationException e) {
            logger.error("login failed~");
        }

    }

    protected Subject subject() {
        return SecurityUtils.getSubject();
    }
}
