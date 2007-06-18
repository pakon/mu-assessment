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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Invalidate web session and redirect to welcome page.
 */
public class LogoutServlet extends HttpServlet
{
    private static final Logger log = Logger.getLogger(LogoutServlet.class);

    /**
     * Receives standard HTTP requests from the public service method
     * and dispatches them to the doXXX methods defined in this class.
     */
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        // invalidates web session and unbinds any objects bound to it
        httpServletRequest.getSession().invalidate();
        log.info("User logout");

        // redirect to welcome page
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/welcome");
    }
}
