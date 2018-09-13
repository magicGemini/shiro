import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;

import java.util.Arrays;

public class PermissionAuthz {

    public static void main(String[] args) {

        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123456");
        subject.login(token);

        // isPermitted(String)
        Assert.assertTrue(subject.isPermitted("user:create"));
        //isPermitted(Permission)
        Permission p = new WildcardPermission("user:create");
        Assert.assertTrue(subject.isPermitted(p));
        // isPermitted(String... )
        boolean[] result = subject.isPermitted("user:create", "user:update");
        Assert.assertEquals(true, result[0]);
        Assert.assertEquals(true, result[1]);
        // isPermittedAll
        Assert.assertTrue(subject.isPermittedAll("user:create", "user:update", "user:delete"));

        // checkPermission(String)：单个资源单个权限
        subject.checkPermission("user:create");
        // checkPermissions(String...)：单个资源多个权限
        subject.checkPermissions("user:create", "user:delete", "user:update");
        // checkPermissions(String...)：单个资源全部权限
        subject.checkPermissions("user:*");
        // 所有资源全部权限
        subject.checkPermission("user:view");

        subject.logout();
    }
}
