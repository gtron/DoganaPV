package com.gsoft.pt_movimentazioni.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.gtsoft.utils.common.ConfigManager;

public class FileBasedCacher  {

	private static MessageDigest algorithm = null ;

	private static String CACHE_PATH = "WEB-INF/qcache";

	public static String getCachedKey(Object key_seed) {

		String key = key_seed.getClass() + "_" + key_seed.toString();

		if ( algorithm == null ) {
			try{
				algorithm = MessageDigest.getInstance("MD5");
			}catch(NoSuchAlgorithmException nsae){
				nsae.printStackTrace();
				return key;
			}
		}
		byte testo[]=key.getBytes();

		algorithm.reset();
		algorithm.update(testo);
		byte messageDigest[] = algorithm.digest();

		StringBuffer hexString = new StringBuffer();
		for (byte element : messageDigest) {
			hexString.append(Integer.toHexString(0xFF & element));
		}
		return hexString.toString();
	}

	public static Object get(String key) {

		String root = ConfigManager.getWebappRoot() ;

		File dirCache = new File(root + CACHE_PATH);

		if ( ! dirCache.exists() )
			return null;

		String filename = root + CACHE_PATH + File.separator + key + ".data";

		Object out = null ;
		try {
			File f = new File(filename);
			if ( ! f.exists() )
				return null;

			FileInputStream fileIn = new FileInputStream(f);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			out = in.readObject();
			in.close();
			fileIn.close();
		} catch(IOException i) {
			System.out.println("CACHE Error : " + i.getMessage() );
			// i.printStackTrace();
		} catch(ClassNotFoundException c) {
			System.out.println("Error Class not found");
			// c.printStackTrace();
		}
		return out;

	}

	public static void set(String key, Object res) throws IOException {
		String root = ConfigManager.getWebappRoot() ;

		File dirCache = new File( root + CACHE_PATH );

		if ( ! dirCache.exists() ) {
			dirCache.mkdir();
		}

		String filename = root + CACHE_PATH + File.separator + key + ".data";

		// Serialize to a file
		ObjectOutput out = new ObjectOutputStream(new FileOutputStream(filename));
		out.writeObject(res);
		out.close();

	}




}