package org.CadeBeckers.Encryption;

/**
 * 
 * @author Cade Beckers | 7/25/2024
 * Revised: 9/11/2024
 */
public class Decrypt {
    // Decryption key (first byte)
    private char[] bitEncKey;

    public byte[] DecryptBytes(byte[] byteArray) {
        this.bitEncKey = byteToCharArray(byteArray[0]);

        // decrypt each byte
        for (int i = 1; i < byteArray.length; i++)
            byteArray[i] = keyEncryptByte(byteArray[i]);

        // remove the first byte of the file (added encryption key)
        byteArray = removeByte(byteArray);
        
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
    public byte[] removeByte(byte[] arr) {
        byte[] newArr = new byte[arr.length - 1];
        System.arraycopy(arr, 1, newArr, 0, newArr.length);
        return newArr;
    }
}
