
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.security.Key;

public class EncodeAndCipher {

    public static void main(String[] args) {

        // Base64 encode/decode
        String base64Encode = Base64.encodeToString("Hello".getBytes());
        System.out.println(base64Encode);
        System.out.println(Base64.decodeToString(base64Encode));

        // Hex encode/decode
        String hexEncode = Hex.encodeToString("Hello".getBytes());
        System.out.println(hexEncode);
        System.out.println(new String(Hex.decode(hexEncode.getBytes())));

        // MD5hash
        String md5Hash = new Md5Hash("Hello", "salt").toString();
        System.out.println(md5Hash);

        // SHA
        String shaHash = new SimpleHash("SHA-1", "Hello", "salt").toString();
        System.out.println(shaHash);

        // AES
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);
        //generate key
        Key key = aesCipherService.generateNewKey();
        // encrypt
        String encryptText = aesCipherService.encrypt("Hello".getBytes(), key.getEncoded()).toHex();
        System.out.println(encryptText);
        // decrypt
        String decryptText = new String(aesCipherService.decrypt(Hex.decode(encryptText), key.getEncoded()).getBytes());
        System.out.println(decryptText);

    }
}
