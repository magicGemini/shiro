import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

public class CredentialsMatcherTest {

    public static void main(String[] args) {
        String algorithm = "md5";
        String username = "root";
        String password = "123456";
        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        int hashIterations = 2;

        SimpleHash hash = new SimpleHash(algorithm, password, salt1 + salt2, hashIterations);
        System.out.println(hash.toString());
    }
}
