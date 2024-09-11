package org.CadeBeckers.Encryption;

import java.util.Random;

/**
 * 
 * @author Cade Beckers | 7/25/2024
 * Revised: 9/11/2024
 */
public class Encrypt {
    // encryption key (random gen for each file)
    private char[] bitEncKey;
    public byte[] encryptBytes(byte[] byteArray) {
        Random random = new Random();
        this.bitEncKey = byteToCharArray((byte) (random.nextInt(256)));

        // encrypt each byte
        for (int i = 0; i < byteArray.length; i++)
            byteArray[i] = keyEncryptByte(byteArray[i]);

        byteArray = addByte(charArrayToByte(bitEncKey), byteArray);
        
        return byteArray;
    }

    public byte keyEncryptByte(byte input) {
        char[] bitInput = byteToCharArray(input);
        int len = bitEncKey.length;
        // bit swapping 
        for (int i = 0; i < len; i++) {
            if (bitEncKey[i] == '0')
                continue;
            if (bitInput[i] == '1')
                bitInput[i] = '0';
            else
                bitInput[i] = '1';

        }
        // char array to byte
        return charArrayToByte(bitInput);
    }

    public char[] byteToCharArray(byte input) {
        String strInput = String.format("%8s", Integer.toBinaryString(input & 0xFF)).replace(' ', '0');
        char[] output = strInput.toCharArray();
        return output;
    }

    public byte charArrayToByte(char[] input) {
        String strInput = new String(input);
        byte output = (byte) Integer.parseInt(strInput, 2);
        return output;
    }

    // add byte to start of array
    public byte[] addByte(byte toAdd, byte[] arr) {
        byte[] newArr = new byte[arr.length + 1];
        newArr[0] = toAdd;
        System.arraycopy(arr, 0, newArr, 1, arr.length);
        return newArr;
    }
}
