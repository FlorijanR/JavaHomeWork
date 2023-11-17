package hr.fer.oprpp1.hw05.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class used for calculating sha-256 digest, encrypting
 * and decrypting files.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class Crypto {

	/**
	 * Runs the program.
	 * 
	 * @param args that set the program function and
	 * 		give path to files
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		if (args[0].equals("checksha")) {
			System.out.println("Please provide expected sha-256 digest for " + args[1] + ":");
			System.out.printf("> ");
			String expDigest = sc.nextLine();
			String actualDigest;
			byte[] hash = null;

			try {
				MessageDigest sha = MessageDigest.getInstance("SHA-256");

				Path p = Paths.get(args[1]);
				try (InputStream is = Files.newInputStream(p)) {
					byte[] buff = new byte[4096];
					while (true) {
						int r = is.read(buff);
						if (r < 1)
							break;

						sha.update(buff, 0, r); // obradi samo buff[0] do buff[r-1]
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				hash = sha.digest();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

			actualDigest = Util.bytetohex(hash);

			if (actualDigest.equals(expDigest.toLowerCase())) {
				System.out.println("Digesting completed. Digest of " + args[1] + " matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of " + args[1]
						+ " does not match the expected digest. Digest was: " + actualDigest);
			}
		}

		if (args[0].equals("encrypt") || args[0].equals("decrypt")) {
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.printf("> ");
			String keyText = sc.nextLine();
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			System.out.printf("> ");
			String ivText = sc.nextLine();

			byte[] processed = null;

			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher = null;

			try {
				cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				e.printStackTrace();
			}
			try {
				cipher.init(args[0].equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}

			Path p = Paths.get(args[1]);
			Path outPath = Paths.get(args[2]);
			try (InputStream is = Files.newInputStream(p); OutputStream os = Files.newOutputStream(outPath)) {
				byte[] buff = new byte[4096];
				while (true) {
					int r = is.read(buff);
					if (r < 1) {
						try {
							processed = cipher.doFinal();
						} catch (IllegalBlockSizeException | BadPaddingException e) {
							e.printStackTrace();
						}
						os.write(processed);
						break;
					}

					processed = cipher.update(buff, 0, r); // obradi samo buff[0] do buff[r-1]
					os.write(processed);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			System.out.println((args[0].equals("encrypt") ? "Encryption" : "Decryption")
					+ " completed. Generated file " + args[2] + " based on file " + args[1] + ".");

		}

		sc.close();

	}

}





