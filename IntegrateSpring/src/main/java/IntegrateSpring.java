import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegrateSpring {

    @Bean
    SecurityManager securityManager() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(myRealm());
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean
    Realm myRealm() {
        return new MyRealm();
    }

    static final Logger logger = LoggerFactory.getLogger(IntegrateSpring.class);

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(IntegrateSpring.class);

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken("zhang", "123456"));
        } catch (AuthenticationException e) {
            logger.error("login failed.");
        }
        subject.logout();
    }
}
