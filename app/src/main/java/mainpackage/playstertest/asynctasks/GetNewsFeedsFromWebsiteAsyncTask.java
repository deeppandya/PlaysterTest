package mainpackage.playstertest.asynctasks;

import android.os.AsyncTask;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import mainpackage.playstertest.listener.GetNewsFeedsFromWebsiteListener;
import mainpackage.playstertest.model.Story;
import mainpackage.playstertest.support.Utils;

/**
 * Created by deeppandya on 2017-04-09.
 */

public class GetNewsFeedsFromWebsiteAsyncTask extends AsyncTask<Void, Void, Document> {
    private Document xmlDocument;
    private GetNewsFeedsFromWebsiteListener getNewsFeedsFromWebsiteListener;

    public GetNewsFeedsFromWebsiteAsyncTask(GetNewsFeedsFromWebsiteListener getNewsFeedsFromWebsiteListener) {
        this.getNewsFeedsFromWebsiteListener = getNewsFeedsFromWebsiteListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        getNewsFeedsFromWebsiteListener.beforeGetNewsFeedsFromWebsite();
    }

    @Override
    protected Document doInBackground(Void... voids) {

        try {
            URL url = new URL(Utils.CBCLINK);
            URLConnection urlConn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            InputStream inputStream = httpConn.getInputStream();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            xmlDocument = documentBuilder.parse(inputStream);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return xmlDocument;
    }

    @Override
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);
        Element rootElement=document.getDocumentElement();
        rootElement.normalize();

        List<Story> storyList=new ArrayList<>();

        NodeList itemList = document.getElementsByTagName(Utils.ITEM);
        for (int i=0; i<itemList.getLength(); i++) {

            Node item = itemList.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                Element itemElement = (Element) item;
                Story story=new Story();
                story.setTitle(getAttrValue(Utils.TITLE,itemElement));
                story.setPubDate(getAttrValue(Utils.PUBDATE,itemElement));
                story.setAuthor(getAttrValue(Utils.AUTHOR,itemElement));
                story.setImgUrl(Utils.getImageUrlFromNode(getImgUrlFromDescription(Utils.DESCRIPTION,itemElement)));
                story.setLink(getAttrValue(Utils.LINK,itemElement));

                storyList.add(story);
            }
        }

        getNewsFeedsFromWebsiteListener.afterGetNewsFeedsFromWebsite(storyList);

    }

    private static String getAttrValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    private static String getImgUrlFromDescription(String tag, Element element) {
        NodeList nodeList=element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(1);
        return node.getNodeValue();
    }

    private String getCharacterDataFromElement(Node child) {
        // TODO Auto-generated method stub
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }
}
