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

import org.apache.jackrabbit.demo.mu.dao.TopicDao;
import org.apache.jackrabbit.demo.mu.model.Topic;
import org.apache.log4j.Logger;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Put tests list to web session and forward to tests list page.
 */
public class TestsServlet extends MuServlet
{
    private static final Logger log = Logger.getLogger(TestsServlet.class);

    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        try {
            // login to repository
            loginToRepository();

            TopicDao topicDao = new TopicDao(session);
            List<Topic> topics = topicDao.getTopics();

            // put tests list to web session
            httpServletRequest.setAttribute("topics", topics);

            // forward to tests list page
            getServletContext().getRequestDispatcher("/pages/Tests.jsp").forward(httpServletRequest, httpServletResponse);
        } catch (RepositoryException e) {
            log.error("Can't select tests from repository");
            throw new ServletException(e);
        } finally {
            // don't forget loguot
            session.logout();
        }
    }
}
