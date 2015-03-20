import java.util.StringTokenizer;

public class util{
	
	public static String[] getTokens(String input){
	    int i = 0;
	    StringTokenizer st = new StringTokenizer(input);
	    int numTokens = st.countTokens();
	    String[] tokenList = new String[numTokens];
	    while (st.hasMoreTokens()){
	    	String next = st.nextToken();
	        tokenList[i] = wordFilter(next);
	        i++;
	    }
	    return(tokenList);
	}
	
    public static String wordFilter(String input)
    {
    	input = input.trim();
    	return input;
    }
    
    // for test
    public static void printWordList(String[] inputtoken)
    {
    	for (int i = 0; i < inputtoken.length; i++)
    	{
    		System.out.println(inputtoken[i]);
    	}
    	System.out.println();
    }
    
    public static String deletecomma(String input)
    {
    	int index1 = input.indexOf("\"") ;
        String newline = input;
    	while (index1 != -1)
    	{
    		String sub1 = newline.substring(0, index1);
    		String subtemp = newline.substring(index1+1);
    		int index2 = subtemp.indexOf("\"");
            if (index2 != -1)
            {
            	String sub2 = subtemp.substring(0, index2);
            	sub2 = sub2.replaceAll(",",".");
            	String sub3 = subtemp.substring(index2+1);
            	newline = sub1 + sub2 + sub3;
            }
            else
            {
            	subtemp = subtemp.replaceAll(",",".");
            	newline = sub1 + subtemp;
            	return newline;
            }
            index1 = newline.indexOf("\"") ;
    	}
    	return newline;
    }

}