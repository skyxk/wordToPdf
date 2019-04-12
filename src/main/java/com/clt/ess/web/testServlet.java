package com.clt.ess.web;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class testServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        doPost(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        // 设置响应内容类型
        response.setContentType("text/html");

        String fid = request.getParameter("fid");
        String convertLogState = request.getParameter("convertLogState");

        update(fid,convertLogState);

        // 实际的逻辑是在这里
        PrintWriter out = response.getWriter();
        out.println(convertLogState);
    }




    private static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://192.144.176.134:3306/wordtopdf?useSSL=true&useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "cltess";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private static int update(String fid,String convertLogState) {
        Connection conn = getConn();
        int i = 0;
        String sql = "update convertlog set convertstatus='" + convertLogState + "' where fid=" + fid + "";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
}
