package worker;

import contract.CFile;
import contract.CSAuthenticator;
import contract.Task;
import contract.TaskList;
import contract.TaskObject;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import security.SecurityUtil;

/**
 * The Worker class represents a simplified volunteer computing worker
 * application that connects to a master server, performs tasks, and engages in
 * mutual authentication. It provides a graphical user interface for user
 * interaction and task execution.
 */
public class Worker extends javax.swing.JFrame {

    private String masterHost;
    private int masterPort;
    private TaskList taskList = new TaskList();

    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private SecretKey sessionKey;

    /**
     * Creates new form Worker
     */
    public Worker() {
        initComponents();
        initialDisableViews();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException ex) {

                }
            }
        });
    }

    /**
     * Disables certain user interface elements when the application starts.
     * This method is typically called during the initialization of the
     * application to ensure that specific views are not accessible until
     * certain conditions are met.
     */
    private void initialDisableViews() {
        clearBoardBtn.setEnabled(false);
        taskListComboBox.setEnabled(false);
        refreshBtn.setEnabled(false);
        calculateBtn.setEnabled(false);
        authenticateBtn.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        masterHostTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        masterPortTextField = new javax.swing.JTextField();
        setBtn = new javax.swing.JButton();
        clearBoardBtn = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        taskListComboBox = new javax.swing.JComboBox<>();
        refreshBtn = new javax.swing.JButton();
        calculateBtn = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        progressBoardTA = new javax.swing.JTextArea();
        authenticateBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simplified Volunteer Computing Worker");
        setMinimumSize(new java.awt.Dimension(750, 550));
        setResizable(false);

        jLabel1.setText("Master Host");

        masterHostTextField.setText("localhost");

        jLabel2.setText("Master Port (Object)");

        masterPortTextField.setText("5001");

        setBtn.setText("Set");
        setBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setBtnActionPerformed(evt);
            }
        });

        clearBoardBtn.setText("Clear Board");
        clearBoardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBoardBtnActionPerformed(evt);
            }
        });

        jLabel3.setText("Task List");

        refreshBtn.setText("Refresh");
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });

        calculateBtn.setText("Calculate");
        calculateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateBtnActionPerformed(evt);
            }
        });

        jLabel4.setText("Progress Board");

        progressBoardTA.setEditable(false);
        progressBoardTA.setColumns(20);
        progressBoardTA.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        progressBoardTA.setLineWrap(true);
        progressBoardTA.setRows(5);
        jScrollPane1.setViewportView(progressBoardTA);

        authenticateBtn.setText("Authenticate");
        authenticateBtn.setEnabled(false);
        authenticateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authenticateBtnActionPerformed(evt);
            }
        });

        jLabel5.setText("User Name");

        usernameTextField.setText("Stephen Smith");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(263, 263, 263)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 299, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(taskListComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(refreshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(calculateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(masterHostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(90, 90, 90)
                                        .addComponent(jLabel5)
                                        .addGap(7, 7, 7)
                                        .addComponent(usernameTextField)
                                        .addGap(3, 3, 3)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(masterPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(setBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(authenticateBtn)
                .addGap(18, 18, 18)
                .addComponent(clearBoardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(masterHostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(masterPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setBtn)
                    .addComponent(authenticateBtn)
                    .addComponent(clearBoardBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(taskListComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(refreshBtn))
                    .addComponent(calculateBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setBtnActionPerformed
        // Connect to the master
        this.masterHost = this.masterHostTextField.getText();
        try {
            this.masterPort = Integer.parseInt(this.masterPortTextField.getText());
            connectToMaster();
        } catch (NumberFormatException e) {
            this.progressBoardTA.setText(this.progressBoardTA.getText() + "\r\n Master Port should be a number.");
        }

    }//GEN-LAST:event_setBtnActionPerformed

    private void clearBoardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBoardBtnActionPerformed
        this.progressBoardTA.setText("");
    }//GEN-LAST:event_clearBoardBtnActionPerformed

    private void calculateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateBtnActionPerformed
        try {
            // Get the selected task name from the task list combo box.
            String taskName = taskList.getAvailableTasks()[taskListComboBox.getSelectedIndex()];
            // Update the progress board with the task in progress message.
            progressBoardTA.append("The Task (" + taskName + ") is in progress...\r\n");

            // Get the class name associated with the selected task.
            String className = taskList.getTaskClassName()[taskListComboBox.getSelectedIndex()];
            // Encrypt the class name using the session key.
            String encryptedClassName = encryptMessage(className, sessionKey);

            // Send the encrypted class name to the server.
            outputStream.writeObject(encryptedClassName);
            outputStream.flush();

            while (true) {
                // Receive an encrypted message from the server.
                String encryptedMessage = (String) inputStream.readObject();
                // Decrypt the received message using the session key.
                Object message = decryptMessage(encryptedMessage, sessionKey);

                if (message instanceof CFile) {
                    // If the received message is a CFile, it contains a file to be processed.
                    progressBoardTA.append("The encrypted CFile String: " + encryptedMessage + "\r\n");

                    // Extract the CFile object.
                    CFile cFile = (CFile) message;
                    // Write the file content to a local file.
                    FileOutputStream fo = new FileOutputStream(cFile.getFname());
                    BufferedOutputStream bos = new BufferedOutputStream(fo);
                    bos.write(cFile.getFbyte(), 0, cFile.getFbyte().length);
                    bos.close();

                    // Create a TaskObject to send back to the server with the task ID.
                    TaskObject taskObject = new TaskObject();
                    taskObject.setTaskID(getSelectedTaskID());

                    // Encrypt the TaskObject and send it to the server.
                    String encryptedTaskObject = encryptMessage(taskObject, sessionKey);

                    outputStream.writeObject(encryptedTaskObject);
                    outputStream.flush();
                } else if (message instanceof TaskObject) {
                    // If the received message is a TaskObject, it represents a task to be executed.
                    TaskObject taskObj = (TaskObject) message;
                    Task task = taskObj.getTObject();
                    if (task.getResult() == null) {
                        // If the task result is not yet computed, execute the task.
                        task.executeTask();

                        // Encrypt the TaskObject with the task result and send it to the server.
                        String encryptedTaskObject = encryptMessage(taskObj, sessionKey);

                        outputStream.writeObject(encryptedTaskObject);
                        outputStream.flush();

                        // Update the progress board with task completion messages.
                        progressBoardTA.append("The Task encrypted String: " + encryptedTaskObject + "\r\n");
                        progressBoardTA.append("The Task (" + taskName + ") is done.\r\n");
                    } else {
                        // If the task result is already computed, receive and display the credit.
                        progressBoardTA.append("The received credit for (" + taskName + ") is " + taskObj.getCredit() + ".\r\n");
                        progressBoardTA.append("----------------------------------------------------\r\n");
                        break; // Exit the loop as the task is completed.
                    }
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_calculateBtnActionPerformed

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        try {
            // Encrypt the task list using the session key and send it to the server.
            String encryptedTaskList = encryptMessage(taskList, sessionKey);
            this.outputStream.writeObject(encryptedTaskList);

            // Receive an encrypted task list from the server and decrypt it using the session key.
            encryptedTaskList = (String) inputStream.readObject();
            taskList = (TaskList) decryptMessage(encryptedTaskList, sessionKey);
            this.progressBoardTA.append("The TaskList encrypted String: " + encryptedTaskList + "\r\n");

            // Clear the task list combo box and enable it.
            taskListComboBox.removeAllItems();
            taskListComboBox.setEnabled(true);

            // Populate the task list combo box with available task names from the received task list.
            for (String task : taskList.getAvailableTasks()) {
                taskListComboBox.addItem(task);
            }

            // Enable the "Calculate" button to allow the user to select and execute tasks.
            calculateBtn.setEnabled(true);
        } catch (IOException | ClassNotFoundException ex) {
        }
    }//GEN-LAST:event_refreshBtnActionPerformed

    private void authenticateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_authenticateBtnActionPerformed
        try {
            // Display progress information about mutual authentication.
            this.progressBoardTA.setText(this.progressBoardTA.getText() + "The mutual authentication is progressing.");

            // Generate a random verification string for mutual authentication.
            String verificationString = SecurityUtil.RandomAlphaNumericString(128);

            // Encrypt the verification string with CENTRE's public key.
            String cipherVerificationString = encrypt(verificationString, "CENTRE-pub.ser");

            // Display the verification string in both plain and cipher text.
            this.progressBoardTA.setText(this.progressBoardTA.getText() + "\r\nThe verification string in plain text: " + verificationString);
            this.progressBoardTA.setText(this.progressBoardTA.getText() + "\r\nThe verification string in cipher text: " + cipherVerificationString);

            // Create a CSAuthenticator object with user information and send it to the server.
            CSAuthenticator authenticator = new CSAuthenticator(
                    usernameTextField.getText(),
                    encrypt(usernameTextField.getText(), usernameTextField.getText() + "-pri.ser"),
                    cipherVerificationString,
                    null
            );
            outputStream.writeObject(authenticator);
            outputStream.flush();

            // Receive a response from the server.
            Object input = inputStream.readObject();
            if (input instanceof String) {
                // If the response is a string, display it in the progress board.
                this.progressBoardTA.setText(this.progressBoardTA.getText() + "\r\n" + input);
            } else if (input instanceof CSAuthenticator) {
                // If the response is a CSAuthenticator object, proceed with mutual authentication.
                CSAuthenticator masterAuthenticator = (CSAuthenticator) input;

                // Retrieve the user's private key.
                HashMap keys = SecurityUtil.ReadinKeys(authenticator.getPlainUserName() + "-pri.ser");

                // Decrypt the session key using the user's private key.
                sessionKey = SecurityUtil.DecryptSessionKey(masterAuthenticator.getSessionKey(), (PrivateKey) keys.get(authenticator.getPlainUserName()));

                // Decrypt the master's name and the verification string using the session key.
                String decryptMasterName = decrypt(masterAuthenticator.getCipherUserName(), "CENTRE-pub.ser");
                String decryptVerificationString = (String) SecurityUtil.SymDecryptObj(masterAuthenticator.getVerficationString(), sessionKey);

                // Check if the decrypted master name is "CENTRE" and the verification strings match.
                if ("CENTRE".equals(decryptMasterName) && verificationString.equals(decryptVerificationString)) {
                    // Display the session key in both cipher and plain text.
                    this.progressBoardTA.setText(this.progressBoardTA.getText() + "\r\n The session key in cipher text: " + masterAuthenticator.getSessionKey());
                    this.progressBoardTA.setText(this.progressBoardTA.getText() + "\r\n The session key in plain text: " + SecurityUtil.keytoB64String(sessionKey));
                    this.progressBoardTA.setText(this.progressBoardTA.getText() + "\r\n The mututal authentication is done!\r\n");

                    // Disable the authentication button and enable the refresh button.
                    authenticateBtn.setEnabled(false);
                    refreshBtn.setEnabled(true);
                } else {
                    // Display a message indicating that mutual authentication failed.
                    this.progressBoardTA.setText(this.progressBoardTA.getText() + "\r\n The mututal authentication failed!\r\n");
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
        }
    }//GEN-LAST:event_authenticateBtnActionPerformed

    private void connectToMaster() {
        try {
            socket = new Socket(masterHost, masterPort);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            // Handling Views
            this.progressBoardTA.setText("TCP connection to the server is done.\r\n");
            clearBoardBtn.setEnabled(true);
            authenticateBtn.setEnabled(true);
            setBtn.setEnabled(false);
        } catch (IOException ex) {
            this.progressBoardTA.setText("Could not connect to the " + masterHost + ":" + masterPort);
        }
    }

    /**
     * Retrieves the selected task's ID from the taskListComboBox.
     *
     * @return The selected task's ID (index).
     */
    private Integer getSelectedTaskID() {
        return taskListComboBox.getSelectedIndex();
    }

    /**
     * Encrypts a username using the specified key file.
     *
     * @param username The username to be encrypted.
     * @param keyFile The path to the key file for encryption.
     * @return The encrypted username in Base64-encoded format.
     */
    private String encrypt(String username, String keyFile) {
        HashMap keys = SecurityUtil.ReadinKeys(keyFile);
        String encryptedRandomBase64String;
        if (keyFile.contains("pri")) {
            encryptedRandomBase64String = SecurityUtil.asyEncrypt(username, (PrivateKey) keys.get(keyFile.split("-")[0]));
        } else {
            encryptedRandomBase64String = SecurityUtil.asyEncrypt(username, (PublicKey) keys.get(keyFile.split("-")[0]));
        }
        return encryptedRandomBase64String;
    }

    /**
     * Decrypts a cipher text using the specified key file.
     *
     * @param cipherText The cipher text to be decrypted.
     * @param keyFile The path to the key file for decryption.
     * @return The decrypted text.
     */
    private String decrypt(String cipherText, String keyFile) {
        HashMap keys = SecurityUtil.ReadinKeys(keyFile);
        String decryptedRandomBase64String;
        if (keyFile.contains("pri")) {
            decryptedRandomBase64String = SecurityUtil.asyDecrypt(cipherText, (PrivateKey) keys.get(keyFile.split("-")[0]));
        } else {
            decryptedRandomBase64String = SecurityUtil.asyDecrypt(cipherText, (PublicKey) keys.get(keyFile.split("-")[0]));
        }
        return decryptedRandomBase64String;
    }

    /**
     * Encrypts an object message using the provided session key.
     *
     * @param message The object message to be encrypted.
     * @param sessionKey The secret key for symmetric encryption.
     * @return The encrypted message in Base64-encoded format.
     */
    private String encryptMessage(Object message, SecretKey sessionKey) {
        return SecurityUtil.SymEncryptObj(message, sessionKey);
    }

    /**
     * Decrypts a message using the provided session key.
     *
     * @param message The encrypted message to be decrypted.
     * @param sessionKey The secret key for symmetric decryption.
     * @return The decrypted object.
     */
    private Object decryptMessage(String message, SecretKey sessionKey) {
        byte[] ObjectBytes = SecurityUtil.SymDecrypt((String) message, sessionKey);
        Object obj = SecurityUtil.convertBytesToObject(ObjectBytes);
        return obj;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Worker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Worker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Worker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Worker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Worker().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton authenticateBtn;
    private javax.swing.JButton calculateBtn;
    private javax.swing.JButton clearBoardBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField masterHostTextField;
    private javax.swing.JTextField masterPortTextField;
    private javax.swing.JTextArea progressBoardTA;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JButton setBtn;
    private javax.swing.JComboBox<String> taskListComboBox;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
