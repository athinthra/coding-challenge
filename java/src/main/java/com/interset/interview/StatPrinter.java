package com.interset.interview;

import java.util.Map;
import java.util.Map.Entry;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

/*
 * Utility to print the required stats
 */

public class StatPrinter {
	List<Object> records;

	public List<Object> getRecords() {
		return records;
	}

	public void setRecords(List<Object> records) {
		this.records = records;
	}
	
	/*
	 * Method to obtain string in printable format
	 * @param map-hashmap of required stat. n-gets first n in list (use negative or inf for all stats). 
	 * @return printable string
	 */
	public String showMapStats(Map<String, Integer> map, int n)
	{
		int nb = 0;
		String results = "";
		for (Entry<String, Integer> entry : map.entrySet()) {
		    String key = entry.getKey();
		    int value = entry.getValue();
		    if(nb < n || n < 0)
		    {
		    	nb ++;
		    	results += key +" ("+value+"), ";
		    }
		    else
		    {
		    	break;
		    }
		   
		}
		
		return results.substring(0, results.lastIndexOf(","));
	}
	
	public void printResults()
	{
		System.out.println("Avg Siblings: " + StatCalculator.sum_siblings / records.size());
		System.out.println("Best 3 Foods: " + this.showMapStats(sortByValue(StatCalculator.foods), 3));
		System.out.println("Birth Months: " + this.showMapStats(StatCalculator.birthMont, -1));
	}
	

	/*
	 * Sorting function taken from
	 * https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values
	 * Function is bit messy :(
	 */
	private static <K, V> Map<K, V> sortByValue(Map<K, V> map) {
	    List<Entry<K, V>> list = new LinkedList<>(map.entrySet());
	    Collections.sort(list, new Comparator<Object>() {
	        public int compare(Object o1, Object o2) {
	            return ((Comparable<V>) ((Map.Entry<K, V>) (o1)).getValue()).compareTo(((Map.Entry<K, V>) (o2)).getValue());
	        }
	    });

	    Map<K, V> result = new LinkedHashMap<>();
	    
	    for(int i = list.size() - 1 ; i >= 0 ; --i)
	    {
	    	Map.Entry<K, V> entry = list.get(i);
	    	result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	}
	
	
	
}