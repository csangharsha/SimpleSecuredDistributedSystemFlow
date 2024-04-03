package contract;

import java.io.Serializable;

/**
 * The `CSAuthenticator` class represents a serializable object used for
 * authentication and session management. It encapsulates user-related
 * information, such as plain and ciphered usernames, a verification string, and
 * a session key.
 *
 */
public class CSAuthenticator implements Serializable {

    private String PlainUserName;
    private String CipherUserName;
    private String VerficationString;
    private String SessionKey;

    /**
     * Constructs a new `CSAuthenticator` instance with default values.
     */
    public CSAuthenticator() {
    }

    /**
     * Constructs a new `CSAuthenticator` instance with the specified user
     * information.
     *
     * @param PlainUserName The plain (unencrypted) username of the user.
     * @param CipherUserName The ciphered (encrypted) username of the user.
     * @param VerficationString The verification string used for authentication.
     * @param SessionKey The session key for secure communication.
     */
    public CSAuthenticator(String PlainUserName, String CipherUserName, String VerficationString, String SessionKey) {
        this.PlainUserName = PlainUserName;
        this.CipherUserName = CipherUserName;
        this.VerficationString = VerficationString;
        this.SessionKey = SessionKey;
    }

    /**
     * Gets the plain (unencrypted) username of the user.
     *
     * @return the PlainUserName
     */
    public String getPlainUserName() {
        return PlainUserName;
    }

    /**
     * Sets the plain (unencrypted) username of the user.
     *
     * @param PlainUserName the PlainUserName to set
     */
    public void setPlainUserName(String PlainUserName) {
        this.PlainUserName = PlainUserName;
    }

    /**
     * Gets the ciphered (encrypted) username of the user.
     *
     * @return the CipherUserName
     */
    public String getCipherUserName() {
        return CipherUserName;
    }

    /**
     * Sets the ciphered (encrypted) username of the user.
     *
     * @param CipherUserName the CipherUserName to set
     */
    public void setCipherUserName(String CipherUserName) {
        this.CipherUserName = CipherUserName;
    }

    /**
     * Gets the verification string used for authentication.
     *
     * @return the VerficationString
     */
    public String getVerficationString() {
        return VerficationString;
    }

    /**
     * Sets the verification string used for authentication.
     *
     * @param VerficationString the VerficationString to set
     */
    public void setVerficationString(String VerficationString) {
        this.VerficationString = VerficationString;
    }

    /**
     * Gets the session key for secure communication.
     *
     * @return the SessionKey
     */
    public String getSessionKey() {
        return SessionKey;
    }

    /**
     * Sets the session key for secure communication.
     *
     * @param SessionKey the SessionKey to set
     */
    public void setSessionKey(String SessionKey) {
        this.SessionKey = SessionKey;
    }

}
