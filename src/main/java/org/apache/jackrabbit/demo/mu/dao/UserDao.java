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
package org.apache.jackrabbit.demo.mu.dao;

import org.apache.jackrabbit.demo.mu.model.User;
import org.apache.log4j.Logger;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.QueryResult;
import java.util.ArrayList;
import java.util.List;

/**
 * Realize repository data manipulation mechanism for <code>User</code>.
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class UserDao extends GenericDao
{
    /**
     * Default constructor. Set repository session.
     *
     * @param session repository session
     */
    public UserDao(Session session)
    {
        super(session);
    }

    /**
     * Returns the <code>User</code> object by given <code>Node</code> object.
     *
     * @param userNode contains user data
     * @return coresponded <code>User</code> object
     * @throws RepositoryException if some repository operation fail
     */
    public User getUser(Node userNode) throws RepositoryException
    {
        // get users properties from node
        String fullName = userNode.getProperty("mu:fullName").getString();
        String login = userNode.getProperty("mu:login").getString();
        String password = userNode.getProperty("mu:password").getString();

        return new User(fullName, login, password);
    }

    /**
     * Returns list of all users in mu-assessment system.
     *
     * @return list of users
     * @throws RepositoryException if some repository operation fail
     */
    public List<User> getUsers() throws RepositoryException
    {
        // create empty list
        List<User> users = new ArrayList<User>();

        // construct query
        String queryString = "select * from mu:user";

        // execute query
        QueryResult result = session.getWorkspace().getQueryManager().createQuery(queryString, "sql").execute();

        // add users list
        while (result.getNodes().hasNext()) {
            Node userNode = result.getNodes().nextNode();
            users.add(getUser(userNode));
        }

        return users;
    }
}
