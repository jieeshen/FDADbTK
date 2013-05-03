package gov.fda.dbtk;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: JShen
 * Date: 5/2/13
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class PubMedRef {
    int pmid;
    String journal;
    String volumn;
    String issue;
    String fpage;
    String lpage;
    String year;
    String fauthor;
    String[] authors;
    String title;
    String aabs;
    String doi;

    // Constructor initialize
    public PubMedRef(String pmidString) throws Exception{
        //get an remote XML and transform to Document
        String urlString="http://www.ncbi.nlm.nih.gov/pubmed/"+pmidString+"?report=xml";
        URL xmlUrl= new URL(urlString);
        InputStream xml = xmlUrl.openStream();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xml);
        xml.close();

        Node root=doc.getDocumentElement();
        parserNode(root);
    }

    private void parserNode(Node node){


    }


}
