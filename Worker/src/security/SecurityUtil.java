package security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 * The `SecurityUtil` class provides utility methods for security-related
 * operations such as encryption, decryption, key generation, and serialization.
 *
 * <p>
 * It includes methods for symmetric and asymmetric encryption, key management,
 * and object serialization. These methods are used for secure communication and
 * data protection.
 *
 */
public class SecurityUtil {

    /**
     * Generates a random alpha-numeric string of the specified length.
     *
     * @param n The length of the generated string.
     * @return A random alpha-numeric string.
     */
    public static String RandomAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz"
                + "+/";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    /**
     * Decrypts a session key using a private key.
     *
     * @param CipherSessionKeyString The ciphered session key string.
     * @param prikey The private key used for decryption.
     * @return The decrypted session key.
     */
    public static SecretKey DecryptSessionKey(String CipherSessionKeyString, PrivateKey prikey) {
        String SessionKeyString = SecurityUtil.asyDecrypt(CipherSessionKeyString, prikey);
        return SecurityUtil.B64StringTokey(SessionKeyString);
    }

    /**
     * Encrypts a session key using a public key.
     *
     * @param SessionKey The session key to be encrypted.
     * @param pubkey The public key used for encryption.
     * @return The ciphered session key string.
     */
    public static String EncryptSessionKey(SecretKey SessionKey, PublicKey pubkey) {
        String SessionKeyString = SecurityUtil.keytoB64String(SessionKey);
        return SecurityUtil.asyEncrypt(SessionKeyString, pubkey);
    }

    /**
     * Encrypts an object using a symmetric key.
     *
     * @param obj The object to be encrypted.
     * @param sessionKey The symmetric key used for encryption.
     * @return The ciphered object as a string.
     */
    public static String SymEncryptObj(Object obj, SecretKey sessionKey) {
        byte[] ObjectBytes = SecurityUtil.convertObjectToBytes(obj);
        String ObjectString = SecurityUtil.SymEncrypt(ObjectBytes, sessionKey);
        return ObjectString;
    }

    /**
     * Decrypts a ciphered object string using a symmetric key.
     *
     * @param ObjectString The ciphered object string.
     * @param sessionKey The symmetric key used for decryption.
     * @return The decrypted object.
     */
    public static Object SymDecryptObj(String ObjectString, SecretKey sessionKey) {
        byte[] ObjectBytes = SecurityUtil.SymDecrypt((String) ObjectString, sessionKey);
        Object obj = SecurityUtil.convertBytesToObject(ObjectBytes);
        return obj;
    }

    /**
     * Converts an object to a byte array.
     *
     * @param obj The object to be converted.
     * @return A byte array representation of the object.
     */
    public static byte[] convertObjectToBytes(Object obj) {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(obj);
            return boas.toByteArray();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        throw new RuntimeException();
    }

    /**
     * Converts a byte array to an object.
     *
     * @param bytes The byte array to be converted.
     * @return The deserialized object.
     */
    public static Object convertBytesToObject(byte[] bytes) {
        InputStream is = new ByteArrayInputStream(bytes);
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }
        throw new RuntimeException();
    }

    /**
     * Generates a new symmetric key.
     *
     * @return A newly generated symmetric key.
     */
    public static SecretKey SecretKeyGen() {
        try {
            KeyGenerator KeyGen = KeyGenerator.getInstance("AES");
            KeyGen.init(128);
            return KeyGen.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Exception in ICentre() : " + ex.getMessage() + "\n");
        }
        throw new RuntimeException();
    }

    /**
     * Reads keys from a file and returns them as a HashMap.
     *
     * @param Keyfile The path to the file containing the keys.
     * @return A HashMap containing the keys.
     */
    public static HashMap ReadinKeys(String Keyfile) {
        FileInputStream pfin = null;
        try {
            pfin = new FileInputStream(Keyfile);
            ObjectInputStream obin = new ObjectInputStream(pfin);
            HashMap keys = (HashMap) obin.readObject();
            obin.close();
            pfin.close();
            return keys;
        } catch (FileNotFoundException ex) {
            System.out.println("Exception in ReadinKeys(): " + ex.getMessage() + "\n");
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Exception in readinKeys(): " + ex.getMessage() + "\n");
        } finally {
            try {
                pfin.close();
            } catch (IOException ex) {
                System.out.println("Exception in readinKeys(): " + ex.getMessage() + "\n");
            }
        }
        throw new RuntimeException();
    }

    /**
     * Encrypts a byte array message using a symmetric key.
     *
     * @param message The message to be encrypted.
     * @param sk The symmetric key used for encryption.
     * @return The ciphered message as a string.
     */
    public static String SymEncrypt(byte[] message, Key sk) {
        String ctext = new String();
        try {
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, sk);
            ctext = Base64.getEncoder().encodeToString(aesCipher.doFinal(message));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println("Exception in SymEncrypt(): " + ex.getMessage());
        }
        return ctext;
    }

    /**
     * Decrypts a ciphered message string using a symmetric key.
     *
     * @param message The ciphered message string.
     * @param sk The symmetric key used for decryption.
     * @return The decrypted message as a byte array.
     */
    public static byte[] SymDecrypt(String message, Key sk) {
        byte[] ptext = null;
        try {
            byte[] msgbytes = Base64.getDecoder().decode(message);
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.DECRYPT_MODE, sk);
            ptext = aesCipher.doFinal(msgbytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println("Exception in SymDecrypt(): " + ex.getMessage());
        }
        return ptext;
    }

    /**
     * Encrypts a string message using an asymmetric key (public key).
     *
     * @param message The message to be encrypted.
     * @param pk The public key used for encryption.
     * @return The ciphered message as a string.
     */
    public static String asyEncrypt(String message, Key pk) {
        String etext = new String();
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pk);
            byte[] cipherData = cipher.doFinal(message.getBytes("UTF-8"));
            etext = Base64.getEncoder().encodeToString(cipherData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | UnsupportedEncodingException
                | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println("Exception in asyEncrypt(): " + ex.getMessage());
        }
        return etext;

    }

    /**
     * Decrypts a ciphered message string using an asymmetric key (private key).
     *
     * @param message The ciphered message string.
     * @param prik The private key used for decryption.
     * @return The decrypted message as a string.
     */
    public static String asyDecrypt(String message, Key prik) {
        String ptext = new String();
        try {
            byte[] msgbytes = Base64.getDecoder().decode(message);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, prik, cipher.getParameters());
            ptext = new String(cipher.doFinal(msgbytes));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | InvalidAlgorithmParameterException
                | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println("Exception in asyDecrypt(): " + ex.getMessage());
        }
        return ptext;
    }

    /**
     * Converts a base64-encoded string to a SecretKey.
     *
     * @param kString The base64-encoded string representing a SecretKey.
     * @return The SecretKey.
     */
    public static SecretKey B64StringTokey(String kString) {
        byte[] bytekey = Base64.getDecoder().decode(kString);
        return new SecretKeySpec(bytekey, 0, bytekey.length, "AES");
    }

    /**
     * Converts a SecretKey to a base64-encoded string.
     *
     * @param sKey The SecretKey to be converted.
     * @return The base64-encoded string.
     */
    public static String keytoB64String(SecretKey sKey) {
        return Base64.getEncoder().encodeToString(sKey.getEncoded());
    }

    /**
     * Converts a PublicKey to a base64-encoded string.
     *
     * @param pKey The PublicKey to be converted.
     * @return The base64-encoded string.
     */
    public static String pubKeytoB64String(PublicKey pKey) {
        return Base64.getEncoder().encodeToString(pKey.getEncoded());
    }

    /**
     * Converts a PrivateKey to a base64-encoded string.
     *
     * @param priKey The PrivateKey to be converted.
     * @return The base64-encoded string.
     */
    public static String priKeytoB64String(PrivateKey priKey) {
        return Base64.getEncoder().encodeToString(priKey.getEncoded());
    }
}
