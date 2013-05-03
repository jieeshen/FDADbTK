package gov.fda.dbtk;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

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
    String volume;
    String issue;
    String pgn;
    String year;
    String affiliation;
    String fauthor;
    ArrayList<String> authors=new ArrayList<String>();
    String atitle;
    String aabs;
    String doi;

    // Constructor initialize
    public PubMedRef(String pmidString) throws Exception{
        //get an remote XML and transform to Document
        String urlString="http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id="+pmidString+"&retmode=xml";
        String path="C:\\JShen\\Research\\EDKB\\ER\\CTD\\newRef\\";
        URL xmlUrl= new URL(urlString);


        BufferedReader in = new BufferedReader(new InputStreamReader(xmlUrl.openStream()));
        PrintWriter pw = new PrintWriter(new FileOutputStream(new File(path+pmidString+".xml")));
        String str;
        while((str = in.readLine()) != null){
            pw.println(str);
        }
        in.close();
        pw.flush();
        pw.close();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        //Document doc = db.parse(new File(path+pmidString+".xml"));
        Document doc = db.parse(xmlUrl.openStream());

        Node root=doc.getDocumentElement();

        parserNode(root);
        fauthor=authors.get(0);
        pmid=Integer.parseInt(pmidString);
    }

    private void parserNode(Node node){
        String tagName = node.getNodeName();
        NodeList children = node.getChildNodes();

        for (int i=0;i<children.getLength();i++){
            Node childnode=children.item(i);
            Short nodeType = childnode.getNodeType();

            if(childnode.getNodeName()=="AuthorList"){
                NodeList authorNodes=childnode.getChildNodes();
                for (int j=0;j<authorNodes.getLength();j++){
                    Node authNode=authorNodes.item(j);
                    if (authNode.getNodeType()== 1){
                        NodeList authName=authNode.getChildNodes();
                        String aname= authName.item(1).getTextContent()+", "+authName.item(3).getTextContent();
                        System.out.println("----"+aname);
                        authors.add(aname);
                    }

                }

            }

            if(nodeType == Node.ELEMENT_NODE ){
                parserNode(childnode);
            }else if(nodeType ==Node.TEXT_NODE){
                if (tagName=="Year" && childnode.getParentNode().getParentNode().getNodeName()=="PubDate"){
                    System.out.print(tagName+" : ");
                    System.out.println(childnode.getNodeValue());
                    year=childnode.getNodeValue();
                }
                if (tagName=="Affiliation"){
                    System.out.print(tagName+" : ");
                    System.out.println(childnode.getNodeValue());
                    affiliation=childnode.getNodeValue();
                }
                if (tagName=="ISOAbbreviation"){
                    System.out.print(tagName+" : ");
                    System.out.println(childnode.getNodeValue());
                    journal=childnode.getNodeValue();
                }
                if (tagName=="Volume"){
                    System.out.print(tagName+" : ");
                    System.out.println(childnode.getNodeValue());
                    volume=childnode.getNodeValue();
                }
                if (tagName=="Issue"){
                    System.out.print(tagName+" : ");
                    System.out.println(childnode.getNodeValue());
                    issue=childnode.getNodeValue();
                }
                if (tagName=="MedlinePgn"){
                    System.out.print(tagName+" : ");
                    System.out.println(childnode.getNodeValue());
                    pgn=childnode.getNodeValue();
                }

                if (tagName=="ArticleTitle"){
                    System.out.print(tagName+" : ");
                    System.out.println(childnode.getNodeValue());
                    atitle=childnode.getNodeValue();
                }
                if (tagName=="AbstractText"){
                    System.out.print(tagName+" : ");
                    System.out.println(childnode.getNodeValue());
                    aabs=childnode.getNodeValue();
                }
                if (tagName=="ELocationID"){
                    System.out.print(tagName+" : ");
                    System.out.println(childnode.getNodeValue());
                    doi=childnode.getNodeValue();
                }


                //System.out.println(childnode.getNodeValue());
                //System.out.println(childnode.getNodeName());
                //System.out.println(childnode.getParentNode().getParentNode().getNodeName());
                //System.out.println("-------------");

            }
        }
    }

    public static void main(String[] argv) throws Exception{
        PubMedRef ref1=new PubMedRef("18992764");
    }






}
