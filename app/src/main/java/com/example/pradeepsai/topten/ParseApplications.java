package com.example.pradeepsai.topten;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by pradeepsai on 3/26/16.
 */
public class ParseApplications {
    private String Xmldata;
    private ArrayList<Application> applications;

    public ParseApplications(String xmldata)
    {
        this.Xmldata = xmldata;
        this.applications = new ArrayList<>();
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public boolean process(){
        boolean status = true;
        String textvalue =null;
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.Xmldata));
            Application currentrecord=null;

            boolean isEntry=false;

            int eventype = xpp.getEventType();

            while(eventype!= XmlPullParser.END_DOCUMENT)
            {
                String tagname = xpp.getName();

                switch (eventype)
                {
                    case XmlPullParser.START_TAG:
                        if(tagname.equalsIgnoreCase("entry"))
                        {
                            Log.d("ParseApplication",tagname);
                            isEntry = true;
                            currentrecord = new Application();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textvalue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(isEntry)
                        {
                            if(tagname.equalsIgnoreCase("entry"))
                            {
                                applications.add(currentrecord);
                                isEntry = false;
                                break;
                            }
                            if(tagname.equalsIgnoreCase("name")){
                                currentrecord.setName(textvalue);
                                break;
                            }

                        }
                        Log.d("ParseApplication",tagname);
                        break;
                    default:
                        break;
                }
                eventype=xpp.next();
            }
        }
        catch (Exception e)
        {
            status = false;
        }
        return status;
    }

}
