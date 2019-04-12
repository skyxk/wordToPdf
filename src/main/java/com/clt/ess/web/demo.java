package com.clt.ess.web;

import java.io.IOException;

import static com.clt.ess.web.saveRequest.downLoadFromUrl;

public class demo {

    public static void main(String[] args) throws IOException {
        downLoadFromUrl("http://10.41.0.66:8081/wordToPdf/1/1.doc","1.doc","d:\\");
    }

}
