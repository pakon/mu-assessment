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

import org.apache.jackrabbit.servlet.ServletRepository;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.jcr.*;

/**
 * Base servlet for all servlets in mu-assessment demo application.
 */
public class MuServlet  extends HttpServlet
{
    private static final Logger log = Logger.getLogger(MuServlet.class);

    /**
     * Proxy for a repository bound in servlet context.
     *
     * @see org.apache.jackrabbit.servlet.ServletRepository
     */
    protected final Repository repository = new ServletRepository(this);

    /**
     * Jackrabbit session.
     */
    protected Session session = null;

    /**
     * Assign new session by logining to the repository.
     *
     * @throws RepositoryException throws when login error occured.
     */
    protected void loginToRepository() throws RepositoryException
    {
        // login to repository with non anonimous credentials to have write access
        session = repository.login(new SimpleCredentials("jackrabbit", "jackrabbit".toCharArray()));
        log.info("Login to repository");
    }
}
