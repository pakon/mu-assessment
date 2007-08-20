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

import org.apache.jackrabbit.core.NodeImpl;
import org.apache.jackrabbit.demo.mu.model.QuestionPackage;

import javax.jcr.RepositoryException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.query.QueryResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

/**
 * TODO write comment
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class PackageDownloadServlet extends MuServlet
{

    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        try {
            // login to repository
            loginToRepository();

            String packageId = httpServletRequest.getParameter("id");

            // construct query
            String queryString = MessageFormat.format("select * from mu:questionPackage where jcr:uuid = ''{0}''", packageId);

            // execute query
            QueryResult queryResult = session.getWorkspace().getQueryManager().createQuery(queryString, "sql").execute();

            NodeIterator iterator = queryResult.getNodes();
            if (iterator.hasNext()) {
                // get node represent package
                Node node = iterator.nextNode();

                String path = node.getPath();
                OutputStream outputStream = httpServletResponse.getOutputStream();

                //
                httpServletResponse.setContentType("text/xml");

                //
                session.exportSystemView(path, outputStream, true, false);
            }

        } catch (RepositoryException e) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error");
        } finally {
            // don't forget loguot
            session.logout();
        }
    }
}
