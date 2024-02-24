/*
 * FileManager.java
 *
 * Created on 10. duben 2002, 12:41
 */

package com.gtsoft.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import com.gtsoft.utils.common.FormattedDate;

/**
 *
 * @author  ghort
 * @version
 */
public class FileManager {

	public static void copy(java.io.File destination, java.io.File source) throws Exception {
		if (source.isDirectory()) {
			if (!destination.isDirectory())
				throw new Exception("Destination '"+destination.getName()+"' is not directory.");
			copyDirectory(destination,source);
		} else {
			if (destination.isDirectory()) {
				destination=new java.io.File(destination,source.getName());
			}
			copyFile(destination,source);
		}
	}

	protected static void copyDirectory(java.io.File destination, java.io.File source) throws Exception {
		java.io.File[] list=source.listFiles();
		for (File element : list) {
			java.io.File dest=new java.io.File(destination,element.getName());
			if (element.isDirectory()) {
				dest.mkdir();
				copyDirectory(dest,element);
			} else {
				copyFile(dest,element);
			}
		}
	}

	protected static void copyFile(java.io.File destination, java.io.File source) throws Exception {
		java.io.FileInputStream inStream=null;
		java.io.FileOutputStream outStream=null;
		try {
			inStream=new java.io.FileInputStream(source);
			outStream=new java.io.FileOutputStream(destination);

			int len;
			byte[] buf=new byte[2048];

			while ((len=inStream.read(buf))!=-1) {
				outStream.write(buf,0,len);
			}
		} catch (Exception e) {
			throw new Exception("Can't copy file "+source+" -> "+destination+".",e);
		} finally {
			try {
				if (inStream!=null) inStream.close();
				if (outStream!=null) outStream.close();
			} catch (Exception e) {
				throw new Exception("Can't close file "+source+" -> "+destination+".",e);
			}
		}
	}

	public static void copy(String input, String output) throws Exception{
		File in=new File(input);
		File out=new File(output);

		FileManager.copy(out,in);

	}

	public static void createDir( String dir ) {
		File directory = new File( dir );
		if ( ! directory.exists() ) {
			directory.mkdir();
			return;
		}
	}

	public static boolean existsDir( String dir ) {
		File directory = new File( dir );
		return directory.exists() ;
	}

	public static File createFile( String filename , boolean overwrite ) {
		File f = new File( filename );

		if ( overwrite && f.exists() ) {
			f.delete();
		}
		if ( ! f.exists() || overwrite ) {
			try {
				f.createNewFile() ;
				return f ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null ;
	}

	public static Vector<File> listFiles( String dir ) {
		Vector<File> list = new Vector<File>() ;
		File f =  new File ( dir ) ;

		if ( f.isDirectory() ) {
			File[] backupFiles = f.listFiles( new FileFilter() {
				public boolean accept(File f) {
					boolean b = f.getName().endsWith(".backup") ;
					return  b ;
				}
			});

			for (File backupFile : backupFiles) {
				list.add( backupFile) ;
			}
		}

		return list;
	}

	public static Vector<String> listDirs( String parent ) {
		Vector<String> list = new Vector<String>() ;
		File f =  new File ( parent ) ;

		if ( f.isDirectory() ) {
			File[] backupFiles = f.listFiles( new FileFilter() {
				public boolean accept(File f) {
					return ( f.isDirectory() &&
							! f.getName().equals(".") &&
							! f.getName().equals("..") ); }
			});

			for (File backupFile : backupFiles) {
				list.add( backupFile.getName()) ;
			}
		}

		return list;
	}


	public static HashMap<FormattedDate, HashMap<String, String>> listBackups( String dir ) {
		HashMap<FormattedDate, HashMap<String, String>> list = new HashMap<FormattedDate, HashMap<String, String>>() ;
		File f =  new File ( dir ) ;


		FormattedDate backupDay = null ;



		if ( f.isDirectory() ) {
			File[] backupFiles = f.listFiles(new FileFilter() {
				public boolean accept(File f) {
					return f.isFile() ; }
			});

			File curr = null ;
			HashMap<String, String> day = null ;
			String filename = null ;

			for (File backupFile : backupFiles) {

				curr = backupFile ;
				filename = curr.getName() ;

				try {
					backupDay = new FormattedDate( filename.split("_")[0] , "yyyy-MM-dd-HH-mm-ss" ) ;
				} catch (Exception e) {
					backupDay = new FormattedDate();
				}


				if ( list.containsKey(backupDay) ) {
					day = (HashMap<String, String>) list.get(backupDay) ;
				}
				else {
					day = new HashMap<String, String>(3);
					list.put( backupDay, day ) ;
				}


				if ( filename.endsWith(".backup")) {
					day.put("backup",filename);
				}
				else if ( filename.endsWith(".txt")) {

					String comment = " - " ;
					String line = null ;
					if ( curr.length() > 0 ) {
						BufferedReader fr = null;
						try {
							fr = new BufferedReader( new FileReader ( curr ) ) ;
							while ( ((line = fr.readLine()) != null) ) {
								comment += line;
							}
						}
						catch ( Exception e ) { comment = " N/A " ; }
						finally {
							if(fr != null)
								try {
									fr.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
						}
					}
					else {
						comment = filename.substring( filename.indexOf('_',20) +1  , filename.lastIndexOf('.')) ;
						comment = comment.replaceAll("_", " ");
					}
					day.put("comment", backupDay.dmyString() + " " + backupDay.hmsString() +  " : " + comment);
				}

			}
		}

		return list;
	}
}
