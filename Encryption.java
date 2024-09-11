package org.CadeBeckers.Encryption;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * @author Cade Beckers | 7/25/2024
 * Revised: 9/11/2024
 */
public class Encryption {
    public void main() throws IOException {
        String password = "password";
        String folder;

        // prompt users for a password
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter password: ");
            String inputPass = scanner.nextLine();

            if (!inputPass.equals(password)) {
                System.out.print("Incorrect password. Terminating.");
                System.exit(0);
            }
            // clear terminal  
            System.out.print("\033[H\033[2J");
            System.out.flush();

            // get directory and requested action 
            System.out.println("Correct password given.\nEnter directory:");
            folder = scanner.nextLine();
            System.out.println("\nEnter action\n0 -> Decrypt  |  1 -> Encrypt: ");
            String action = scanner.nextLine();
            // do action or terminate
            if ("0".equals(action) || "1".equals(action))
                crypt(strToInt(action), folder);
            System.out.println("\nUnrecognized action, terminating.");
            System.exit(0);
        }
    }
    public List openFiles(String folder) throws FileNotFoundException, IOException {
        List<String> files = getFiles(folder);
        return files;
    }

    // pull all files from a given directory and add them to a list
    public List<String> getFiles(String folder) {
        List files = new ArrayList();
        try (Stream<Path> filePathStream = Files.walk(Paths.get(folder))) {
            List<String> fileList = filePathStream
                    .map(Path::toString)
                    .collect(Collectors.toList());
            fileList.forEach(files::add);
        } catch (IOException e) {
        }
        return files;
    }

    // case 0 is decrypt | case 1 is encrypt
    public void crypt(int action, String folder) throws IOException {
        List<String> files = openFiles(folder);
        switch (action) {
            case 0 -> {
                for (int i = 1; i < files.size();i++) {
                    byte[] byteArray = (byte[]) Files.readAllBytes(Paths.get(files.get(i)));
                    Decrypt decryptor = new Decrypt();
                    byte[] DecryptedFile = decryptor.DecryptBytes(byteArray);
                    File file = new File(files.get(i));
                    try (OutputStream output = new FileOutputStream(file)) {
                        output.write(DecryptedFile);
                    }
                }
                System.out.println((files.size()-1)+" files affected.");
            }
            case 1 -> {
                for (int i = 1; i < files.size();i++) {
                    byte[] byteArray = (byte[]) Files.readAllBytes(Paths.get(files.get(i)));
                    Encrypt encryptor = new Encrypt();
                    byte[] EncryptedFile = encryptor.encryptBytes(byteArray);
                    File file = new File(files.get(i));
                    try (OutputStream output = new FileOutputStream(file)) {
                        output.write(EncryptedFile);
                    }
                }
                System.out.println((files.size()-1)+" files affected.");
            }
        }
        System.exit(0);
    }

    // string into int for action selection
    public int strToInt(String input) {
        if ("0".equals(input))
            return 0;
        return 1;
    }
}
