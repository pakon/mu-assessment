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
 * Represent the test.
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class Test
{
    /**
     * UUID of coresponded node in repository.
     */
    private String id;

    /**
     * The title of the test.
     */
    private String title;

    /**
     * Questions of the test.
     */
    private List<Question> questions;

    /**
     * Default constructor.
     */
    public Test()
    {
        questions = new ArrayList<Question>();
    }

    /**
     * Returns the test id.
     *
     * @return the test id
     */
    public String getId()
    {
        return id;
    }

    /**
     * Returns the test id.
     *
     * @param id the test id.
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Returns the title of the test.
     *
     * @return the title of the test
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Set the title of the test.
     *
     * @param title the title of the test
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Add a question in the test.
     *
     * @param question new question
     */
    public void addQuestion(Question question)
    {
        questions.add(question);
    }

    /**
     * Returns all questions of the test.
     *
     * @return all questions of the test
     */
    public List<Question> getQuestions()
    {
        return questions;
    }

    /**
     * Remove question from the test.
     *
     * @param question removed question
     */
    public void removeQuestion(Question question)
    {
        questions.remove(question);
    }
}
