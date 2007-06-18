package org.apache.jackrabbit.demo.mu.model;

import org.apache.commons.collections.IteratorUtils;
import org.apache.jackrabbit.demo.mu.model.Answer;
import org.apache.jackrabbit.demo.mu.model.QuestionSummary;
import org.apache.jackrabbit.demo.mu.exceptions.TestEndException;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TestProcess implements Serializable
{
    private Integer questionNumber;

    private Node testNode;

    private Value[] questions;


    public TestProcess(Node test, Value[] questions) {
        this.testNode = test;
        this.questions = questions;
        this.questionNumber = 0;
    }

    public QuestionSummary processPosition(Long pageNumber, Session session) throws RepositoryException, TestEndException {
        Node question;
        try {
            question = session.getNodeByUUID(questions[questionNumber++].getString());
        } catch (Exception e) {
            throw new TestEndException();
        }
        String questionText = question.getProperty("mu:text").getString();
        List<Answer> answers = new ArrayList<Answer>();
        Node answer;
        for (Object obj : IteratorUtils.toList(question.getNodes())) {
            answer = (Node) obj;
            answers.add(new Answer(
                    answer.getProperty("mu:text").getString(),
                    answer.getProperty("mu:correct").getBoolean()
            ));
        }
        return new QuestionSummary(questionText, answers, questionNumber);
    }

    public String getTestName() {
        try {
            return testNode.getProperty("mu:title").getString();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Integer getQuestionNumber() {
        return questionNumber;
    }

}
