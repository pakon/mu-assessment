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

import java.util.List;
import java.util.ArrayList;

/**
 * Represent the question in a test.
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class Question
{
    /**
     * UUID of coresponded node in repository.
     */
    private String id;

    /**
     * The text of the question.
     */
    private String text;

    /**
     * Ball assigned to correct answer on the question.
     */
    private long weight;

    /**
     * Answers of the question.
     */
    private List<Answer> answers;

    /**
     * Default constructor.
     */
    public Question()
    {
        answers = new ArrayList<Answer>();
    }

    /**
     * Returns id of the question.
     *
     * @return id of the question
     */
    public String getId()
    {
        return id;
    }

    /**
     * Set id of the question.
     *
     * @param id the id of the question
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Returns the text of the question.
     *
     * @return the text of the question
     */
    public String getText()
    {
        return text;
    }

    /**
     * Set the text of the question.
     *
     * @param text the text of the question
     */
    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * Returns ball of the question.
     *
     * @return questions ball
     */
    public long getWeight()
    {
        return weight;
    }

    /**
     * Set ball of the question.
     *
     * @param weight questions ball
     */
    public void setWeight(long weight)
    {
        this.weight = weight;
    }

    /**
     * Add new answer to the answers list.
     *
     * @param answer new answer
     */
    public void addAnswer(Answer answer)
    {
        answers.add(answer);
    }

    /**
     * Returns possible answers on the question.
     *
     * @return answers list
     */
    public List<Answer> getAnswers()
    {
        return answers;
    }

    /**
     * Set possible answers on the question.
     *
     * @param answers list of new answers.
     */
    public void setAnswers(List<Answer> answers)
    {
        this.answers = answers;
    }
}
