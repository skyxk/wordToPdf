package com.clt.ess.entity;

public class ConvertLog {
    private int fid;
    private String wordpath;
    private String pdfpath;
    private String convertstatus;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getWordpath() {
        return wordpath;
    }

    public void setWordpath(String wordpath) {
        this.wordpath = wordpath;
    }

    public String getPdfpath() {
        return pdfpath;
    }

    public void setPdfpath(String pdfpath) {
        this.pdfpath = pdfpath;
    }

    public String getConvertstatus() {
        return convertstatus;
    }

    public void setConvertstatus(String convertstatus) {
        this.convertstatus = convertstatus;
    }
}
