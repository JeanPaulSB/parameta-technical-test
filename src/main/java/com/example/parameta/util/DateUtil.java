package com.example.parameta.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil
{
    public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
        if (date == null) return null;
        try {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException("Error converting Date to XMLGregorianCalendar", e);
        }
    }

    public static Date fromXMLGregorianCalendar(XMLGregorianCalendar xmlCalendar) {
        if (xmlCalendar == null) return null;
        return xmlCalendar.toGregorianCalendar().getTime();
    }
}
