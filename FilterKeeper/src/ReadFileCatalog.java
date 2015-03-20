
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

public class ReadFileCatalog {
  public ReadFileCatalog() {}
  
  public static void ChangeDir(String dirname){
	  dir = dirname;
  }

  private static String dir = "POINTS";
  private static ArrayList<String> l = new ArrayList<String>();
  /**
   * �ҵ�ĳ���ļ����µ������ļ��к��ļ�
   * @param findpath String
   * @throws FileNotFoundException
   * @throws IOException
   * @return boolean
   */
  private static  boolean readfile(String filepath) throws FileNotFoundException,
      IOException {
	 // String[] filelistall = new String[1000];
	 // int count = 0;
	
	  
    try {

      File file = new File(filepath);
      if (!file.isDirectory()) {
      //  System.out.println("�ļ�");
      //  System.out.println("path=" + file.getPath());
      //  System.out.println("absolutepath=" + file.getAbsolutePath());
      //  System.out.println("name=" + file.getName());

      }
      else if (file.isDirectory()) {
       // System.out.println("�ļ���");
    	String[] filelist = file.list();
        for (int i = 0; i < filelist.length; i++) {
          File readfile = new File(filepath + "/" + filelist[i]);
          if (!readfile.isDirectory()) {
          //  System.out.println("path=" + readfile.getPath());
            l.add(readfile.getPath());
        //   System.out.println("absolutepath=" + readfile.getAbsolutePath());
        //    System.out.println("name=" + readfile.getName());           
          }
          else if (readfile.isDirectory()) {
            readfile(filepath + "/" + filelist[i]);
          }
        }
      }
    }
    catch (FileNotFoundException e) {
      System.out.println("readfile()   Exception:" + e.getMessage());
    }
    return true;
  }
     
  public static ArrayList<String> getAllFiles()
  {
	
	    try {
	    //  readfile("D:/file");
	    	readfile(dir);
	      //deletefile("D:/file");
	    }
	    catch (FileNotFoundException ex) {
	    }
	    catch (IOException ex) {
	    }
	    return l;
  }

  /*
  public static void main(String[] args) {
	
	  getAllFiles();
 //   System.out.println("ok");
  }
  */
  
}