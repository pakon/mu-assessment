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
import org.apache.jackrabbit.demo.mu.dao.QuestionPackageDao;
import org.apache.jackrabbit.demo.mu.model.QuestionPackage;

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
public class PackagesServlet extends MuServlet
{
    private static final Logger log = Logger.getLogger(PackagesServlet.class);

    /**
     */
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        try {
            // login to repository
            loginToRepository();

            // create DAO for QuestionPackages
            QuestionPackageDao dao = new QuestionPackageDao(session);

            // get list of all avaible question packages
            List<QuestionPackage> packages = dao.getQuestionPackages();
            httpServletRequest.getSession().setAttribute("questionPackagesList", packages);

            // redirect to question packages list
            httpServletRequest.getRequestDispatcher("/pages/Packages.jsp").forward(httpServletRequest, httpServletResponse);

        } catch (RepositoryException e) {
            log.error("Error while getting question-packages list");

            // remove possible setted web session attributes
            httpServletRequest.getSession().removeAttribute("questionPackagesList");

            // redirect to error page
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error");
        } finally {
            // don't forget loguot
            session.logout();
        }
    }
}
