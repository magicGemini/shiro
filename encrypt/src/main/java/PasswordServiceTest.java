package shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.format.DefaultHashFormatFactory;
import org.apache.shiro.crypto.hash.format.Shiro1CryptFormat;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordServiceTest {

    static final Logger logger = LoggerFactory.getLogger(PasswordServiceTest.class);

    public static void main(String[] args) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-passwordService.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken("zhang", "123456"));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            logger.error("login failed!");
        }
        subject.logout();
    }

    static void passwordEncrypt() {
        DefaultPasswordService passwordService = new DefaultPasswordService();
        passwordService.setHashService(new DefaultHashService());
        passwordService.setHashFormat(new Shiro1CryptFormat());
        passwordService.setHashFormatFactory(new DefaultHashFormatFactory());
        String encryptedPasswd = passwordService.encryptPassword("123456");
        System.out.println(encryptedPasswd);
    }
}
