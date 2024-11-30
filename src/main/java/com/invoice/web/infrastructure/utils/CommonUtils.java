package com.invoice.web.infrastructure.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class CommonUtils {


    public static String generateInvoiceNumber() {

//        int randomNumber = ThreadLocalRandom.current().nextInt(100);
        DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
        Date date = new Date();
//        return String.valueOf(randomNumber).concat(dateFormat.format(date));
        return dateFormat.format(date);
    }
}

