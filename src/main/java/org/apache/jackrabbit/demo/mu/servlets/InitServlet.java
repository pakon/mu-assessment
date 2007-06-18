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

import org.apache.jackrabbit.api.JackrabbitNodeTypeManager;
import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.apache.jackrabbit.core.nodetype.NodeTypeDef;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeRegistry;
import org.apache.jackrabbit.core.nodetype.compact.CompactNodeTypeDefReader;
import org.apache.jackrabbit.core.nodetype.compact.ParseException;
import org.apache.jackrabbit.demo.mu.exceptions.InitializationException;
import org.apache.jackrabbit.demo.DemoUtils;
import org.apache.log4j.Logger;

import javax.jcr.*;
import javax.jcr.query.QueryResult;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.text.MessageFormat;

/**
 * The InitServlet register namespace and nodetypes, import prepared data for repository
 * needed for mu-assessment demo application.
 */
public class InitServlet extends MuServlet
{
    private static final Logger log = Logger.getLogger(InitServlet.class);

    /**
     * Receives standard HTTP requests from the public service method
     * and dispatches them to the doXXX methods defined in this class.
     */
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        try {
            // login to repository
            loginToRepository();

            // if node "mu-root" don't exist than mu-assesment run first
            if (!session.getRootNode().hasNode("mu-root")) {
                // register defining node types and making namespace declarations
                registerCnd("/WEB-INF/mu.cnd");
                // import prepared data for demo application from XML document
                importPreparedRepositoryData("/WEB-INF/imported-data.xml");
            }

            // succefully init and forward to welcome page
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/welcome");

        } catch (InitializationException e) {
            throw new ServletException(e);
        } catch (RepositoryException e) {
            throw new ServletException(e);
        } finally {
            // don't forget loguot
            session.logout();
        }
    }

    /**
     * Register namespce and CND definitions for mu-assessment application.
     *
     * @param cndLocation absolute path (in webapp context) to cnd file with mu-assessment definitions.
     * @throws InitializationException throws when registration not possible.
     */
    private void registerCnd(String cndLocation) throws InitializationException
    {
        try {
            // register demo application related namespace
            session.getWorkspace().getNamespaceRegistry().registerNamespace("mu", "http://www.konnikov.net/GSoC/2007/jackrabbit-jcr-demo/0.1");

            // create CND reader from file with CND definitions
            InputStream inputStream = getServletContext().getResourceAsStream(cndLocation);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CompactNodeTypeDefReader cndReader = new CompactNodeTypeDefReader(inputStreamReader, "mu.cnd");

            // get node type registry
            JackrabbitNodeTypeManager manager = (NodeTypeManagerImpl) session.getWorkspace().getNodeTypeManager();
            NodeTypeManagerImpl managerImpl = (NodeTypeManagerImpl) manager;
            NodeTypeRegistry nodeTypeRegistry = managerImpl.getNodeTypeRegistry();

            // register all definitions
            List<NodeTypeDef> definitions = cndReader.getNodeTypeDefs();
            for (NodeTypeDef definition : definitions) {
                nodeTypeRegistry.registerNodeType(definition);
            }

            log.info("mu.cnd registred");
        } catch (ParseException e) {
            log.error("Can't create CND reader for " + cndLocation);
            throw new InitializationException(e);
        } catch (InvalidNodeTypeDefException e) {
            log.error("Can't register node type from " + cndLocation);
            throw new InitializationException(e);
        } catch (RepositoryException e) {
            log.error("Error while registering definitions from " + cndLocation);
            throw new InitializationException(e);
        }
    }

    /**
     * Import prepared data for repository from system view xml document.
     *
     * @param dataFileLocation absolute path (in webapp context) to xml file with data in system view.
     * @throws InitializationException throws when import not possible.
     */
    private void importPreparedRepositoryData(String dataFileLocation) throws InitializationException
    {
        try {
            // import data from system view xml document located by dataFileLocation
            InputStream inputStream = getServletContext().getResourceAsStream(dataFileLocation);
            session.getWorkspace().importXML("/", inputStream, ImportUUIDBehavior.IMPORT_UUID_CREATE_NEW);
            inputStream.close();

            log.info("Prepared data for repository was imported");

            // print dump for "/mu-root" node
            Node muRootNode = session.getRootNode().getNode("mu-root");
            DemoUtils.dump(muRootNode);
        } catch (IOException e) {
            log.error("Error while importing prepared data from " + dataFileLocation);
            throw new InitializationException(e);
        } catch (RepositoryException e) {
            log.error("Error while importing prepared data from " + dataFileLocation);
            throw new InitializationException(e);
        }
    }
}
