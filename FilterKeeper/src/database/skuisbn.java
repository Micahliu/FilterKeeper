
package database;

import java.sql.*;

import dataobject.ItemOrder;

public class skuisbn{
	
	private Connection con;
	private PreparedStatement psInsert;
	private PreparedStatement  psSelectItemAccount;
	private PreparedStatement  psSelectIsbn;
	private PreparedStatement psSelectCheckTimes;
	private PreparedStatement psUpdataCheckTimes;
	private PreparedStatement psDeleteIsbn;
//	private PreparedStatement  psUpdate;
	
	public void iniconnect() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		 Class.forName("com.mysql.jdbc.Driver").newInstance();
	     con=java.sql.DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/amazon","root","");		 
			
	     psInsert = con.prepareStatement(
			    "insert into skuisbn(sku,isbn,checktime) " +
			    "values(?,?,?)");
		 psSelectItemAccount= con.prepareStatement("select count(*) from skuisbn where sku = ? and isbn = ?");

		 psSelectIsbn = con.prepareStatement("select isbn from skuisbn where sku = ?" );
		 
		 psSelectCheckTimes = con.prepareStatement("select checktime from skuisbn where sku = ?" );
		 
		 psUpdataCheckTimes = con.prepareStatement("update skuisbn set checktime = checktime + 1 where sku = ? and isbn = ?");
		 //		 psUpdate = con.prepareStatement(
//				    "update ordersskuisbn set checktime = checktime+1 where sku = ? and isbn = ?");
		 psDeleteIsbn = con.prepareStatement("delete from skuisbn where sku = ? and isbn!= ?");
	}
	
	public skuisbn() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
	//	iniconnect();
	}
	
	public void closecon() throws SQLException
	{
		psInsert.close();
		psSelectItemAccount.close();
		psSelectIsbn.close();
		psSelectCheckTimes.close();
//		psUpdate.close();
		con.close();
	}
	
	public void UpdagteCheckTimesFromSku(String sku, String Isbn) throws SQLException
	{	
	//	psUpdataCheckTimes.setInt(1,1);  
		psUpdataCheckTimes.setString(1,sku);           //set parameter
		psUpdataCheckTimes.setString(2,Isbn);  
	    psUpdataCheckTimes.executeUpdate(); 
	}
	
	public int getCheckTimesFromSku(String sku) throws SQLException
	{	

		psSelectCheckTimes.setString(1,sku);           //set parameter
	    ResultSet rst=psSelectCheckTimes.executeQuery(); 
	    int checktimes = 0;

	    while(rst.next())
	    {    
	    	checktimes = rst.getInt(1);
	    	return checktimes;
	    }
	    return checktimes;

	}
	
	public String getIsbnFromSku(String sku) throws SQLException
	{	
		psSelectIsbn.setString(1,sku);           //set parameter
	    ResultSet rst=psSelectIsbn.executeQuery(); 
	    String isbn = "";

	    while(rst.next())
	    {    
	    //	number ++;
	    	isbn = rst.getString(1);
	    }
	 //   if (number == 1)
	 //   {
	    	return isbn;
	//    }
	//    else
	//    {
	//    	return "";
	//    }
	}
	
	public boolean alreadycontinueSkuIsbn(ItemOrder ito) throws SQLException
	{
		boolean re = false;
		
		psSelectItemAccount.setString(1,ito.sku);           //set parameter
		psSelectItemAccount.setString(2,ito.isbn);   
		
	    ResultSet rst=psSelectItemAccount.executeQuery(); 
        
	    while(rst.next())
	    {
	    	int number = rst.getInt(1);
	    	if ( number != 0 )
	    	{
	    		return true;
	    	}
	    }   
		return re;
	}
	
	public void write(ItemOrder ito) throws SQLException
	{

		 // if already contain sku, delete it first
	   	String isbn = getIsbnFromSku(ito.sku);
	   	if ( !isbn.equals("")   )
	   	{
	   		System.out.println("already have sku, delete it first");   
	   		psDeleteIsbn.setString(1, ito.sku);
	   		psDeleteIsbn.setString(2, ito.isbn);
	   		psDeleteIsbn.executeUpdate();
	   	}
	   	
	   	if ( alreadycontinueSkuIsbn(ito) == true )
	   	{
	   		System.out.println("already have skuIsbn:");        
	   		System.out.println(ito.toString());         
	   	    return;
	   	}

	   	//System.out.println("insert");
	   		   	
	   	psInsert.setString(1, ito.sku);
	   	psInsert.setString(2, ito.isbn);
	   	psInsert.setInt(3, 0);
	 
	   	psInsert.execute();
	}
	
}

    
    
