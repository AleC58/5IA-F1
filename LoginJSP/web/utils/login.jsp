<%-- 
    Document   : login
    Created on : 3-giu-2019, 9.08.17
    Author     : nicot
--%>
<%@page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.DataOutputStream"%>
<%@page import="java.net.URLConnection"%>
<%@page import="java.net.URL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="java.security.MessageDigest"%>
<%
    URL url = new URL("http://192.168.4.64:9090/authenticate");

    URLConnection urlConn = url.openConnection();
    urlConn.setDoInput(true);
    urlConn.setDoOutput(true);
    urlConn.setUseCaches(false);
    urlConn.setRequestProperty("Content-Type",
            "application/x-www-form-urlencoded");
    DataOutputStream cgiInput = new DataOutputStream(urlConn.getOutputStream());

    String content = "username=" + URLEncoder.encode("prova")
            + "&password="
            + URLEncoder.encode(DigestUtils.sha256Hex("123"));
    cgiInput.writeBytes(content);
    cgiInput.flush();
    cgiInput.close();
    BufferedReader cgiOutput
            = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
    PrintWriter servletOutput = response.getWriter();
    servletOutput.print("<html><body><h1>This is the Source Servlet</h1><p />");
    String line = null;
    while (null != (line = cgiOutput.readLine())) {
        servletOutput.println(line);
    }
    cgiOutput.close();
    servletOutput.print("</body></html>");
    servletOutput.close();
%>
