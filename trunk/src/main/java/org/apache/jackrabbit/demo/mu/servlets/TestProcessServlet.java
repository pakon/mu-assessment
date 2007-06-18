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

import org.apache.jackrabbit.demo.mu.model.TestProcess;
import org.apache.jackrabbit.demo.mu.model.TestCommandType;
import org.apache.jackrabbit.demo.mu.exceptions.TestEndException;
import org.apache.jackrabbit.servlet.ServletRepository;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import java.io.IOException;

/**
 *
 */
public class TestProcessServlet extends HttpServlet {

    private final Repository repository = new ServletRepository(this);

    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String command;
        String number;
        if (httpServletRequest.getAttribute("newtest") != null) {
            command = "process";
            number = "0";
        } else {
            number = httpServletRequest.getParameter("number");
            command = httpServletRequest.getParameter("command");
        }
        switch (TestCommandType.valueOf(command)) {
            case abort:
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "index.jsp");
                break;
            case process:
                RequestDispatcher requestDispatcher;
                Session session = null;
                try {
                    session = repository.login();
                    TestProcess testprocess = (TestProcess) httpServletRequest.getSession().getAttribute("process");
                    httpServletRequest.setAttribute("questionSummary", testprocess.processPosition(
                            Long.valueOf(number), session));
                    requestDispatcher = this.getServletContext().getRequestDispatcher("/process/index.jsp");
                    requestDispatcher.forward(httpServletRequest, httpServletResponse);
                } catch (TestEndException e) {
                    requestDispatcher = this.getServletContext().getRequestDispatcher("/testend");
                    requestDispatcher.forward(httpServletRequest, httpServletResponse);
                } catch (LoginException e) {
                    e.printStackTrace();
                } catch (RepositoryException e) {
                    e.printStackTrace();
                } finally{
                  session.logout();
                }
                break;
        }
    }
}
