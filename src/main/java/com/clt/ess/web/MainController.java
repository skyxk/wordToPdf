package com.clt.ess.web;

import com.clt.ess.dao.IConvertLogDao;
import com.clt.ess.dao.ISetupDao;
import com.clt.ess.dao.ISysInfoDao;

import com.clt.ess.entity.ConvertLog;
import com.clt.ess.entity.SysInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.clt.ess.web.saveRequest.downLoadFromUrl;
import static com.sun.xml.internal.ws.util.JAXWSUtils.getUUID;

@Controller
@RequestMapping("") // url:/模块/资源/{id}/细分 /seckill/list
public class MainController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;


    @Autowired
    private IConvertLogDao convertLogDao;
    @Autowired
    private ISysInfoDao sysInfoDao;
    @Autowired
    private ISetupDao setupDao;
    /**
     * 每次拦截到请求会先访问此函数，
     * 获取request,session,response等实例
     * @param request http请求
     * @param response http回应
     */
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    @RequestMapping(value = "/createConvertLog_1.html", method = RequestMethod.GET)
    @ResponseBody
    private String createConvertLog_1(String fileName,String systemId){
        String result = "ESSB@4@ESSE";
        SysInfo sysInfo = sysInfoDao.findSysInfoById(systemId);
        String uuid = getUUID();
        if (sysInfo!=null){
            String pdfPath = sysInfo.getFtppath() +"/pdf/"+uuid+".pdf";
            String wordPath = sysInfo.getFtppath() +"/word/"+fileName;
            ConvertLog convertLog = new ConvertLog();
            convertLog.setPdfpath(pdfPath);
            convertLog.setWordpath(wordPath);
            convertLog.setConvertstatus("ESSB@0@ESSE");
            int a = convertLogDao.addConvertLog(convertLog);
            result = "ESSB@"+ convertLog.getFid() +"@ESSE";
        }
        return result;
    }
    @RequestMapping(value = "/createConvertLog.html", method = RequestMethod.POST)
    @ResponseBody
    private String createConvertLog(String fileUrl,String systemId){
        String result = "ESSB@0@ESSE";
        SysInfo sysInfo = sysInfoDao.findSysInfoById(systemId);
        String uuid = getUUID();
        String pdfPath = sysInfo.getFtppath() +"/pdf/"+uuid+".pdf";
        String wordPath = sysInfo.getFtppath() +"/word/"+uuid+".doc";
        ConvertLog convertLog = new ConvertLog();
        convertLog.setPdfpath(pdfPath);
        convertLog.setWordpath(wordPath);
        convertLog.setConvertstatus("ESSB@0@ESSE");
        try {
            downLoadFromUrl(fileUrl,uuid+".doc",sysInfo.getFtppath()+"/word");
            int a = convertLogDao.addConvertLog(convertLog);
            result = "ESSB@"+ convertLog.getFid() +"@ESSE";
        } catch (IOException e) {
            e.printStackTrace();
            result = "ESSB@ERROR@ESSE";
        }
        return result;
//        int wait = 200;
//        int maxwait = setupDao.findMaxWait();
//
//        for(int i=0;i<100;i++){
//            ConvertLog convertLog1 = convertLogDao.findConvertLogById(convertLog.getFid());
//            if("ESSB@1@ESSE".equals(convertLog1.getConvertstatus())||"ESSB@0@ESSE".equals(convertLog1.getConvertstatus())){
//                if(wait<maxwait){
//                    sleep(wait);
//                    wait =wait+200;
//                }else{
//                    result = "ESSB@4@ESSE";
//                    break;
//                }
//                result = convertLog1.getConvertstatus();
//            }else{
//                result = convertLog1.getConvertstatus();
//                break;
//            }
//        }
//        return result;
    }
    @RequestMapping(value = "/queryConvertState.html", method = RequestMethod.GET)
    @ResponseBody
    private String queryConvertState(int fid){
        String result = "";
        ConvertLog convertLog = convertLogDao.findConvertLogById(fid);
        if(convertLog==null){
            result = "ESSB@5@ESSE";
        }else{
            result = convertLog.getConvertstatus();
        }
        return result;
    }

    @RequestMapping(value = "/queryConvert.html", method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    @ResponseBody
    private String queryConvert() throws IOException {
        StringBuilder result = new StringBuilder();
        result.append("ESSB@");
        ConvertLog convertLog = convertLogDao.findConvertLogByState();
        if(convertLog!=null){
            result.append(convertLog.getFid()).append("@");
            result.append(convertLog.getWordpath()).append("@");
            result.append(convertLog.getPdfpath());
            convertLog.setConvertstatus("ESSB@1@ESSE");
            convertLogDao.updateConvertLog(convertLog);
        }
        result.append("@ESSE");
        return result.toString();
    }

    @RequestMapping(value = "/updateConvert.html", method = RequestMethod.POST)
    @ResponseBody
    private String updateConvert(int fid,String convertLogState){
        String result = "";
        ConvertLog convertLog = convertLogDao.findConvertLogById(fid);
        if(convertLog==null){
            result = "ESSB@5@ESSE";
        }else{
            convertLog.setConvertstatus(convertLogState);
            convertLogDao.updateConvertLog(convertLog);
            result = convertLog.getConvertstatus();
        }
        return result;
    }



}
