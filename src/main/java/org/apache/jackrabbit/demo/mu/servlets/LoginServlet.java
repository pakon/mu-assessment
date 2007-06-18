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

import org.apache.log4j.Logger;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.query.QueryResult;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * Verify users login and password. If user sucessfully login forward to test list,
 * else redirect to error page.
 */
public class LoginServlet extends MuServlet
{
    private static final Logger log = Logger.getLogger(InitServlet.class);

    /**
     * Receives standard HTTP requests from the public service method
     * and dispatches them to the doXXX methods defined in this class.
     */
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        try {
            // login to repository
            loginToRepository();

            // get parameters from login form
            String name = httpServletRequest.getParameter("login");
            String password = httpServletRequest.getParameter("password");

            // construct query
            String queryString = MessageFormat.format("select * from mu:user where mu:login = ''{0}'' and mu:password = ''{1}'' and jcr:path like ''/mu-root/users[%]/%'' ", name, password);

            // execute query
            QueryResult result = session.getWorkspace().getQueryManager().createQuery(queryString, "sql").execute();

            if (result.getNodes().hasNext()) {
                // get selected user as node
                Node user = (Node) result.getNodes().next();

                // put user data to web session
                // TODO put to session User bean instead Node
                httpServletRequest.getSession().setAttribute("user", user);
                httpServletRequest.getSession().setAttribute("user_name", user.getProperty("mu:fullName").getString());

                log.info("User login: " + httpServletRequest.getSession().getAttribute("user_name"));

                // succefully login and redirect to tests list
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/testlist");
            } else {

                System.out.println("Bad pair login/password");
                log.info("Bad pair login/password");
                // unsuccefully login and redirect to error page
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error.jsp");
            }

        } catch (RepositoryException e) {
            System.out.println("Bad pair login/password");
            log.error("Error while login");

            // remove possible setted web session attributes
            httpServletRequest.getSession().removeAttribute("user");
            httpServletRequest.getSession().removeAttribute("user_name");

            // unsuccefully login and redirect to error page
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error.jsp");
            throw new ServletException(e);
        } finally {
            // don't forget loguot
            session.logout();
        }
    }
}
