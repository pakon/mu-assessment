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

import org.apache.jackrabbit.demo.mu.dao.TestDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.jcr.RepositoryException;
import java.io.IOException;

/**
 * TODO write comment
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class TestDeleteServlet extends MuServlet
{

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        try {
            //login to repository
            loginToRepository();

            // get id parameter
            String testId = httpServletRequest.getParameter("id");

            // delete test by id
            TestDao testDao = new TestDao(session);
            testDao.deleteTest(testId);

            // redirect to packages list
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/tests");

        } catch (RepositoryException e) {
            // redirect to error page
            // todo передать сообщение об ошибке
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error");
        } finally {
            // don't forget logout
            session.logout();
        }
    }
}
