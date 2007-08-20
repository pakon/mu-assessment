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

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.demo.DemoUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.jcr.*;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.lock.LockException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * TODO write comment
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class PackageUploadServlet extends MuServlet
{
    private static final Logger log = Logger.getLogger(PackageUploadServlet.class);

    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(httpServletRequest);

        // Create a factory for disk-based file items
        FileItemFactory factory = new DiskFileItemFactory();

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            // get uploaded file (only one filed in form -> index 0)
            FileItem fileItem = (FileItem) upload.parseRequest(httpServletRequest).get(0);

            // login to repository
            loginToRepository();

            // import new package into repository
            importQuestionPackage(fileItem);

            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/packages");

        } catch (FileUploadException e) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error");
        } catch (RepositoryException e) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error");
        } finally {
            // don't forget loguot
            session.logout();
        }
    }

    private void importQuestionPackage(FileItem fileItem) throws IOException, RepositoryException
    {
        InputStream inputStream = fileItem.getInputStream();
        session.getWorkspace().importXML("/mu-root/question-packages", inputStream, ImportUUIDBehavior.IMPORT_UUID_CREATE_NEW);

        inputStream.close();
    }

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        doPost(httpServletRequest, httpServletResponse);
    }
}
