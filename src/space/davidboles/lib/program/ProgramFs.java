package space.davidboles.lib.program;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ProgramFs {
	
	public static String getProgramDir() {
		return System.getProperty("user.dir");
	}
	
	public static File getProgramFile(String filename) {
		return new File(getProgramDir() + File.separator + filename);
	}
	
	public static void setProgramOut(PrintStream printStream) {
		System.setOut(printStream);
	}
	
	public static void setProgramOut(File file) {
		try {
			System.setOut(new PrintStream(new FileOutputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void setProgramOut(String fileName) {
		try {
			System.setOut(new PrintStream(new FileOutputStream(getProgramFile(fileName))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void setProgramErr(PrintStream printStream) {
		System.setErr(printStream);
	}
	
	public static void setProgramErr(File file) {
		try {
			System.setErr(new PrintStream(new FileOutputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void setProgramErr(String fileName) {
		try {
			System.setErr(new PrintStream(new FileOutputStream(getProgramFile(fileName))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Deprecated
	public static void outPrint(String out) {System.out.print("[" + System.currentTimeMillis() + "] " + out);}
	@Deprecated
	public static void outPrintln(String out) {System.out.println("[" + System.currentTimeMillis() + "] " + out);}
	@Deprecated
	public static void errPrint(String out) {System.err.print("[" + System.currentTimeMillis() + "] " + out);}
	@Deprecated
	public static void errPrintln(String out) {System.err.println("[" + System.currentTimeMillis() + "] " + out);}

	@Deprecated
	public static void outP (String out) {outPrint(out);}
	@Deprecated
	public static void outPL (String out) {outPrintln(out);}
	@Deprecated
	public static void errP (String out) {errPrint(out);}
	@Deprecated
	public static void errPL (String out) {errPrintln(out);}
	
	public static Object loadObject(File file) {
		try {
			ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			Object out = oin.readObject();
			oin.close();
			return out;
		}catch(Exception e) {
			ProgramFs.errPrintln("Error loading object at:" + file.getAbsolutePath() + ", error message:\n" + e.getMessage());
			return null;
		}
	}
	
	public static boolean saveObject(File file, Object object) {
		try {
			ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			oout.writeObject(object);
			oout.close();
			return(true);
		}catch(Exception e) {
			ProgramFs.errPrintln("Error saving object at:" + file.getAbsolutePath() + ", error message:\n" + e.getMessage());
			e.printStackTrace();
			return(false);
		}
	}
	
	public static String loadString(File file) {
		try {
			return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
		}catch(Exception e) {
			ProgramFs.errPrintln("Error loading object at:" + file.getAbsolutePath() + ", error message:\n" + e.getMessage());
			return null;
		}
	}
	
	public static boolean saveString(File file, String string) {
		try {
			PrintWriter p = new PrintWriter(file);
			p.print(string);
			p.close();
			return(true);
		}catch(Exception e) {
			ProgramFs.errPL("Error saving string at:" + file.getAbsolutePath() + ", error message:\n" + e.getMessage());
			return(false);
		}
	}
}
