package com.domineer.triplebro.booklistener.utils;

import com.domineer.triplebro.booklistener.beans.VoiceInfo;
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

public class VoiceInfoParser extends DefaultHandler {

    private List<VoiceInfo> voiceInfoList;
    private VoiceInfo voiceInfo;

    private StringBuilder voice_type_id;
    private StringBuilder voice_name;
    private StringBuilder voice_path;
    private StringBuilder voice_image;
    private StringBuilder author;
    private StringBuilder time;

    private String nodeName;

    public List<VoiceInfo> parseXML(InputStream in) throws
            ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(in, this);
        return voiceInfoList;
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if (localName.equals("VoiceInfoList")) {
            voiceInfoList = new ArrayList<VoiceInfo>();
            voice_type_id = new StringBuilder();
            voice_name = new StringBuilder();
            voice_path = new StringBuilder();
            voice_image = new StringBuilder();
            author = new StringBuilder();
            time = new StringBuilder();
        } else if (localName.equals("voiceInfo")) {
            voiceInfo = new VoiceInfo();
            String value = attributes.getValue(0);
            voiceInfo.set_id(Integer.parseInt(value));
        }
        nodeName = localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {
        super.endElement(uri, localName, qName);
        if (localName.equals("voiceInfo")) {
            voiceInfoList.add(voiceInfo);
        } else if (localName.equals("voice_type_id")) {
            voiceInfo.setTypeId(Integer.parseInt(voice_type_id.toString().trim()));
            voice_type_id.setLength(0);
        } else if (localName.equals("voice_name")) {
            voiceInfo.setVoiceName(voice_name.toString().trim());
            voice_name.setLength(0);
        } else if (localName.equals("voice_path")) {
            voiceInfo.setVoicePath(voice_path.toString().trim());
            voice_path.setLength(0);
        } else if (localName.equals("voice_image")) {
            voiceInfo.setVoiceImage(voice_image.toString().trim());
            voice_image.setLength(0);
        } else if (localName.equals("author")) {
            voiceInfo.setAuthor(author.toString().trim());
            author.setLength(0);
        } else if (localName.equals("time")) {
            voiceInfo.setTime(Integer.parseInt(time.toString().trim()));
            time.setLength(0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws
            SAXException {
        super.characters(ch, start, length);
        if (nodeName.equals("voice_type_id")) {
            voice_type_id.append(ch, start, length);
        }else if (nodeName.equals("voice_name")) {
            voice_name.append(ch, start, length);
        }else if (nodeName.equals("voice_image")) {
            voice_image.append(ch, start, length);
        }else if (nodeName.equals("voice_path")) {
            voice_path.append(ch, start, length);
        }else if (nodeName.equals("author")) {
            author.append(ch, start, length);
        }else if (nodeName.equals("time")) {
            time.append(ch, start, length);
        }
    }
}

