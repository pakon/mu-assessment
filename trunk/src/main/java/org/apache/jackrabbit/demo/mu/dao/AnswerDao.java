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

import org.apache.jackrabbit.demo.mu.model.Answer;
import org.apache.log4j.Logger;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * Realize repository data manipulation mechanism for <code>Answer</code>.
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class AnswerDao extends GenericDao
{
    /**
     * Default constructor. Set repository session.
     *
     * @param session repository session
     */
    public AnswerDao(Session session)
    {
        super(session);
    }

    /**
     * Returns <code>Answer</code> object from <code>Node</code>.
     *
     * @param node with path /mu-root/question-packages/question-package/question/answer
     * @return corresponded <code>Answer</code> object.
     * @throws RepositoryException if node has another path
     */
    public Answer getAnswer(Node node) throws RepositoryException
    {
        // get node properties
        String text = node.getProperty("mu:text").getString();
        boolean correct = node.getProperty("mu:correct").getBoolean();

        return new Answer(text, correct);
    }
}
