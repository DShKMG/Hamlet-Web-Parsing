import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

/*PLEASE ADD THE LIBRARY FILE LIB FOLDER INSIDE THE PROJECT IN ORDER TO RUN THE SPECIFIC LIBRARY DEPENDENT PACKAGES BELOW*/
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

/*PLEASE NOTE THAT ALL FILES UNDER THE  LIB DIRECTORY IN THE LIBRARY FOLDER HAS TO BE INCLUDED*/
public class App {
    public static int getFreq(String s, String sub) throws IOException {

        int ind, count = 0;
        for (int i = 0; i + sub.length() <= s.length(); i++) // i+sub.length() is used to reduce comparisions
        {
            ind = s.indexOf(sub, i);
            if (ind >= 0) {
                count++;
                i = ind;
                ind = -1;
            }
        }
        System.out.println("Occurence of '" + sub + "' in String is " + count);
        return count;
    }
    // Find frequency function goes above here

    // Main function where programme runs
    public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        System.out.println("This program is going to give you 2 output file where you can find details in there\n");
        System.out.println("File names are :\n1 - Fulltext.TXT\n2 - World Frequency.TXT\n");

        // Website settings
        WebClient webClient = new WebClient();
        HtmlPage currentPage = webClient.getPage("http://shakespeare.mit.edu/hamlet/full.html");
        System.out.println("Fetched data from : " + currentPage.getTitleText());

        // File I/O
        File fobj1 = new File("Fulltext.TXT");
        File fobj2 = new File("World Frequency.TXT");
        if (fobj1.exists())
            fobj1.delete();
        if (fobj2.exists())
            fobj2.delete();
        fobj1.createNewFile();
        fobj2.createNewFile();
        PrintWriter pw1 = new PrintWriter(fobj1);
        PrintWriter pw2 = new PrintWriter(fobj2);
        // File I/O

        // Declaring variables
        String fulline = "";
        String[] fullbook;
        int nodeLen = 300;

        // Using XPath to get the all queries

        // For given nodes only, original node length 5163

        for (int i = 0; i < nodeLen; i++) {
            HtmlElement words = (HtmlElement) currentPage.getByXPath("//a").get(i);

            // HtmlElement actors = (HtmlElement) currentPage.getByXPath("//b").get(i);
            // DomNode result = actors.getChildNodes().get(0); Those two ar uncessary since
            // a gives everything including actors
            DomNode wordSaid = words.getChildNodes().get(0);
            System.out.println("\nNode Nr : " + i);
            System.out.println(wordSaid.asText());
            fulline += wordSaid.asText();
            pw1.println(wordSaid.asText() + "\n"); // Prints the line inside the Fulltext file
            pw1.flush();
        }
        fullbook = fulline.split(" "); // Splits into the seperate words

        // Initializing freq Word array corresponds to each word
        int[] freqWord = new int[fullbook.length];

        for (int i = 0; i < fullbook.length; i++) {
            freqWord[i] = getFreq(fulline, fullbook[i]);
        }
        // Sorting for the 3rd task by getting index sorting according to the Frequency
        // of Words array
        Integer[] indexes = IntStream.range(0, freqWord.length).boxed().toArray(Integer[]::new);
        Arrays.sort(indexes, Comparator.<Integer>comparingDouble(i -> freqWord[i]));
        // By this way we can just use new array called indixed to find max indices
        // places
        for (int i = 0; i < indexes.length; i++) {
            System.out.println(fullbook[i] + " x " + freqWord[i]);
            pw2.println(fullbook[i] + " x " + freqWord[i]);
            pw2.flush();
        }

        // For given nodes only

        // Codes below are old codes which did not work properly or edited to have
        // better versions
        // Automated loop with unknown nodes to write
        /*
         * int keyLoop = 1; int i =0; while(keyLoop == 1){ // Codes here HtmlElement
         * words = (HtmlElement) currentPage.getByXPath("//a").get(i); DomNode
         * parsedWords = words.getChildNodes().get(0); fulline +=parsedWords.asText();
         * i+=1; System.out.println("\nNode value : "+i); if
         * (parsedWords.getChildNodes().isEmpty() == false){
         * System.out.println("\nTotal Number of Nodes in this page : "+i+"\n"); keyLoop
         * = 0 ; } }
         */
        // Automated loop with unknown nodes to write

        /*
         * HtmlElement words = (HtmlElement) currentPage.getByXPath("//a").get(5162);
         * DomNode parsedWords = words.getChildNodes().get(0); fulline
         * +=parsedWords.asText();
         * System.out.println(fulline+"\n"+parsedWords.getChildNodes().isEmpty());
         */

        /*
         * String maxFreq = Seperator(fullbook); //Max frequency of the word
         * System.out.println("Max freq : "+maxFreq);
         */
        // Print full lines in text file

    }
}
