/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.easyxue.wx.wxtest.slt;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

/**
 *
 * @author MaYichao
 */
public class TokenHelloSvlt extends HttpServlet {

    private String token;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //重复通知过滤
    private static ExpireKey expireKey = new DefaultExpireKey();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); //To change body of generated methods, choose Tools | Templates.
        token = config.getInitParameter("token");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        String signature = request.getParameter("signature");
//        String timestamp = request.getParameter("timestamp");
//        String nonce = request.getParameter("nonce");
//        String echostr = request.getParameter("echostr");
//        PrintWriter out = response.getWriter();
//        try {
//
//            String s = SignatureUtil.generateEventMessageSignature("123456", timestamp, nonce);
//            if (s.equals(signature)) {
//                
//                out.println(echostr);
//            }else{
//                out.println("Who are you?");
//            }
//        } finally {
//            out.close();
//        }

        ServletInputStream inputStream = request.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        //首次请求申请验证,返回echostr
        if (echostr != null) {
            outputStreamWrite(outputStream, echostr);
            return;
        }

        //验证请求签名
        if (!signature.equals(SignatureUtil.generateEventMessageSignature(token, timestamp, nonce))) {
            System.out.println("The request signature is invalid");
            return;
        }

        if (inputStream != null) {
            //转换XML
            EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);
            String key = eventMessage.getFromUserName() + "__"
                    + eventMessage.getToUserName() + "__"
                    + eventMessage.getMsgId() + "__"
                    + eventMessage.getCreateTime();
            if (expireKey.exists(key)) {
                //重复通知不作处理
                return;
            } else {
                expireKey.add(key);
            }

            //创建回复
            XMLMessage xmlTextMessage = new XMLTextMessage(
                    eventMessage.getFromUserName(),
                    eventMessage.getToUserName(),
                    "你好");
            //回复
            xmlTextMessage.outputStreamWrite(outputStream);
            return;
        }
        outputStreamWrite(outputStream, "");

    }

//    private String parseRequest(HttpServletRequest request){
//        
//    }
    /**
     * 数据流输出
     *
     * @param outputStream
     * @param text
     * @return
     */
    private boolean outputStreamWrite(OutputStream outputStream, String text) {
        try {
            outputStream.write(text.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
