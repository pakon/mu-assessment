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
package org.apache.jackrabbit.demo.mu.model;

/**
 * Represent the user of mu-assessment system.
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class User
{
    /**
     * The full user name. For example "Pavel Konnikov" or "System Manager" or "Google Inc."
     */
    private String fullName;

    /**
     * The user login.
     */
    private String login;

    /**
     * The user password.
     */
    private String password;

    /**
     * Constructor. Assign fullName, login and password.
     *
     * @param fullName full name of the user
     * @param login user login
     * @param password user password
     */
    public User(String fullName, String login, String password)
    {
        this.fullName = fullName;
        this.login = login;
        this.password = password;
    }


    /**
     * Returns the full user name as string.
     *
     * @return the full user name as string
     */
    public String getFullName()
    {
        return fullName;
    }

    /**
     * Set the full user name.
     *
     * @param fullName the full user name
     */
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    /**
     * Returns the user login.
     *
     * @return the user login
     */
    public String getLogin()
    {
        return login;
    }

    /**
     * Set the user login.
     *
     * @param login the user login
     */
    public void setLogin(String login)
    {
        this.login = login;
    }

    /**
     * Returns the user password.
     *
     * @return the user password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Set the user password.
     *
     * @param password the user password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
}
