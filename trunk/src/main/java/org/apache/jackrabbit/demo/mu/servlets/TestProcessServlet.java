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

import org.apache.jackrabbit.demo.mu.exceptions.TestEndException;
import org.apache.jackrabbit.demo.mu.model.TestCommandType;
import org.apache.jackrabbit.demo.mu.model.TestProcess;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Process answers on previous question and forward to the nex one or to the results of test.
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class TestProcessServlet extends MuServlet
{
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        String command;
        if (httpServletRequest.getAttribute("newtest") != null) {
            command = "process";
        } else {
            command = httpServletRequest.getParameter("command");
        }

        switch (TestCommandType.valueOf(command)) {
            case abort:
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/tests");
                break;
            case process:
                try {
                    // login to repository
                    loginToRepository();

                    // get test process attribute
                    TestProcess testProcess = (TestProcess) httpServletRequest.getSession().getAttribute("process");

                    List<Integer> nubersOfGivenAnswers = new LinkedList<Integer>();
                    for (Object param : httpServletRequest.getParameterMap().keySet()) {
                        if (httpServletRequest.getParameter((String) param).equals("on")) {
                            // in case checkboxes
                            nubersOfGivenAnswers.add(Integer.valueOf(((String) param).substring(6)));
                        } else if (((String) param).startsWith("answer")) {
                            // in case radio buttons
                            nubersOfGivenAnswers.add(Integer.valueOf(httpServletRequest.getParameter((String) param).substring(6)));
                        }
                    }

                    // process given answers
                    testProcess.processGivenAnswers(nubersOfGivenAnswers);

                    // give next question
                    httpServletRequest.setAttribute("multiple", testProcess.isMultiple());
                    httpServletRequest.setAttribute("question", testProcess.nextQuestion());
                    httpServletRequest.setAttribute("position", testProcess.getQuestionNumber());

                    // forward to next question
                    getServletContext().getRequestDispatcher("/pages/ProcessQuestion.jsp").forward(httpServletRequest, httpServletResponse);

                } catch (TestEndException e) {
                    // remove possible setted web session attributes
                    httpServletRequest.removeAttribute("question");
                    httpServletRequest.removeAttribute("position");
                    httpServletRequest.removeAttribute("multiple");

                    // forward to the test results
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/end-test");
                } catch (RepositoryException e) {
                    // remove possible setted web session attributes
                    httpServletRequest.removeAttribute("question");
                    httpServletRequest.removeAttribute("position");
                    httpServletRequest.removeAttribute("multiple");

                    // redirect to the error page
                    httpServletRequest.getSession().setAttribute("errorDescription", "Can't process question.");
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error");
                } finally {
                    // don't forget loguot
                    session.logout();
                }
                break;
        }
    }
}
