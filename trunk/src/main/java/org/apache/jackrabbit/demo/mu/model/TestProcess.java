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

import org.apache.jackrabbit.demo.mu.exceptions.TestEndException;

import java.util.LinkedList;
import java.util.List;

/**
 * Represent the process of test passing and processing.
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class TestProcess
{
    /**
     * The test to be processed.
     */
    private Test test;

    /**
     * Number of current question in the test.
     */
    private Integer questionNumber;

    /**
     * List of "correct" values given by user on the correspond cuestions.
     */
    private List<Boolean> givenAnswers;

    /**
     * Constructor.
     *
     * @param test processed test
     */
    public TestProcess(Test test)
    {
        this.test = test;
        this.questionNumber = 0;
        this.givenAnswers = new LinkedList<Boolean>();
    }

    /**
     * Returns next question in the test and increment currnt question number.
     *
     * @return next question in the test
     * @throws TestEndException
     */
    public Question nextQuestion() throws TestEndException
    {
        try {
            return test.getQuestions().get(questionNumber++);
        } catch (IndexOutOfBoundsException e) {
            throw new TestEndException();
        }
    }

    /**
     * Process answers given by user on the current question.
     *
     * @param truePositions list of numbers when user choose right answer
     * @throws TestEndException when no more questions
     */
    public void processGivenAnswers(List<Integer> truePositions) throws TestEndException
    {
        if (questionNumber == 0) {
            return;
        }
        try {
            boolean result = true;
            Question question = (test.getQuestions().get(questionNumber-1));
            List<Answer> answers = question.getAnswers();
            List<Boolean> actualAnswers = new LinkedList<Boolean>();
            for (int i = 0; i < answers.size(); i++) {
                if (truePositions.contains(i)) {
                    actualAnswers.add(true);
                } else {
                    actualAnswers.add(false);
                }
            }

            for (int i = 0; i < answers.size(); ++i) {
                if (answers.get(i).getCorrect() != actualAnswers.get(i)) {
                    result = false;
                }
            }

            givenAnswers.add(result);

        } catch (IndexOutOfBoundsException e) {
            throw new TestEndException();
        }

    }

    /**
     * Returns true if current question has more than one correct answer.
     *
     * @return true if current question has more than one correct answer
     */
    public boolean isMultiple()
    {
        int counter = 0;
        List<Answer> answers = test.getQuestions().get(questionNumber).getAnswers();
        for (Answer answer : answers) {
            if (answer.getCorrect()) {
                ++counter;
                if (counter > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the number of the current question.
     *
     * @return the number of the current question
     */
    public Integer getQuestionNumber()
    {
        return questionNumber;
    }

    /**
     * Returns list of "correct" values of answers given by user.
     *
     * @return list of "correct" values
     */
    public List<Boolean> getGivenAnswers()
    {
        return givenAnswers;
    }
}
