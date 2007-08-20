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
import org.apache.commons.io.FileUtils;

import javax.jcr.*;
import javax.jcr.observation.Event;
import javax.jcr.observation.ObservationManager;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Date;
import java.text.MessageFormat;

/**
 * The InitServlet register namespace and nodetypes, import prepared data for repository
 * needed for mu-assessment demo application.
 */
public class InitServlet extends MuServlet
{
    private static final Logger log = Logger.getLogger(InitServlet.class);

    private Session observationSession;

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

            // register repository observer
            registerObservers();

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

        } catch (IOException e) {
            log.error("Error while importing prepared data from " + dataFileLocation);
            throw new InitializationException(e);
        } catch (RepositoryException e) {
            log.error("Error while importing prepared data from " + dataFileLocation);
            throw new InitializationException(e);
        }
    }

    private void registerObservers()
    {
        final String basePath = getServletContext().getRealPath("/");
        log.info("Observation support is: " + repository.getDescriptor(Repository.OPTION_OBSERVATION_SUPPORTED));
        try {
            observationSession = repository.login(new SimpleCredentials("jackrabbit", "jackrabbit".toCharArray()));
            ObservationManager om = observationSession.getWorkspace().getObservationManager();
            om.addEventListener(
                    new EventListener()
                    {
                        private LogWriter logger = new LogWriter(basePath);

                        public void onEvent(EventIterator eventIterator)
                        {
                            while (eventIterator.hasNext()) {
                                Event muEvent = eventIterator.nextEvent();
                                try {
                                    Node added = (Node) observationSession.getItem(muEvent.getPath());
                                    logger.writeLog(added.getUUID(), added.getProperty("mu:title").getString());
                                } catch (RepositoryException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, Event.NODE_ADDED, "/mu-root/tests", true, null, null, false
            );
        } catch (RepositoryException e) {
            e.printStackTrace();
            log.error("Error while registring observers, repository not suppotr it");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error while writing into log file");
        }
    }

    class LogWriter
    {
        private static final String LOG_FILENAME = "observation.log";

        private File logFile;


        public LogWriter(String basePath) throws IOException
        {
            logFile = new File(basePath + System.getProperty("file.separator") + LOG_FILENAME);
            if (!logFile.exists()) FileUtils.touch(logFile);
        }

        public synchronized void writeLog(String testId, String title)
        {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
                writer.append(MessageFormat.format("<li>New test <a href=\"begin-test?id={0}\"> \"{2}\"</a> was created at {1} </li>", testId, new Date(), title));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error("Error while writing into log file");
            }
        }
    }
}
