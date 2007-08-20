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
import org.apache.jackrabbit.demo.mu.model.Answer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.jcr.RepositoryException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * TODO write comment
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class QuestionUpdateServlet extends MuServlet
{

    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        try {
            // login to repository
            loginToRepository();

            String qestionText = httpServletRequest.getParameter("questionText");
            int weight = Integer.parseInt(httpServletRequest.getParameter("weight"));

            Question sourceQuestion = (Question) httpServletRequest.getSession().getAttribute("question");

            Question newQuestion = new Question();
            newQuestion.setText(qestionText);
            newQuestion.setWeight(weight);
            newQuestion.setId(sourceQuestion.getId());

            // todo add answers
            List<Integer> nubersOfGivenAnswers = new LinkedList<Integer>();
            for (Object param : httpServletRequest.getParameterMap().keySet()) {
                if (httpServletRequest.getParameter((String) param).equals("on")) {
                    // in case checkboxes
                    nubersOfGivenAnswers.add(Integer.valueOf(((String) param).substring(6)));
                }
            }
            for (int i = 0; i < sourceQuestion.getAnswers().size(); ++i) {
                Answer answer = new Answer(sourceQuestion.getAnswers().get(i).getText(), nubersOfGivenAnswers.contains(i));
                newQuestion.getAnswers().add(answer);
            }

            QuestionDao questionDao = new QuestionDao(session);
            questionDao.updateQuestion(newQuestion);

            // forward to packages list
            httpServletRequest.getRequestDispatcher("/packages").forward(httpServletRequest, httpServletResponse);

        } catch (RepositoryException e) {
            // redirect to the error page
            httpServletRequest.getSession().setAttribute("errorDescription", "Can't process question.");
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error");
        } finally {
            // don't forget logout
            session.logout();
        }
    }
}
