import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.io.IOException;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import util.util;

//import database.skuisbn;

public class keeper{
	
	// files

	private String  relatedwordsinfile;
	
	// buffers	
	// this is the file reader buffer to read the realtedwords
	private BufferedReader relatedBuffer;

	// related words file list
	private ArrayList<String> relalist;
	private int listsize;
	
//	private skuisbn dbsi;
	
	private static int linenumber = 0; 
	private static int validlines = 0;
  	private static int keepedlines = 0;
	
	private static int isbnlines = 0;
	private static int validisbnnumbers = 0;

	private Map<String, String> mymap_SKU_ISBN = new HashMap<String, String>();
	
	public void inirelatedbuffer(int i) throws FileNotFoundException
	{   
	//	System.out.println("i:" + i );
		relatedwordsinfile = relalist.get(i);	
		System.out.println("file name: " + relatedwordsinfile );
        File related = new File(relatedwordsinfile);
        FileReader frelated = new FileReader(related);        
        relatedBuffer = new BufferedReader(frelated); 
	}

	public keeper() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{		
    //  initialize title buffer       
    //  read the related words files list
		ReadFileCatalog.ChangeDir("ZHENDIANBIAO");
	    relalist = ReadFileCatalog.getAllFiles();
	    listsize = relalist.size();
	    System.out.println ( "file list size:" + relalist.size() );
	//  dbsi = new skuisbn();           
	}
	
	public void fullfillAuthor() throws IOException, SQLException
	{
		DateFormat   df   =   new   SimpleDateFormat( "yyyy-MM-dd   HH:mm:ss "); 
		System.out.println(df.format(new   Date())); 
		
		ReadFileCatalog.ChangeDir("Correction");
		relalist.clear();
		relalist = ReadFileCatalog.getAllFiles();
	    listsize = relalist.size();
	    System.out.println ( "file list size:" + relalist.size() );
	    
        String OutputFile = "keeper.txt"; 
        FileWriter fw = new FileWriter(OutputFile);
        PrintWriter pw = new PrintWriter(fw);
  //    StringBuffer buffer = new StringBuffer();
	
        String linewords = "not empty";
        int fileindex = 0;
        int invalidlines = 0;

        while (fileindex < this.listsize)
        {        
        	this.inirelatedbuffer(fileindex);  
            linewords =  "";

            // while (linewords != null)
          	while (linewords != null)
            { 
            	linenumber++;
            	linewords =  this.relatedBuffer.readLine();
            	linewords = util.deletecomma(linewords);             	
             	System.out.println( "line: " + linenumber + ",linewords:" + linewords  ); 
             	if (linewords == null)
            	{
            		System.out.println( "null linewords:" + linenumber  ); 
            		continue;
            	} 
            	if ( linewords.contains("seller-sku") )
            	{
            		
            		continue;
            	}
            	if (linewords == "")
            	{
            		continue;
            	}

            	String strs[];
            	
            	if (linewords.contains("\t"))
            	{
            		strs = linewords.split("\t",2);
            	}
            	else
            	{
            		strs = linewords.split(",");
            	}
            		            	
            	if (strs.length < 1)
            	{             		
            		continue;
            	}
            	            	
            	if ( strs[0] == null )
            	{
            		continue;
            	}
        		
           		validlines++;
            	boolean filter = false;
            	
                //   if (strs.length < 9)
                //   continue;
            	
                String Isnb = strs[0];
                while(Isnb.length() < 10)
                {
                	Isnb = "0" + Isnb;
                }
            	if ( mymap_SKU_ISBN.containsKey( Isnb ) )
        		{
        			filter = true;
        		}                                 	
                
            	if (filter == true)
            	{
            		keepedlines ++;
            	//  buffer.append(pruduct[0][0] + " " + pruduct[0][1] + " " +  pruduct[0][2]  + " " +  pruduct[0][3]);  
            		String output = linewords + "\t" + mymap_SKU_ISBN.get(Isnb);
            		pw.println(output);
            	//	pw.println("\n");
        			System.out.println(" product: " + validlines + " " + output); 
                  // buffer.append("\n");                       
            	}
            	if (filter == false)
            	{
            		pw.println(linewords);
            		invalidlines++;
            	}
           
            }
            fileindex++;
          
        }
        
        // read io close
        this.relatedBuffer.close();       
        System.out.println(df.format(new Date())); 
        
    //  this.dbsi.closecon();
        System.out.println("valid product lines: " + validlines );

        System.out.println("keeped product lines: " + keepedlines );
        System.out.println("read product information finish");
        System.out.println("invalidlines: " + invalidlines);
           
    //  pw.println(buffer.toString());  
        pw.close();
     
	}
	
	public void readSkuIsbn() throws IOException, SQLException
	{
		ReadFileCatalog.ChangeDir("ZHENDIANBIAO");
		relalist.clear();
		relalist = ReadFileCatalog.getAllFiles();
	    listsize = relalist.size();
	    System.out.println ( "file list size:" + relalist.size() );
	    
        String linewords = "not empty";
        int fileindex = 0;
               
        while (fileindex < this.listsize)
        {     
      
        	this.inirelatedbuffer(fileindex);  
            linewords =  "not null";
          	
            while (linewords != null)
            { 
            	isbnlines++;
            	linewords =  this.relatedBuffer.readLine();
            	             	
             	System.out.println( "line: " + isbnlines + ",linewords:" + linewords  ); 
             	if (linewords == null)
            	{
            		continue;
            	} 
            	if ( linewords.contains("ProductId") )
            	{
            		continue;
            	}
            	if (linewords == "")
            	{
            		continue;
            	}
 
           //   String strs[] = util.getTokens(linewords);
            	String strs[] = linewords.split("\t",2);
           //   String strs[] = linewords.split(",");
            	
            	if (strs.length < 2)
            	{
            		continue;
            	}
            	
            	if (strs[0] == null)
            	{
            		continue;
            	}
            	
            	if (strs[0].length() != 10)
            	{
            	//	continue;
            	}
          
            	/*
             	String isbn = strs[1];
            	
            	while(isbn.length() < 10)
            	{
            		isbn = "0" + isbn;
            	}
            	*/
                              
            //	String firstunit = strs[45].replaceAll(" ","");
            	
            	validisbnnumbers++;            
            	mymap_SKU_ISBN.put( strs[0], strs[1] );           
            }
            fileindex++;        
         }        
        // read io close
        this.relatedBuffer.close();       
  //    this.dbsi.closecon();
        System.out.println("valid isbn lines: " + validisbnnumbers );
        System.out.println("read filter sku finish");      
	}
	
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{    
		keeper myco = new keeper();		
		myco.readSkuIsbn();	
		myco.fullfillAuthor();					
    }   

}

 