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

import org.apache.jackrabbit.demo.mu.model.Test;
import org.apache.jackrabbit.demo.mu.model.Question;
import org.apache.jackrabbit.value.StringValue;
import org.apache.jackrabbit.value.ReferenceValue;
import org.apache.log4j.Logger;

import javax.jcr.*;
import javax.jcr.query.QueryResult;
import java.text.MessageFormat;
import java.util.List;

/**
 * Realize repository data manipulation mechanism for <code>Test</code>.
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class TestDao extends GenericDao
{
    private static final Logger log = Logger.getLogger(TestDao.class);

    /**
     * Default constructor. Set repository session.
     *
     * @param session repository session
     */
    public TestDao(Session session)
    {
        super(session);
    }

    /**
     * Returns the <code>Test</code> object by given <code>Node</code> object.
     *
     * @param testNode contains test data
     * @return corresponded <code>Test</code> object
     * @throws RepositoryException if some repository operation fail
     */
    public Test getTest(Node testNode) throws RepositoryException
    {
        // create new test
        Test test = new Test();

        // assign tests fileds by values from node
        test.setTitle(testNode.getProperty("mu:title").getString());
        test.setId(testNode.getProperty("jcr:uuid").getString());

        // get references on test questions
        Value [] references = testNode.getProperty("mu:questionrefs").getValues();

        // add questions to test
        QuestionDao questionDao = new QuestionDao(session);
        for (Value referance : references) {
            Question question = questionDao.getQuestion(referance.getString());
            test.addQuestion(question);
        }

        return test;
    }

    /**
     * Returns the <code>Test</code> object by given uuid.
     *
     * @param testId uuid of the test node
     * @return coresponded <code>Test</code> object
     * @throws RepositoryException if some repository operation fail
     */
    public Test getTest(String testId) throws RepositoryException
    {
        // construct query
        String queryString = MessageFormat.format("select * from mu:test where jcr:uuid = ''{0}''", testId);

        // execute query
        QueryResult queryResult = session.getWorkspace().getQueryManager().createQuery(queryString, "sql").execute();

        NodeIterator iterator = queryResult.getNodes();
        if (iterator.hasNext()) {
            // get test node
            Node testNode = iterator.nextNode();

            return getTest(testNode);
        } else {
            log.warn("Node with uuid = " + testId + " not exits");
            throw new RepositoryException("Node with uuid = " + testId + " not exits");
        }
    }

    /**
     * Add new test to the repository.
     *
     * @param title the test title
     * @param topicTheme the topic theme
     * @param questionRefs references on questions belong to the test
     * @throws RepositoryException if some repository operation fail
     */
    public void addTest(String title, String topicTheme, List<String> questionRefs) throws RepositoryException
    {
        // get tests node
        Node testsNode = session.getRootNode().getNode("mu-root/tests");

        // add new test node
        Node newTestNode = testsNode.addNode("test", "mu:test");

        // set test title
        newTestNode.setProperty("mu:title", title);

        // add references on questions
        Value[] values = new Value[questionRefs.size()];
        for (int i = 0; i < questionRefs.size(); ++i) {
//            values[i] = session.getValueFactory().createValue(session.getNodeByUUID(questionRef));
            values[i] = new StringValue(questionRefs.get(i));
        }
        newTestNode.setProperty("mu:questionrefs", values);

        // add test to topic
        addTestToTopic(newTestNode, topicTheme);

        // apply changes
        session.save();
    }

    /**
     * Add new test referance to the topic.
     *
     * @param testNode new referenced test node
     * @param topicTheme theme of the topic
     * @throws RepositoryException if some repository operation fail
     */
    public void addTestToTopic(Node testNode, String topicTheme) throws RepositoryException
    {
        // get topic DAO
        TopicDao topicDao = new TopicDao(session);

        // get topic node
        Node topicNode = topicDao.getTopicNode(topicTheme);

        // create new references
        Value [] oldReferences = topicNode.getProperty("mu:testrefs").getValues();
        Value [] newReferences = new Value[oldReferences.length + 1];
        System.arraycopy(oldReferences, 0, newReferences, 0, oldReferences.length);

        // add new reference on new test
        newReferences[newReferences.length - 1] = new ReferenceValue(testNode);

        // assign new references to the topic
        topicNode.setProperty("mu:testrefs", newReferences);
    }

    /**
     * Delete test from repository by test uuid.
     *
     * @param testId uuid of the test
     * @throws RepositoryException if some repository operation fail
     */
    public void deleteTest(String testId) throws RepositoryException
    {
        // construct query
        String queryString = MessageFormat.format("select * from mu:test where jcr:uuid = ''{0}''", testId);

        // execute query
        QueryResult queryResult = session.getWorkspace().getQueryManager().createQuery(queryString, "sql").execute();

        NodeIterator iterator = queryResult.getNodes();
        if (iterator.hasNext()) {
            // get test node
            Node testNode = iterator.nextNode();

            // remove all references to this test
            PropertyIterator referencesIterator = testNode.getReferences();
            while (referencesIterator.hasNext()) {
                // todo remove property correct
                referencesIterator.nextProperty().remove();
            }

            // remove test
            testNode.remove();
        }

        // apply changes
        session.save();

        log.info("Test " + testId + " was deleted");
    }
}
