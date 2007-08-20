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

import org.apache.jackrabbit.demo.mu.model.QuestionPackage;
import org.apache.jackrabbit.demo.mu.model.Question;
import org.apache.log4j.Logger;

import javax.jcr.*;
import javax.jcr.query.QueryResult;
import java.util.List;
import java.util.ArrayList;
import java.text.MessageFormat;

/**
 * Realize repository data manipulation mechanism for <code>QuestionPackage</code>.
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class QuestionPackageDao extends GenericDao
{
    private static final Logger log = Logger.getLogger(QuestionPackageDao.class);

    /**
     * Default constructor. Set repository session.
     *
     * @param session repository session
     */
    public QuestionPackageDao(Session session)
    {
        super(session);
    }

    /**
     * Returns the <code>QuestionPackage</code> object by given <code>Node</code> object.
     *
     * @param node contains question packages data
     * @return coresponded <code>QuestionPackage</code> object
     * @throws RepositoryException if some repository operation fail
     */
    public QuestionPackage getQuestionPackage(Node node) throws RepositoryException
    {
        // create new package
        QuestionPackage pack = new QuestionPackage();

        // assign packages fileds by values from node
        pack.setDate(node.getProperty("mu:date").getString());
        pack.setAuthor(node.getProperty("mu:author").getString());
        pack.setId(node.getUUID());

        // create DAO object for question
        QuestionDao questionDao = new QuestionDao(session);

        // add questions into package
        NodeIterator iterator = node.getNodes();
        while (iterator.hasNext()) {
            Question question = questionDao.getQuestion(iterator.nextNode());
            pack.addQuestion(question);
        }

        return pack;
    }

    /**
     * Return all avaible question packages.
     *
     * @return list of <code>QuestionPackage</code> objects
     * @throws RepositoryException if some repository operation fail
     */
    public List<QuestionPackage> getQuestionPackages() throws RepositoryException
    {
        List<QuestionPackage> questionPackages = new ArrayList<QuestionPackage>();

        // construct query
        String queryString = "select * from mu:questionPackage";

        // execute query
        QueryResult queryResult = session.getWorkspace().getQueryManager().createQuery(queryString, "sql").execute();

        NodeIterator iterator = queryResult.getNodes();
        while (iterator.hasNext()) {
            // get node represent package
            Node node = iterator.nextNode();

            // create new question package
            QuestionPackage pack = getQuestionPackage(node);

            // add package to result packages list
            questionPackages.add(pack);
        }

        return questionPackages;
    }

    /**
     * Delete question package from repository if no one question not used in any test.
     *
     * @param packageId uuid of node
     * @throws RepositoryException  if some repository operation fail
     */
    public void deletePackage(String packageId) throws RepositoryException
    {
        // construct query
        String queryString = MessageFormat.format("select * from mu:questionPackage where jcr:uuid = ''{0}''", packageId);

        // execute query
        QueryResult queryResult = session.getWorkspace().getQueryManager().createQuery(queryString, "sql").execute();

        // remove question package node if no references to it
        NodeIterator iterator = queryResult.getNodes();
        while (iterator.hasNext()) {
            Node questionPackageNode = iterator.nextNode();
            if (!questionPackageNode.getReferences().hasNext()) {
                questionPackageNode.remove();
            }
        }

        // apply changes
        session.save();

        log.debug("Package " + packageId + " was deleted");
    }
}
