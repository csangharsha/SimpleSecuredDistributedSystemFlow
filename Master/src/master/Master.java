package master;

import contract.CFile;
import contract.CSAuthenticator;
import contract.CalculateGCD;
import contract.CalculatePi;
import contract.CalculatePrimes;
import contract.Task;
import contract.TaskList;
import contract.TaskObject;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.crypto.SecretKey;
import security.SecurityUtil;

/**
 * The `Master` class represents a server that coordinates and manages
 * distributed tasks. It listens for worker connections, handles task
 * distribution, and performs secure communication with workers.
 *
 * <p>
 * The class provides methods for accepting worker connections, handling worker
 * communication, and managing tasks.
 *
 */
public class Master {

    private ServerSocket master;
    private final int port;

    /**
     * Constructs a new `Master` instance with the specified port number.
     *
     * @param port The port number on which the master server will listen for
     * worker connections.
     */
    public Master(int port) {
        this.port = port;
    }

    /**
     * Starts the master server, listens for worker connections, and handles
     * worker requests.
     */
    public void run() {
        try {
            master = new ServerSocket(port);
            System.out.println("------------------------------");
            System.out.println("The Master is listening at port " + port + " for object transfer...");
            System.out.println("------------------------------");

            acceptWorker();
        } catch (IOException i) {
            System.out.println(i.getMessage());
        }
    }

    private void acceptWorker() {
        TaskList taskList = new TaskList();
        taskList.setAvailableTasks(
                new String[]{
                    "Calculate Pi to 50 decimal digits",
                    "Calculate Prime from 1-70",
                    "Calculate GCD of 128 and 76",
                    "Calculate Pi to 70 decimal digits",
                    "Calculate Prime from 1-100",
                    "Calculate GCD of 252 and 24"
                }
        );
        taskList.setTaskClassName(
                new String[]{
                    "CalculatePi.class",
                    "CalculatePrimes.class",
                    "CalculateGCD.class",
                    "CalculatePi.class",
                    "CalculatePrimes.class",
                    "CalculateGCD.class"
                }
        );
        while (true) {
            try {
                Socket socket = master.accept();
                Thread workerThread = new Thread(new WorkerHandler(socket, taskList));
                workerThread.start();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Master master = new Master(5001);
        master.run();
    }

    /**
     * The `WorkerHandler` class represents a worker request handler responsible
     * for managing worker communication with the master server. It handles
     * tasks such as secure authentication, task distribution, and result
     * retrieval.
     *
     * <p>
     * The class provides methods to handle worker authentication, task
     * distribution, result retrieval, and secure communication.
     *
     */
    private class WorkerHandler implements Runnable {

        private final Socket workerSocket;
        private final TaskList taskList;

        private ObjectInputStream inputStream;
        private ObjectOutputStream outputStream;
        private String CONTRACT_PACKAGE_LOCATION = "./build/classes/contract";
//                "./contract";
//                "./build/classes/contract";

        private SecretKey sessionKey;

        WorkerHandler(Socket workerSocket, TaskList taskList) {
            this.workerSocket = workerSocket;
            this.taskList = taskList;
            try {
                inputStream = new ObjectInputStream(workerSocket.getInputStream());
                outputStream = new ObjectOutputStream(workerSocket.getOutputStream());
            } catch (IOException ex) {
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Object message = inputStream.readObject();
                    if (message instanceof CSAuthenticator) {
                        // If the received message is an instance of CSAuthenticator, 
                        // perform the following actions for mutual authentication.
                        CSAuthenticator authenticator = (CSAuthenticator) message;

                        // Decrypt the ciphered username using the public key of the user.
                        String decryptedUsername = decrypt(authenticator.getCipherUserName(), authenticator.getPlainUserName() + "-pub.ser");

                        // Decrypt the verification string using the private key of the server.
                        String decryptedVerificationString = decrypt(authenticator.getVerficationString(), "CENTRE-pri.ser");

                        // Check if the decrypted username matches the plain username in the authenticator.
                        if (authenticator.getPlainUserName().equals(decryptedUsername)) {

                            // Mutual authentication is in progress.
                            System.out.println("The mutual authentication of user " + decryptedUsername + "is progressing!");
                            System.out.println("The The verification string in cipher text: " + authenticator.getVerficationString());
                            System.out.println("The The verification string in plain text: " + decryptedVerificationString);

                            // Retrieve the public keys for the user.
                            HashMap keys = SecurityUtil.ReadinKeys(decryptedUsername + "-pub.ser");
                            // Generate a session key for secure communication.

                            sessionKey = SecurityUtil.SecretKeyGen();

                            // Encrypt the session key using the user's public key.
                            String cipheredSessionKey = SecurityUtil.EncryptSessionKey(sessionKey, (PublicKey) keys.get(decryptedUsername));

                            // Create a master authenticator for the server.
                            CSAuthenticator masterAuthenticator = new CSAuthenticator();
                            masterAuthenticator.setPlainUserName("CENTRE");

                            // Encrypt the server's username using its private key.
                            masterAuthenticator.setCipherUserName(encrypt(masterAuthenticator.getPlainUserName(), "CENTRE-pri.ser"));

                            // Encrypt the verification string using the generated session key.
                            masterAuthenticator.setVerficationString(SecurityUtil.SymEncryptObj(decryptedVerificationString, sessionKey));

                            // Set the session key for the master authenticator.
                            masterAuthenticator.setSessionKey(cipheredSessionKey);

                            System.out.println("The session Key in plain text: " + SecurityUtil.keytoB64String(sessionKey));
                            System.out.println("The session Key in cipher text: " + masterAuthenticator.getSessionKey());
                            System.out.println("The mutual Authentication is done!");
                            System.out.println("----------------------------------------------");

                            // Send the master authenticator to the client for mutual authentication.
                            outputStream.writeObject(masterAuthenticator);
                            outputStream.flush();
                        } else {
                            // If the decrypted username doesn't match the plain username,
                            // respond with an "Invalid Username" message to the client.
                            outputStream.writeChars("Invalid Username");
                            outputStream.flush();
                        }
                    } else if (message instanceof String) {
                        // If the received message is a String, handle various actions based on its content.

                        // Decrypt the received message using the session key.
                        Object input = decryptMessage((String) message, sessionKey);
                        if (input instanceof TaskList) {
                            // If the decrypted input is a TaskList, provide the list of available compute-tasks to the worker.
                            TaskList receivedTaskList = (TaskList) input;
                            receivedTaskList.setAvailableTasks(taskList.getAvailableTasks());
                            receivedTaskList.setTaskClassName(taskList.getTaskClassName());

                            // Encrypt the TaskList and send it to the worker.
                            String encryptedTaskList = encryptMessage(receivedTaskList, sessionKey);
                            outputStream.writeObject(encryptedTaskList);
                            System.out.println("The TaskList encrypted String: " + encryptedTaskList);
                            System.out.println("The list of available compute-tasks has been transferred to a worker.");
                        } else if (input instanceof String) {
                            // If the decrypted input is a String, it represents the name of a task file to be transferred.
                            String msg = (String) input;
                            File file = new File(CONTRACT_PACKAGE_LOCATION, msg);
                            // Read the file content and create a CFile object.
                            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                            DataInputStream dis = new DataInputStream(bis);
                            byte[] mybytearray = new byte[(int) file.length()];
                            dis.readFully(mybytearray, 0, mybytearray.length);
                            CFile cfile = new CFile(file.getPath(), mybytearray);

                            // Encrypt the CFile and send it to the worker.
                            String encryptedCFile = encryptMessage(cfile, sessionKey);
                            outputStream.writeObject(encryptedCFile);
                            outputStream.flush();

                            System.out.println("The CFile encrypted String: " + encryptedCFile);
                            System.out.println("The task: " + msg + " has been transferred to a worker.");
                        } else if (input instanceof TaskObject) {
                            // If the decrypted input is a TaskObject, handle task execution and response.
                            TaskObject taskObj = (TaskObject) input;
                            if (taskObj.getTObject() == null) {
                                // If the task object does not contain a task instance, it needs to be initialized.

                                // Get the task based on the task ID.
                                Task task = getTask(taskObj.getTaskID());
                                taskObj.setTObject(task);

                                // Encrypt the initialized task object and send it to the worker.
                                String encryptedTaskObject = encryptMessage(taskObj, sessionKey);

                                outputStream.writeObject(encryptedTaskObject);
                                outputStream.flush();
                            } else {
                                // If the task object already contains a task instance, it represents a completed task.

                                System.out.println("The Task encrypted String: " + message);

                                System.out.println("The task: " + taskList.getTaskClassName()[taskObj.getTaskID()] + " has been performed by the worker, the result is: " + getFormattedResultToPrint(taskObj));
                                // Determine and assign credit to the worker.
                                taskObj.setCredit(getCredit(taskObj.getTaskID()));
                                System.out.println("Award a credit of " + taskObj.getCredit() + " to a worker.");

                                // Encrypt the task object with the credit information and send it to the worker.
                                String encryptedTaskObject = encryptMessage(taskObj, sessionKey);

                                outputStream.writeObject(encryptedTaskObject);
                                outputStream.flush();
                                System.out.println("----------------------------------------------");
                            }
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
            } finally {
                try {
                    inputStream.close();
                    outputStream.close();
                    workerSocket.close();
                } catch (IOException e) {
                }
            }
        }

        private String decrypt(String cipherText, String keyFile) {
            HashMap keys = SecurityUtil.ReadinKeys(keyFile);
            String decryptedRandomBase64String = null;
            if (keyFile.contains("pri")) {
                decryptedRandomBase64String = SecurityUtil.asyDecrypt(cipherText, (PrivateKey) keys.get(keyFile.split("-")[0]));
            } else {
                decryptedRandomBase64String = SecurityUtil.asyDecrypt(cipherText, (PublicKey) keys.get(keyFile.split("-")[0]));
            }
            return decryptedRandomBase64String;
        }

        private String encrypt(String username, String keyFile) {
            HashMap keys = SecurityUtil.ReadinKeys(keyFile);
            String encryptedRandomBase64String = null;
            if (keyFile.contains("pri")) {
                encryptedRandomBase64String = SecurityUtil.asyEncrypt(username, (PrivateKey) keys.get(keyFile.split("-")[0]));
            } else {
                encryptedRandomBase64String = SecurityUtil.asyEncrypt(username, (PublicKey) keys.get(keyFile.split("-")[0]));
            }
            return encryptedRandomBase64String;
        }

        private Task getTask(Integer taskID) {
            return switch (taskID + 1) {
                case 1 ->
                    new CalculatePi(50);
                case 2 ->
                    new CalculatePrimes(1, 70);
                case 3 ->
                    new CalculateGCD(128, 76);
                case 4 ->
                    new CalculatePi(70);
                case 5 ->
                    new CalculatePrimes(1, 100);
                case 6 ->
                    new CalculateGCD(252, 24);
                default ->
                    null;
            };
        }

        private String getFormattedResultToPrint(TaskObject taskObj) {
            if (taskObj.getTObject() instanceof CalculatePi) {
                return taskObj.getTObject().getResult().toString();
            } else if (taskObj.getTObject() instanceof CalculatePrimes) {
                List<Integer> result = (ArrayList<Integer>) taskObj.getTObject().getResult();
                StringBuilder sb = new StringBuilder(String.format("The number of primes is: %d, and they are: ", result.size()));
                for (int i = 0; i < result.size(); i++) {
                    sb.append(result.get(i));
                    if (i < result.size() - 1) {
                        sb.append(", ");
                    } else {
                        sb.append(".");
                    }
                }
                return sb.toString();
            } else if (taskObj.getTObject() instanceof CalculateGCD) {
                CalculateGCD gcd = (CalculateGCD) taskObj.getTObject();
                return String.format("The Greatest Common Divisor of %d and %d is %d", gcd.getFirst(), gcd.getSecond(), gcd.getResult());
            }
            return "";
        }

        private Integer getCredit(Integer taskID) {
            return switch (taskID + 1) {
                case 1 ->
                    25;
                case 2 ->
                    30;
                case 3 ->
                    35;
                case 4 ->
                    40;
                case 5 ->
                    45;
                case 6 ->
                    50;
                default ->
                    null;
            };
        }

        private Object decryptMessage(String message, SecretKey sessionKey) {
            byte[] ObjectBytes = SecurityUtil.SymDecrypt((String) message, sessionKey);
            Object obj = SecurityUtil.convertBytesToObject(ObjectBytes);
            return obj;
        }

        private String encryptMessage(Object message, SecretKey sessionKey) {
            return SecurityUtil.SymEncryptObj(message, sessionKey);
        }
    }
}
