import java.util.Arrays;
import java.util.Base64;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

// Name: Param Jansari
// Student Number: 6046833

public class AESEncryption {
 
    private static SecretKeySpec sKey;
    private static byte[] key;
    public static boolean enableEncryption = false; // decides if AES encryption should be enabled in communication channel
 
    // Generates an AES Key
    public static void genKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8"); // encode key 
            sha = MessageDigest.getInstance("SHA-256"); // set hash algorithm to SHA-256
            key = sha.digest(key); // hash encoded key
            key = Arrays.copyOf(key, 16); // add padding
            sKey = new SecretKeySpec(key, "AES"); // generate key for AES
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
    }
 
    // Encrypts message based on provided password
    public static String encryptMessage(String message, String password) 
    {
        try
        {
            genKey(password); // generate AES key
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // set encryption algorithm
            cipher.init(Cipher.ENCRYPT_MODE, sKey); // set to encrypt with key
            return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes("UTF-8"))); // encrypted message
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }
 
    // Decrypts messages based on provided password
    public static String decryptMessage(String message, String secret) 
    {
        try
        {
            genKey(secret); // generate AES key
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING"); // set encryption algorithm
            cipher.init(Cipher.DECRYPT_MODE, sKey); // set to decrypt with key
            return new String(cipher.doFinal(Base64.getDecoder().decode(message))); // decrypted message
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }
}