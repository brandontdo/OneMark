package com.example.onemarkeah;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.os.StrictMode;

/**
 * Parses a MarkBook output file to student entries
 */
public class CourseObject {
  
  public String id; //either a student # or email
  
  //parallel lists storing the entries and weight factors
  public ArrayList<Double> marks;
  public ArrayList<Double> outof;
  public ArrayList<Double> weights;
  public ArrayList<String> category;
  
  //maps categories (e.g. "K" for knowledge) to a percent
  private static HashMap<String, Double> binmarks;
  private static HashMap<String, Double> binoutof;

  private static HashMap<String, ArrayList<Double>> binMarkTotals;
  
  /**
   * 
   * @param url  URL to the HTML markbook file (e.g. Google Drive)  
   * @param id   The value in the first column to identify the student
   *             This is either a name or a code name
   */
  @SuppressLint("NewApi")
public CourseObject(String url, String id) {
    marks    = new ArrayList<Double>();
    outof    = new ArrayList<Double>();
    weights  = new ArrayList<Double>();
    category = new ArrayList<String>();
    binmarks = new HashMap<String, Double>();
    binoutof = new HashMap<String, Double>();
    binMarkTotals = new HashMap<String, ArrayList<Double>>();
    
    //PrintStream out = System.out;
    
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
    
    try {
      Document doc = Jsoup.parse(toString(new URL(url).openStream()), "UTF-8");
      
      Element markTable = doc.getElementsByTag("table").get(0); //get the marks
      Element infoTable = doc.getElementsByTag("table").get(1); //get the entry info
      
      Elements rows, cols;
      
      //look for the student name
      rows = markTable.select("tr");
      
      /*
      //make sure that ALL the entries 1..N are in the same file
      if (!rows.get(0).select("td").get(1).text().equals("1")) {
        System.err.println("You must have ALL entries (i.e. starting at 1) in one HTML file.");
        return;
      }
      */
      
      //get the "outof" column
      int orow = -1;
      if (rows.get(1).select("td").get(0).text().contains("Out Of")) {
        orow = 1;
      }
      
      cols = rows.get(1).select("td");
      if (orow >= 0) for (int i = 1; i < cols.size(); i++) {
         outof.add(Double.parseDouble(cols.get(i).text()));
      }
      
      int mrow = 2;
      for (int i = 0; i < rows.size(); i++) {
        if (rows.get(i).text().contains("Percent")) {
          mrow = i + 1;
          break;
        }
      }
      
      int mcol = 2;
      if (rows.get(mrow-1).text().contains("Zero")) mcol = 4;
            
      //find the student id from the list
      for (int i = mrow; i < rows.size(); i++) {
        cols = rows.get(i).select("td");
        if (cols.get(0).text().equals(id)) {
          //get the marks
          for (int j = mcol; j < cols.size(); j++) {
            String s = cols.get(j).text();
            if (s.contains("Zero")) s = "0";
            if (s.contains("--")) s = "-1";
            marks.add(Double.parseDouble(s));
            if (orow < 0) outof.add(100.0);
          }
          break;
        }
      }
      
      //parse the info table at the bottom for bins and weights
      rows = infoTable.select("tr");
      
      int wcol = -1;
      cols = rows.get(0).select("td");
      for (int i = 0; i < cols.size(); i++) {
        if (cols.get(i).text().contains("Weight")) {
          wcol = i;
          break;
        }
      }
      
      int bcol = -1;
      cols = rows.get(0).select("td");
      for (int i = 0; i < cols.size(); i++) {
        if (cols.get(i).text().contains("Category")) {
          bcol = i;
          break;
        }
      }
      
      for (int i = 1; i < rows.size(); i++) {
        cols = rows.get(i).select("td");
        
        String b = "";
        double w = 1.0;
        if (wcol >= 0) w = Double.parseDouble(cols.get(wcol).text());
        if (bcol >= 0) b = cols.get(bcol).text();

        if (!binmarks.containsKey(b)) {
          binmarks.put(b, 0.0);
          binoutof.put(b, 0.0);
          binMarkTotals.put(b, new ArrayList<Double>());
        }
        int entry = weights.size();
        if (marks.get(entry) < 0) w = 0.0;
        binmarks.put(b, binmarks.get(b) + w*(marks.get(entry)/outof.get(entry)));
        binoutof.put(b, binoutof.get(b) + w);
        binMarkTotals.get(b).add(binmarks.get(b) / binoutof.get(b) * 100);
        
        weights.add(w);
        category.add(b);
      }
      
      /*
      System.out.println(marks);
      System.out.println(outof);
      System.out.println(weights);
      System.out.println(category);
      */
      
    } catch (IOException e) {
      System.err.println("Cannot load markbook file from: " + url);
      e.printStackTrace();
    }
  }
  
  public int numEntries() { return marks.size(); }
  
  private double getBinMark(String bin) {
    if (!binmarks.containsKey(bin)) {
      return 0.0;
    }
    return binmarks.get(bin) / binoutof.get(bin);
  }
  
  private int getBinLength(String bin) {
	  return binMarkTotals.get(bin).size();
  }
  
  public double getOverallMark() {
    double ret = 0, tot = 0;
    for (int i = 0; i < marks.size(); i++) {
        ret += weights.get(i)*(marks.get(i) / outof.get(i));
        tot += weights.get(i);
    }
    if (tot == 0.0) return -1.0;
    return 100*(ret / tot);
  }
  
  private static String toString(InputStream is) {
    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }
  
	public double getTotalBinMark(int pos) {

		double binTotal = 0;
		for (int i = 0; i < pos; i++) {
			binTotal += binmarks.get(i);
		}
		if (binmarks.size() == 0) return 0;
		return binTotal / binmarks.size();
	
	}
	public ArrayList<String> getCategories(){
		return this.category;
	}
  
  //example usage: pass a file and a codename
  public static void main(String[] args) {
    CourseObject s = new CourseObject("http://alexli.ca/onemark/SPH4C1.htm", "VSRSS");
    System.out.println(s.getOverallMark());
  }
}

