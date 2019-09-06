import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WebSearchEngine {
	
	
	static ArrayList<String> key = new ArrayList<String>();
	static Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();
	static Scanner sc = new Scanner(System.in);
	
	public WebSearchEngine()
	{
		try {
			HTMLTextConverter.convertHtmlToText();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//get occurance and position of word
	public int searchWord(File filePath, String s1)throws IOException  {
		int counter=0;
		String data="";
		try
    	{
    		BufferedReader Object = new BufferedReader(new FileReader(filePath));
    		String line = null;
    		
              while ((line = Object.readLine()) != null){
              
            	  data= data+line;
             
             }
              Object.close();
      		
    	}
    	catch(NullPointerException  e)
    	{
    		e.printStackTrace();
    	}
		
			
			
			String txt = data;
			BoyerMoore offset1a = new BoyerMoore(s1);

			 int offset = 0;

				for (int loc = 0; loc <= txt.length(); loc += offset + s1.length()) {

					offset = offset1a.search(s1, txt.substring(loc)); 
					//System.out.println("\n");
					if ((offset + loc) < txt.length()) {
						counter++;
						System.out.println(s1 + " is at position " + (offset + loc));   //printing position of word
					}
				}
				if(counter!=0)	{			
					System.out.println("\nIn file: "+filePath.getName());
					System.out.println("-----------------------------------------------------\n");			
					
				}
				return counter;
	}
		
	// Ranking of Web Pages using merge sort 
	//Collections.sort by default uses merge sort
	 public static void rankFiles(Hashtable<?, Integer> fname,int occur){
			

	       //Transfer as List and sort it
	       ArrayList<Map.Entry<?, Integer>> list = new ArrayList(fname.entrySet());
	       
	      
	       
	       Collections.sort(list, new Comparator<Map.Entry<?, Integer>>(){

	         public int compare(Map.Entry<?, Integer> obj1, Map.Entry<?, Integer> obj2) {
	            return obj1.getValue().compareTo(obj2.getValue());
	        }});
	      
	       Collections.reverse(list);
	       
	      
	       if(occur!=0) {
		       System.out.println("\n------Best 5 Search Results-----\n");
		       
		       int num = 5;
		       int j = 1;
				while (list.size() > j && num>0){
					System.out.println("("+j+") "+list.get(j)+" times ");
					j++;
					num--;
				}
	       }else {
	    	   
	       }  
	       
	    }
	 
	 /*using regex to find similar string to pattern */
	 public void suggestions(String pattern) {
			try {
				
				// String to be scanned to find the pattern.
			      String line = " ";
			      String reg = "[\\w]+[@$%^&*()!?{}\b\n\t]*";
			      
			     
			      // Create a Pattern object
			      Pattern pat = Pattern.compile(reg);
			      // Now create matcher object.
			      Matcher match = pat.matcher(line);
			      int fileNum=0;
					try {
						File directory = new File("C:\\Users\\dolly\\eclipse-workspace\\SearchEngine\\src\\ConvertedTextFiles\\");
						File[] fileArray = directory.listFiles();
						for(int i=0;i<fileArray.length;i++)			
						{
							findWord(fileArray[i],fileNum,match,pattern);
							fileNum++;
						}
						
				        Set keys = new HashSet();
				        Integer value =1;
				        Integer val = 0;
				        int counter = 0;
				        
				      
				        System.out.println("\nDid you mean?:");
				        System.out.println("---------------------");
				        for(Map.Entry entry: numbers.entrySet()){
				        	if(val == entry.getValue()) {
				        		
				        		break;
					           
				            }
				        	else {
				        		
				        		 if(value==entry.getValue()){
						                
						            	if (counter==0) {
											System.out.print(entry.getKey());
											counter++;
										}
						            	
						            	else {
											System.out.print(" or "+entry.getKey());
											counter++;
										}
				                
				            }
				            
				        	}}
				        


					} catch (Exception e) {
						System.out.println("Exception:"+e);
					}
					finally
					{
						
					}
				
				
				
			}
			catch(Exception e){
				
			}
		}
			
		//finds strings with similar pattern and calls edit distance() on those strings
		public static void findWord(File sourceFile,int fileNumber,Matcher match,String str) throws FileNotFoundException,ArrayIndexOutOfBoundsException
	    {
	    	try
	    	{
	    		int i = 0;
	    		BufferedReader rederObject = new BufferedReader(new FileReader(sourceFile));
	    		String line = null;

	              while ((line = rederObject.readLine()) != null)
	              {
	              match.reset(line);
	              while (match.find()) 
	              {
	            	  key.add(match.group());
	                }
	             }
	              rederObject.close();
	              for(int p = 0; p<key.size(); p++){ 
	              	  numbers.put(key.get(p), editDistance(str.toLowerCase(),key.get(p).toLowerCase()));
	                }
	    	}     
	    	catch(Exception e)
	    	{
	    		System.out.println("Exception:"+e);
	    	}
	    	
	    }
		
		//Uses Edit distance to compare nearest distance between keyword and similar patterns obtained from regex
		public static int editDistance(String str1, String str2) {
			int len1 = str1.length();
			int len2 = str2.length();
		 
			
			int[][] array = new int[len1 + 1][len2 + 1];
		 
			for (int i = 0; i <= len1; i++) {
				array[i][0] = i;
			}
		 
			for (int j = 0; j <= len2; j++) {
				array[0][j] = j;
			}
		 
			//iterate though, and check last char
			for (int i = 0; i < len1; i++) {
				char c1 = str1.charAt(i);
				for (int j = 0; j < len2; j++) {
					char c2 = str2.charAt(j);
		 
					if (c1 == c2) {
						
						array[i + 1][j + 1] = array[i][j];
					} else {
						int replace = array[i][j] + 1;
						int insert = array[i][j + 1] + 1;
						int delete = array[i + 1][j] + 1;
		 
						int min = replace > insert ? insert : replace;
						min = delete > min ? min : delete;
						array[i + 1][j + 1] = min;
					}
				}
			}
		 
			return array[len1][len2];
		}

	
	
	
	public static void main(String[] args) {
		
		WebSearchEngine websearch = new WebSearchEngine();
		
		
		Hashtable<String,Integer> hashtable = new Hashtable<String,Integer>();
		Scanner s = new Scanner (System.in);
		System.out.println("Enter your search: ");
		String p= s.nextLine();
		long fileNumber = 0;
		int frequency=0;
		int rep=0;		// No. of files that contains the Searched word
		try {
			long startTime = System.currentTimeMillis();
			long startTimesearch = System.currentTimeMillis();
			File dir = new File("C:\\Users\\dolly\\eclipse-workspace\\SearchEngine\\src\\ConvertedTextFiles");
			
			File[] fileArray = dir.listFiles();
			for(int i=0;i<fileArray.length;i++)	
			{
				
				frequency=websearch.searchWord(fileArray[i],p);
				
				hashtable.put(fileArray[i].getName(), frequency);
				if(frequency!=0) 
				{
					rep++;
				}
							
				fileNumber++;
			}
		
			System.out.println("\nNumber of files contains " + p + " word is= "+rep);
			long endTimesearch = System.currentTimeMillis();
			System.out.println("\nSerach Result execution time:"+(endTimesearch-startTimesearch)+" Milli Seconds");
			if(rep==0) {
				
		        System.out.println("\nSearching...");
		        
				websearch.suggestions(p);	
				
			}
			long startTimerank = System.currentTimeMillis();
			rankFiles(hashtable,rep);
			long endTimerank = System.currentTimeMillis();
			System.out.println("\nRanking Algorithm time:"+(endTimerank-startTimerank)+" Milli Seconds");
			long endTime = System.currentTimeMillis();
			
			System.out.println("\nTotal Execution Time:"+(endTime-startTime)+" Milli Seconds");
		
		}
		catch (Exception e) {
			System.out.println("Exception:"+e);
		}
		finally
		{
				
		} 
		
		}
	
		
	
	
	}

	
