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
import org.apache.log4j.Logger;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.query.QueryResult;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

/**
 *
 */
public class TestBeginServlet extends MuServlet
{
    private static final Logger log = Logger.getLogger(TestBeginServlet.class);

    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        String testName = httpServletRequest.getParameter("t_n");
        try {
            // login to repository
            loginToRepository();

            // construct query
            String queryString = MessageFormat.format("select * from mu:test where mu:title = ''{0}''  ", testName);

            // execute query
            QueryResult result = session.getWorkspace().getQueryManager().createQuery(queryString, "sql").execute();

            Node testNode = result.getNodes().nextNode();
            Value[] questionReferenceValues = testNode.getProperty("mu:questionrefs").getValues();

            TestProcess process = new TestProcess(testNode, questionReferenceValues);

            httpServletRequest.getSession().setAttribute("process", process);
            httpServletRequest.setAttribute("newtest", true);

            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/process");
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        } catch (RepositoryException e) {
            log.error("Can't process test: " + testName);
            
            throw new ServletException(e);
        } finally {
            session.logout();
        }
    }
}
