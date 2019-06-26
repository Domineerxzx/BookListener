package com.domineer.triplebro.booklistener.utils;

import com.domineer.triplebro.booklistener.beans.VoiceTypeInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class VoiceTypeInfoParser extends DefaultHandler{

    private List<VoiceTypeInfo> voiceTypeInfoList;
    private VoiceTypeInfo voiceTypeInfo;

    private StringBuilder voice_type_name;

    private String nodeName;

    public List<VoiceTypeInfo> parseXML(InputStream in) throws
            ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(in,this);
        return voiceTypeInfoList;
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if(localName.equals("VoiceTypeInfoList")){
            voiceTypeInfoList = new ArrayList<VoiceTypeInfo>();
            voice_type_name = new StringBuilder();
        }else if(localName.equals("voiceTypeInfo")) {
            voiceTypeInfo = new VoiceTypeInfo();
            String value = attributes.getValue(0);
            voiceTypeInfo.set_id(Integer.parseInt(value));
        }
        nodeName=localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {
        super.endElement(uri, localName, qName);
        if(localName.equals("voiceTypeInfo")){
            voiceTypeInfoList.add(voiceTypeInfo);
        }else if(localName.equals("voice_type_name")){
            voiceTypeInfo.setType_name(voice_type_name.toString().trim());
            voice_type_name.setLength(0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws
            SAXException {
        super.characters(ch, start, length);
        if(nodeName.equals("voice_type_name")){
            voice_type_name.append(ch,start,length);
        }
    }
}

