package com.polamr.rssexample.utils;

import com.polamr.rssexample.Global;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class RssParseHandler extends DefaultHandler {

    private ArrayList<RssItem> rssItems;

    private RssItem currentItem;
    private boolean parsingTitle,parsingThumbnail;
    private boolean parsingId,parsingDescription;

    public RssParseHandler() {
        rssItems = new ArrayList<>();
    }

    public ArrayList<RssItem> getItems() {
        return rssItems;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //Global.showLog( "qName : " + qName+", local name: "+localName+  ", uri : "+uri+", attributes : "+attributes);
        if ("entry".equals(qName)) {
            currentItem = new RssItem();
        } else if ("title".equals(qName)) {
            parsingTitle = true;
        } else if ("id".equals(qName)) {
            parsingId = true;
        }else if ("media:description".equals(qName)) {
            parsingDescription = true;
        } else if("media:thumbnail".equals(qName)) {
            parsingThumbnail = true;
            readMediaUrl(attributes);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //Global.showLog("uri : "+uri+", localName : "+localName);
        if ("entry".equals(qName)) {
            rssItems.add(currentItem);
            currentItem = null;
        } else if ("title".equals(qName)) {
            parsingTitle = false;
        } else if ("id".equals(qName)) {
            parsingId = false;
        }else if ("media:description".equals(qName)) {
            parsingDescription = false;
        }else if("media:thumbnail".equals(qName)) {
            parsingThumbnail = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //Global.showLog("ch : "+ch+", start : "+start);
        if (parsingTitle) {
            if (currentItem != null) {
                String title = new String(ch, start, length);
                Global.showLog("title title : " + title);
                if(title.length()>0 && !title.equals(""))
                    currentItem.setTitle(new String(ch, start, length));
                else
                    currentItem.setTitle("");
            }
        } else if (parsingId) {
            if (currentItem != null) {
                currentItem.setId(new String(ch, start, length));
                parsingId = false;
            }
        } else if (parsingDescription) {
            if (currentItem != null) {
                currentItem.setDescription(new String(ch, start, length));
                parsingDescription = false;
            }
        }
    }

    private void readMediaUrl(Attributes attributes)
    {
        String url = attributes.getValue("url");
        //Global.showLog("Url Media : "+url);
        if(parsingThumbnail) {
            if (currentItem != null) {
                currentItem.setImageUrl(url);
                parsingThumbnail = false;
            }
        }
    }
}
