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

import javax.jcr.Session;

/**
 * Base class for all DAO classes in org.apache.jackrabbit.demo.mu.dao package.
 * It provide access to the <code>Session</code> object used by all DAO objects.
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class GenericDao
{
    /**
     * Repository session.
     */
    protected Session session;

    /**
     * Assign session parameter to session field.
     */
    public GenericDao(Session session)
    {
        this.session = session;
    }
}
