package gov.fda.dbtk;

import java.io.*;
import java.util.ArrayList;
import gov.fda.dbtk.PubMedRef;
/**
 * Created with IntelliJ IDEA.
 * User: JShen
 * Date: 5/3/13
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetPMInfo {

    static ArrayList<String> getPubMedIds(String inFileName) throws Exception{
        //read in a file contains a pure list of pubmed ids.
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inFileName)));
        ArrayList<String> pmidList = new ArrayList<String>();
        String pmid = null;
        while ((pmid = br.readLine())!=null){
            pmidList.add(pmid);
        }
        return pmidList;
    }

    public static void main(String[] args) throws Exception{
        ArrayList<String> pmList=getPubMedIds("E:\\Research\\EDKB\\DEAC\\130501_CTD\\pmid_list.txt");
        PrintWriter pw=new PrintWriter(new OutputStreamWriter(new FileOutputStream("E:\\Research\\EDKB\\DEAC\\130501_CTD\\out.txt")));
        pw.println("ID\tPubMedLink\tFullText\tAuthors\tTitle\tJournal\tYear\tVolume\tIssue\tPages\tDOI\tAffiliation\tAbstract");


        for (int i=0; i<pmList.size();i++){
            PubMedRef ref=new PubMedRef(pmList.get(i));
            pw.print(ref.pmid+"\t");
            pw.print("http://www.ncbi.nlm.nih.gov/pubmed/"+ref.pmid+"\t");
            if (ref.doi!=null){
                pw.print("http://dx.doi.org/"+ref.pmid+"\t");
            }
            else{
                pw.print("\t");
            }
            pw.print(ref.authors.toString()+"\t");
            pw.print(ref.atitle+"\t");
            pw.print(ref.journal+"\t");
            pw.print(ref.year+"\t");
            pw.print(ref.volume+"\t");
            pw.print(ref.issue+"\t");
            pw.print(ref.pgn+"\t");
            pw.print(ref.doi+"\t");
            pw.print(ref.affiliation+"\t");
            pw.print(ref.aabs);
            pw.println();
        }

        pw.flush();
        pw.close();


    }

}
