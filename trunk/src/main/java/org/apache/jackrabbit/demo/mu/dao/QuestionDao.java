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

import org.apache.jackrabbit.demo.mu.model.Question;
import org.apache.jackrabbit.demo.mu.model.Answer;
import org.apache.jackrabbit.demo.mu.model.QuestionPackage;
import org.apache.jackrabbit.value.StringValue;
import org.apache.jackrabbit.value.LongValue;
import org.apache.log4j.Logger;

import javax.jcr.*;
import javax.jcr.query.QueryResult;
import java.text.MessageFormat;
import java.util.List;

/**
 * Realize repository data manipulation mechanism for <code>Question</code>.
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class QuestionDao extends GenericDao
{
    private static final Logger log = Logger.getLogger(QuestionDao.class);

    /**
     * Default constructor. Set repository session.
     *
     * @param session repository session
     */
    public QuestionDao(Session session)
    {
        super(session);
    }

    /**
     * Returns the <code>Question</code> object by given <code>Node</code> object.
     *
     * @param questionNode contains question's data
     * @return coresponded <code>Question</code> object
     * @throws RepositoryException if some repository operation fail
     */
    public Question getQuestion(Node questionNode) throws RepositoryException
    {
        Question question = new Question();

        question.setId(questionNode.getProperty("jcr:uuid").getString());
        question.setText(questionNode.getProperty("mu:text").getString());
        question.setWeight(questionNode.getProperty("mu:weight").getLong());

        AnswerDao answerDao = new AnswerDao(session);
        NodeIterator iterator = questionNode.getNodes();
        while (iterator.hasNext()) {
            Answer answer = answerDao.getAnswer(iterator.nextNode());
            question.addAnswer(answer);
        }

        return question;
    }

    /**
     * Returns the <code>Question</code> object by given uuid.
     *
     * @param questionId uuid of node
     * @return coresponded <code>Question</code> object
     * @throws RepositoryException if some repository operation fail  or node with given uuid not exists
     */
    public Question getQuestion(String questionId) throws RepositoryException
    {
        // construct query
        String queryString = MessageFormat.format("select * from mu:question where jcr:uuid = ''{0}''", questionId);

        // execute query
        QueryResult queryResult = session.getWorkspace().getQueryManager().createQuery(queryString, "sql").execute();

        NodeIterator iterator = queryResult.getNodes();
        if (iterator.hasNext()) {
            // get node represent question
            Node questionNode = iterator.nextNode();

            // create new question
            return getQuestion(questionNode);
        } else {
            log.warn("Node with uuid = " + questionId + " not exists");
            throw new RepositoryException("Node with uuid = " + questionId + " not exists");
        }
    }

    /**
     * Returns uuid of parent node in repository by given uuid of node which represent question.
     *
     * @param questionId uuid of node which represent question
     * @return uuid of parent node
     * @throws RepositoryException if some repository operation fail
     */
    public String getQuestionParentId(String questionId) throws RepositoryException
    {
        // construct query
        String queryString = MessageFormat.format("select * from mu:question where jcr:uuid = ''{0}''", questionId);

        // execute query
        QueryResult queryResult = session.getWorkspace().getQueryManager().createQuery(queryString, "sql").execute();

        NodeIterator iterator = queryResult.getNodes();
        if (iterator.hasNext()) {
            // get node represent question
            Node questionNode = iterator.nextNode();

            return questionNode.getParent().getProperty("jcr:uuid").getString();
        } else {
            log.warn("Node with uuid = " + questionId + " not exists");
            throw new RepositoryException("Can't get parent for question with uuid = " + questionId);
        }
    }

    /**
     * Returns list of all questions contained in questions package with given uuid.
     *
     * @param packageId uuid of package
     * @return list of questions
     * @throws RepositoryException if some repository operation fail
     */
    public List<Question> getQuestions(String packageId) throws RepositoryException
    {
        // construct query
        String queryString = MessageFormat.format("select * from mu:questionPackage where jcr:uuid = ''{0}''", packageId);

        // execute query
        QueryResult queryResult = session.getWorkspace().getQueryManager().createQuery(queryString, "sql").execute();

        NodeIterator iterator = queryResult.getNodes();
        if (iterator.hasNext()) {
            // get node represent package
            Node node = iterator.nextNode();

            // create new question package
            QuestionPackageDao packageDao = new QuestionPackageDao(session);
            QuestionPackage questionPackage = packageDao.getQuestionPackage(node);

            return questionPackage.getQuestions();
        } else {
            log.warn("Node with uuid = " + packageId + " not exists");
            throw new RepositoryException("Question package with given uuid not exists.");
        }
    }

    public void updateQuestion(Question question) throws RepositoryException
    {
        // construct query
        String queryString = MessageFormat.format("select * from mu:question where jcr:uuid = ''{0}''", question.getId());

        // execute query
        QueryResult queryResult = session.getWorkspace().getQueryManager().createQuery(queryString, "sql").execute();

        NodeIterator iterator = queryResult.getNodes();
        if (iterator.hasNext()) {
            // get node represent question
            Node node = iterator.nextNode();

            node.setProperty("mu:text", new StringValue(question.getText()));
            node.setProperty("mu:weight", new LongValue(question.getWeight()));

            NodeIterator answersIterator = node.getNodes();
            int answerCount = 0;
            while (answersIterator.hasNext()) {
                answersIterator.nextNode().setProperty("mu:correct", question.getAnswers().get(answerCount++).getCorrect());
            }

            // apply changes
            session.save();
        } else {
            log.warn("Node with uuid = " + question.getId() + " not exists");
            throw new RepositoryException("Question package with given uuid not exists.");
        }
    }
}
