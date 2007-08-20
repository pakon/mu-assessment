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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.jcr.RepositoryException;
import java.io.IOException;
import java.util.List;

/**
 * TODO write comment
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class PackageServlet extends MuServlet
{

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        try {
            // login to repository
            loginToRepository();

            // get question package id
            String packageId = httpServletRequest.getParameter("id");

            QuestionDao dao = new QuestionDao(session);
            
            List<Question> questions = dao.getQuestions(packageId);

            httpServletRequest.getSession().setAttribute("packageId", packageId);
            httpServletRequest.getSession().setAttribute("questions", questions);

            // redirect to question packages list
            httpServletRequest.getRequestDispatcher("/pages/Questions.jsp").forward(httpServletRequest, httpServletResponse);
        } catch (RepositoryException e) {
            // todo handle exception
            e.printStackTrace();
        } finally {
            // don't forget loguot
            session.logout();
        }
    }
}
