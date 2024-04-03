
**SECURED MASTER/WORKER FRAMEWORK**


1. Introduction to the Secured Master/Worker Framework User Manual

Welcome to the User Guide for the Secured Master/Worker Framework. This manual has been crafted to assist you in gaining a deep understanding of and proficiently utilizing the functionalities of this robust and secure software system. Whether you are a software developer seeking to establish a runtime environment or an individual eager to harness the potential of this framework, this guide will furnish you with the essential insights.


## 1.1 About the Secured Master/Worker Framework

The Secured Master/Worker Framework represents a specialized initiative in software development, driven by the goal of establishing secure communication pathways among a range of participants, with a primary focus on Workers and the Master entity. At its core, its fundamental objective is to facilitate smooth and secure sharing of tasks and data within a designated network or system. This framework has undergone meticulous design to accommodate a variety of applications, particularly those that necessitate rigorous security protocols.

In practical terms, think of this framework as a secure link, guaranteeing that communication remains private and dependable between Workers, responsible for specific tasks, and the Master, responsible for overseeing and managing these tasks. By creating a secure space for the exchange of information, this framework finds applicability across a broad array of industries and scenarios, especially those where the preservation of data confidentiality and integrity is of paramount importance. Whether in research, finance, healthcare, or other sectors, the framework's adaptability makes it an invaluable asset, reinforcing the security of existing systems and ensuring the protection of sensitive data.




## 1.2 Key Features of the Secured Master/Worker Framework



* Mutual Authentication: This means that the framework makes sure that both the Workers and the Master can securely verify each other. It's like a trust handshake for communication.
* Encrypted Communication: All the talking and sharing of information that happens in the framework is like secret codes. It's like having a secret language so that no one else can understand what's being said.
* Easy to Use: Even though it's super secure, the framework is designed to be easy for people to use. It's like having clear and simple instructions for talking to each other.
* Versatile: You can use this framework in lots of different situations where you need to share tasks and data securely. It's like a versatile tool that can be used in many ways.

**Note:**

**If you are a Developer, turn to Section 2 [Instruction for Developers].**

**If you are an End User, proceed to Section 3 [End User Instruction].**





2. Instruction for Developers


## 2.1 Project Structure

Secured Master/Worker Framework has the Master and Worker packages. Both Master and worker has 3 sub packages (contract, master, and security). Master class consist of the main method in master package and likewise for Worker has main method in worker package. Further we will discuss the key functionalities of the of each package and sub packages.


## 2.1.1 Master package

The provided code is a Java class named Master within the "master" package. This class serves as the core component of a server responsible for coordinating and managing distributed tasks. Here's a summary of its key functionalities:

**Server Setup**

Method: run()

This method starts the master server, listens for worker connections, and handles worker requests.

**Task Management**

Methods: acceptWorker()

This method maintains a list of available tasks and their corresponding class names. It also continuously accepts incoming worker connections and spawns new threads (WorkerHandler) to handle worker communication.

**Worker Handling**

Method: WorkerHandler(Socket workerSocket, TaskList taskList)

This nested class represents a worker request handler responsible for managing worker communication with the master server. It handles tasks such as secure authentication, task distribution, and result retrieval.

**Secure Communication:**

Methods: decrypt(String cipherText, String keyFile) (for decryption), encrypt(String username, String keyFile) (for encryption), decryptMessage(String message, SecretKey sessionKey) (for decrypting messages), encryptMessage(Object message, SecretKey sessionKey) (for encrypting messages)

These methods are responsible for secure communication, including encryption and decryption using keys.

**Task Execution:**

Methods: getTask(Integer taskID), getFormattedResultToPrint(TaskObject taskObj), getCredit(Integer taskID)

These methods handle task execution, including initializing tasks, formatting results for printing, and assigning credits to workers.

**Error Handling:**

The code includes general error handling to manage exceptions gracefully during communication or execution.

These methods collectively enable the "Master" class to perform its key functionalities, which include setting up the server, managing tasks, handling worker communication, ensuring secure communication, executing tasks, and handling errors**.**


## 2.1.2 SecurityUtil Package(master)

**Random Alpha-Numeric String Generation**

Method: RandomAlphaNumericString(int n)

This method generates a random alpha-numeric string of the specified length.

**Session Key Encryption and Decryption**

Methods: DecryptSessionKey(String CipherSessionKeyString, PrivateKey prikey) (for decryption), EncryptSessionKey(SecretKey SessionKey, PublicKey pubkey) (for encryption)

These methods handle the encryption and decryption of session keys using asymmetric encryption.

**Object Encryption and Decryption**

Methods: SymEncryptObj(Object obj, SecretKey sessionKey) (for encryption), SymDecryptObj(String ObjectString, SecretKey sessionKey) (for decryption)

These methods are responsible for encrypting and decrypting objects using symmetric encryption.


**Object Serialization and Deserialization**

Methods: convertObjectToBytes(Object obj) (for object to byte array conversion), convertBytesToObject(byte[] bytes) (for byte array to object conversion)

These methods handle the serialization and deserialization of objects.


## 2.1.3 Contract Package(master)

The "Contract" package encompasses several Java classes with distinct functionalities: "CalculatePi" for approximating the mathematical constant Pi, "CalculateGCD" to compute the Greatest Common Divisor of integers, "CalculatePrimes" for generating and handling prime numbers, "Cfile," whose purpose isn't explicitly clear from its name, "CSA Authenticator" for system and user authentication, "TaskList" to manage collections of tasks, and "TaskObject" to represent individual tasks. These classes collectively address mathematical computations, data handling, security/authentication, and task management within the project, forming a diverse set of tools and functionalities.


## 2.2.1 Worker package

Similarly in the Worker package, It has Cfile, CSA Authenticator , task list, task object, security and worker. Worker is the main method

**Connecting to a Master Server**

**Method: connectToMaster()**

This method establishes a connection with the master server.

Authentication:

**Method: authenticateBtnActionPerformed(ActionEvent evt)**

This method initiates mutual authentication with the server using encryption techniques and a verification string.

Task Execution:

**Method: calculateBtnActionPerformed(ActionEvent evt)**

This method handles the execution of selected tasks and manages task-related actions.

Updating Progress:

**Methods: clearBoardBtnActionPerformed(ActionEvent evt)**

This method Clears the progress board 

**Task List Refresh:**

**Method: refreshBtnActionPerformed(ActionEvent evt)**

This method refreshes the list of available tasks from the server.

Handling User Interface:

**Methods:initComponents()**

UI component initialization, setBtnActionPerformed(ActionEvent evt) (Handles connection setup), and other event listener methods.

Security:

Methods: encrypt(String username, String keyFile) (Encrypts data), decrypt(String cipherText, String keyFile) (Decrypts data), encryptMessage(Object message, SecretKey sessionKey) (Encrypts messages for secure communication), decryptMessage(String message, SecretKey sessionKey) (Decrypts messages for secure communication).

**Resource Cleanup:**

Methods: The windowClosing(WindowEvent e) method within the constructor ensures that network resources are properly closed when the program is terminated.

These methods collectively enable the "Worker" class to perform its key functionalities, which include connecting to a master server, authenticating with it, executing tasks, updating progress, refreshing task lists, handling the user interface, ensuring security, and managing resource cleanup.


## 2.2.2 SecurityUtil package(worker)

The provided code defines a SecurityUtil class, which contains various utility methods for security-related operations. Here are the key functionalities and methods in this class:

* RandomAlphaNumericString(int n):

Generates a random alpha-numeric string of the specified length.



* DecryptSessionKey(String CipherSessionKeyString, PrivateKey prikey):

Decrypts a session key using a private key.



* EncryptSessionKey(SecretKey SessionKey, PublicKey pubkey):

Encrypts a session key using a public key.



* SymEncryptObj(Object obj, SecretKey sessionKey):

Encrypts an object using a symmetric key.



* SymDecryptObj(String ObjectString, SecretKey sessionKey):

Decrypts a ciphered object string using a symmetric key.



* convertObjectToBytes(Object obj):

Converts an object to a byte array.



* convertBytesToObject(byte[] bytes):

Converts a byte array to an object.



* SecretKeyGen():

Generates a new symmetric key.



* ReadinKeys(String Keyfile):

Reads keys from a file and returns them as a HashMap.



* SymEncrypt(byte[] message, Key sk):

Encrypts a byte array message using a symmetric key.



* SymDecrypt(String message, Key sk):

Decrypts a ciphered message string using a symmetric key.



* asyEncrypt(String message, Key pk):

Encrypts a string message using an asymmetric key (public key).



* asyDecrypt(String message, Key prik):

Decrypts a ciphered message string using an asymmetric key (private key).



* B64StringTokey(String kString):

    Converts a base64-encoded string to a SecretKey.

* keytoB64String(SecretKey sKey):

    Converts a SecretKey to a base64-encoded string.

* pubKeytoB64String(PublicKey pKey):

    Converts a PublicKey to a base64-encoded string.

* priKeytoB64String(PrivateKey priKey):

    Converts a PrivateKey to a base64-encoded string.


These methods provide functionalities for encryption, decryption, key management, object serialization, and conversion between different key types. The class seems to be designed for use in secure communication and data protection applications.


## 2.2.3 Contract Package(worker)

The "Worker" package consists of various Java classes designed to fulfill distinct roles and responsibilities, excluding any calculation-related classes found in other packages. These classes cater to diverse functionalities within the project, spanning data processing, system integration, and task execution. While the package doesn't contain calculation-specific classes, it plays a pivotal role in the broader ecosystem, contributing to data handling, communication, and task execution mechanisms essential for the project's overall functionality.



3. The end user instruction

**System Requirements:**



* Ensure that computers have the Java Development Kit (JDK) installed, with a version of 17 or higher.
* Ensure that there is network connectivity between the Master and Workers and they are on the same local network or connected to the internet.
    1. Compiling and Installation 

**3.1.1 Download the Framework**:


    -Download and open the zip file “Runtime” and then extract all the files.


    -Save it to the location you preferred (both RuntimeMaster and RuntimeWorker should be in same path folder)


        1. **Compile the Code**:   

    (This is optional as the files are already compiled and ready to use, you can skip to 1.3 if you do not want to compile)

*   Make sure all the files are present as per the above screenshots.
* Open the Command Prompt (Windows) or Terminal (Mac or Linux) on system.
* Navigate to the folder containing the Runtime framework files using the cd command.

    -**cd path_name**

* Compile the code using the command-**javac Contract/*.java Master/*.java**

    **- javac Contract/*.java Worker/*.java**






        2. **Executing Master:**
* Open two Command Prompt, one for master and other for worker.
* Execute Master(First Master should be run as it is a server)
* Run the Master using the command:
* java -cp . master.Master




        3. **Executing Worker:**
* Open a separate Command Prompt or Terminal on each computer you want to use as a Worker.
* Run the Worker using the command:
* java -cp . worker.Worker





3.1.5  When both java classes is run, GUI pop window like below should appear, where connection needs to be set first. Once it is done, you will get a message in the Worker window as “TCP connection to the server is done.”




3.1.6 After clicking on authentication, the mutual authentication between master and server gets started when the verification and session key is retrieved by the worker.



3.1.7 Retrieving the task list from the master, the encrypted task list should show in the worker console


3.1.8 You can select any of the task listed task to perform in master,The TaskList encrypted String is a secure, encrypted representation of a list of tasks.The list of available compute-tasks has been transferred to a worker signifies successful transmission of tasks to a worker.The CFile encrypted String likely contains encrypted information about a file or resource.The task: CalculatePi.class has been transferred to a worker indicates the transfer of a specific task, CalculatePi.class to a worker for processing. These actions are part of a distributed computing system.


# Framework is protected against masquerade attacks

A masquerade attack is when a cyber attacker impersonates a legitimate user or system to gain unauthorized access to computers, networks, or data. They use various techniques to mimic real users, evade security measures, and steal or manipulate information. Preventing masquerade attacks involves strong authentication, access controls, monitoring, and user education.

The mutual authentication procedure you described enhances the framework's security against masquerade attacks. It ensures that both the Worker and the Master can trust each other's identities before exchanging sensitive information. Here's how it helps protect against masquerade attacks:



1. Worker Authentication: The Worker sends an authenticator to the Master, which includes encrypted information about its identity and a verification string. The Master decrypts and verifies the Worker's identity based on the received information. This step ensures that the Worker is not impersonating another user.
2. Master Authentication: The Master decrypts the verification string using its private key and creates a session key for secure communication. This ensures that the Master is the legitimate CENTRE entity. It prevents attackers from posing as the Master.
3. Session Key: The creation and exchange of a session key further enhance security. The Worker and Master both possess this key, which is used for encrypting and decrypting future communications. This session key ensures confidentiality and integrity of data exchanged during the session.
4. Verification String Comparison: Both the Master and Worker compare the decrypted verification string. Successful comparisons in both directions confirm the authenticity of each party. This step prevents masqueraders from tampering with or intercepting the verification string. 
