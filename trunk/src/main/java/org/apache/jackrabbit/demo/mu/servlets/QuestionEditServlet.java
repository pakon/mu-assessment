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

import org.apache.jackrabbit.demo.mu.dao.QuestionDao;
import org.apache.jackrabbit.demo.mu.model.Question;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.jcr.RepositoryException;
import java.io.IOException;

/**
 * Prepare data for editing the question.
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class QuestionEditServlet extends MuServlet
{
    private static final Logger log = Logger.getLogger(InitServlet.class);

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        try {
            loginToRepository();

            String id = httpServletRequest.getParameter("id");
            QuestionDao dao = new QuestionDao(session);
            Question question = dao.getQuestion(id);
            String packageId = dao.getQuestionParentId(id);

            httpServletRequest.getSession().setAttribute("question", question);
            httpServletRequest.getSession().setAttribute("packageId", packageId);

            // forward to the question edit form
            httpServletRequest.getRequestDispatcher("/pages/EditQuestion.jsp").forward(httpServletRequest, httpServletResponse);

        } catch (RepositoryException e) {
            log.error("Error while retrive question info");

            // remove possible setted web session attributes
            httpServletRequest.getSession().removeAttribute("question");
            httpServletRequest.getSession().removeAttribute("packageId");

            // redirect to error page
            httpServletRequest.getSession().setAttribute("errorDescription", "Can't process question.");
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error");
        } finally {
            // don't forget loguot
            session.logout();
        }
    }
}
