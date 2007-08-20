/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.demo.mu.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * TODO write comment
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class SourceFilesServlet extends HttpServlet
{
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        if (httpServletRequest.getParameter("file") != null) {
            getServletContext().getNamedDispatcher("SourceFileServlet").forward(httpServletRequest, httpServletResponse);
            return;
        }

        List<String> files = new ArrayList<String>();

        String currentPage = (String) httpServletRequest.getSession().getAttribute("currentPage");
        System.out.println(currentPage);
        
        if (currentPage.equals("/welcome/index.jsp")) {
            files.add("/org/apache/jackrabbit/demo/mu/servlets/MuServlet.java");
            files.add("/org/apache/jackrabbit/demo/mu/servlets/InitServlet.java");
        } else if (currentPage.equals("/testslist/index.jsp")) {
            files.add("/org/apache/jackrabbit/demo/mu/servlets/MuServlet.java");
            files.add("/org/apache/jackrabbit/demo/mu/servlets/TestListServlet.java");
        }

        httpServletRequest.setAttribute("files", files);
        httpServletRequest.getRequestDispatcher("/pages/SourceFiles.jsp").forward(httpServletRequest, httpServletResponse);
//        getServletContext().getRequestDispatcher("/pages/SourceFiles.jsp").forward(httpServletRequest, httpServletResponse);
    }
}