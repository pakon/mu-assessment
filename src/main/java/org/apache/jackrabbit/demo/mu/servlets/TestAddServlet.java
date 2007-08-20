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

import org.apache.jackrabbit.demo.mu.dao.QuestionPackageDao;
import org.apache.jackrabbit.demo.mu.dao.TopicDao;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class TestAddServlet extends MuServlet
{
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        try {
            // login to repository
            loginToRepository();

            QuestionPackageDao questionPackageDao = new QuestionPackageDao(session);
            TopicDao topicDao = new TopicDao(session);

            httpServletRequest.setAttribute("packages", questionPackageDao.getQuestionPackages());
            httpServletRequest.setAttribute("topics", topicDao.getTopics());

            getServletContext().getRequestDispatcher("/pages/AddNewTest.jsp").forward(httpServletRequest, httpServletResponse);

        } catch (RepositoryException e) {
            // todo обработать исключения
            throw new ServletException(e);
        } finally {
            // don't forget loguot
            session.logout();
        }
    }
}
