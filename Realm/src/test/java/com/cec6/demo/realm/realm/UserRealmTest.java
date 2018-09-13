package com.cec6.demo.realm.realm;

import com.cec6.demo.realm.BaseTest;
import junit.framework.Assert;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * UserRealm Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>九月 13, 2018</pre>
 */
public class UserRealmTest extends BaseTest {

    @Test
    public void testLoginSuccess() {
        login("classpath:shiro-userRealm.ini", u1.getUsername(), "123456");
        Assert.assertTrue(subject().isAuthenticated());
    }

    @Test(expected = UnknownAccountException.class)
    public void testLoginFailWithUnknownUsername() {
        login("classpath:shiro-userRealm.ini", u1.getUsername() + "1", "123456");
    }

    @Test(expected = IncorrectCredentialsException.class)
    public void testLoginFailWithErrorPassowrd() {
        login("classpath:shiro-userRealm.ini", u1.getUsername(), "123456" + "1");
    }

    @Test(expected = LockedAccountException.class)
    public void testLoginFailWithLocked() {
        login("classpath:shiro-userRealm.ini", u4.getUsername(), "123456" + "1");
    }

    @Test(expected = ExcessiveAttemptsException.class)
    public void testLoginFailWithLimitRetryCount() {
        for (int i = 1; i <= 5; i++) {
            try {
                login("classpath:shiro-userRealm.ini", u3.getUsername(), "123456" + "1");
            } catch (Exception e) {/*ignore*/}
        }
        login("classpath:shiro-userRealm.ini", u3.getUsername(), "123456" + "1");
        //需要清空缓存，否则后续的执行就会遇到问题(或者使用一个全新账户测试)
    }

    @Test
    public void testHasRole() {
        login("classpath:shiro-userRealm.ini", u1.getUsername(), "123456");
        Assert.assertTrue(subject().hasRole("admin"));
    }

    @Test
    public void testNoRole() {
        login("classpath:shiro-userRealm.ini", u2.getUsername(), "123456");
        Assert.assertFalse(subject().hasRole("admin"));
    }

    @Test
    public void testHasPermission() {
        login("classpath:shiro-userRealm.ini", u1.getUsername(), "123456");
        Assert.assertTrue(subject().isPermittedAll("user:create", "menu:create"));
    }

    @Test
    public void testNoPermission() {
        login("classpath:shiro-userRealm.ini", u2.getUsername(), "123456");
        Assert.assertFalse(subject().isPermitted("user:create"));
    }


} 
