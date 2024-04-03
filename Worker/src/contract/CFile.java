package contract;

import java.io.Serializable;

/**
 * The `CFile` class represents a serializable object that encapsulates a file's
 * name and its content as a byte array. This class is designed to be used for
 * various file-related operations and serialization purposes.
 * 
 */
public class CFile implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fname;
    private byte[] fbyte;

    /**
     * Constructs a new `CFile` instance with default values.
     */
    public CFile() {
    }

    /**
     * Constructs a new `CFile` instance with the specified file name and
     * content.
     *
     * @param fname The name of the file.
     * @param fbyte The binary content of the file as a byte array.
     */
    public CFile(String fname, byte[] fbyte) {
        this.fname = fname;
        this.fbyte = fbyte;
    }

    /**
     * Gets the file name.
     *
     * @return The name of the file.
     */
    public String getFname() {
        return fname;
    }

    /**
     * Sets the file name.
     *
     * @param fname The new name for the file.
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * Gets the binary content of the file.
     *
     * @return The content of the file as a byte array.
     */
    public byte[] getFbyte() {
        return fbyte;
    }

    /**
     * Sets the binary content of the file.
     *
     * @param fbyte The new content for the file as a byte array.
     */
    public void setFbyte(byte[] fbyte) {
        this.fbyte = fbyte;
    }

}
