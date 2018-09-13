package shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuthenticationTest {

    static final Logger logger = LoggerFactory.getLogger(AuthenticationTest.class);

    public static void main(String[] args) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        // Step 1: Collect the Subject’s principals and credentials
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123456");
        try {
            // Step 2: Submit the principals and credentials
            subject.login(token);
        } catch (UnknownAccountException uae) {

        } catch (IncorrectCredentialsException ice) {

        } catch (LockedAccountException lae) {

        } catch (ExcessiveAttemptsException eae) {

        } catch (AuthenticationException e) {
            // Step 3: Handling Success or Failure
            e.printStackTrace();
            return;
        }
        Assert.assertEquals(true, subject.isAuthenticated());
        logger.info("login success");
        subject.logout();
    }

}
