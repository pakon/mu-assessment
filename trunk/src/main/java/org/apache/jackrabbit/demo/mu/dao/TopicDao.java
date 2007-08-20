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

import org.apache.jackrabbit.demo.mu.model.Topic;
import org.apache.log4j.Logger;

import javax.jcr.*;
import javax.jcr.query.QueryResult;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Realize repository data manipulation mechanism for <code>Topic</code>.
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class TopicDao extends GenericDao
{
    private static final Logger log = Logger.getLogger(UserDao.class);

    /**
     * Default constructor. Set repository session.
     *
     * @param session repository session
     */
    public TopicDao(Session session)
    {
        super(session);
    }

    /**
     * Returns the <code>Topic</code> object by given <code>Node</code> object.
     *
     * @param node the topic theme
     * @return corresponded <code>Topic</code> object
     * @throws RepositoryException if some repository operation fail
     */
    public Topic getTopic(Node node) throws RepositoryException
    {
        // get theme property
        String theme = node.getProperty("mu:theme").getString();

        // create new topic
        Topic topic = new Topic(theme);

        // add tests for the topic
        Property testReferencesProperty = node.getProperty("mu:testrefs");
        Value[] testsReferences = testReferencesProperty.getValues();
        TestDao testDao = new TestDao(session);
        for (Value testReference : testsReferences) {
            topic.addTest(testDao.getTest(testReference.getString()));
        }

        return topic;
    }

    /**
     * Returns the <code>Topic</code> object by given theme.
     *
     * @param theme the topic theme
     * @return corresponded <code>Topic</code> object
     * @throws RepositoryException if some repository operation fail
     */
    public Topic getTopic(String theme) throws RepositoryException
    {
        // prepare query
        String queryString = MessageFormat.format("select * from mu:topic where mu:theme = ''{0}''", theme);

        // execute query
        QueryResult queryResult = session.getWorkspace().getQueryManager().createQuery(queryString, "sql").execute();

        NodeIterator iterator = queryResult.getNodes();
        if (iterator.hasNext()) {
            Node topicNode = iterator.nextNode();
            return getTopic(topicNode);
        } else {
            log.warn("Topic node with theme = '" + theme + "' not exists");
            throw new RepositoryException("Topic node with theme = '" + theme + "' not exists");
        }
    }

    /**
     * Returns the topic node that has given theme as "mu:thteme"
     *
     * @param theme the theme of the topic
     * @return corresponded topic node
     * @throws RepositoryException if some repository operation fail
     */
    public Node getTopicNode(String theme) throws RepositoryException
    {
        // prepare query
        String queryString = MessageFormat.format("select * from mu:topic where mu:theme = ''{0}''", theme);

        // execute query
        QueryResult queryResult = session.getWorkspace().getQueryManager().createQuery(queryString, "sql").execute();

        NodeIterator iterator = queryResult.getNodes();
        if (iterator.hasNext()) {
            return iterator.nextNode();
        } else {
            log.warn("Topic node with theme = '" + theme + "' not exists");
            throw new RepositoryException("Topic node with theme = '" + theme + "' not exists");
        }
    }

    /**
     * Returns list of all topics.
     *
     * @return list of topics
     * @throws RepositoryException if some repository operation fail
     */
    public List<Topic> getTopics() throws RepositoryException
    {
        // create an empty topic list
        List<Topic> topics = new ArrayList<Topic>();

        // prepare query
        String queryString = "select * from mu:topic";

        // execute query
        QueryResult queryResult = session.getWorkspace().getQueryManager().createQuery(queryString, "sql").execute();

        // add topics to the list
        NodeIterator iterator = queryResult.getNodes();
        while (iterator.hasNext()) {
            Topic topic = getTopic(iterator.nextNode());
            topics.add(topic);
        }

        return topics;
    }

    /**
     * Create new topic in repository.
     *
     * @param theme the theme of the topic
     * @throws RepositoryException if some repository operation fail
     */
    public void createTopic(String theme) throws RepositoryException
    {
        // get topics node
        Node topicsNode = session.getRootNode().getNode("mu-root/topics");

        // add new topic node
        Node newTopicNode = topicsNode.addNode("topic", "mu:topic");

        // setup topics properties
        newTopicNode.setProperty("mu:theme", theme);
        newTopicNode.setProperty("mu:testrefs", new Value[]{});

        // apply changes
        session.save();
    }
}
