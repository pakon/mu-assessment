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
 * Represent the package of questions.
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class QuestionPackage
{
    /**
     * UUID of coresponded node in repository.
     */
    private String id;

    /**
     * Author of the package.
     */
    private String author;

    /**
     * Date when package was created.
     */
    private String date;

    /**
     * Questions of the questions package.
     */
    private List<Question> questions;

    /**
     * Default constructor.
     */
    public QuestionPackage()
    {
        questions = new ArrayList<Question>();
    }

    /**
     * Returns the question package id.
     *
     * @return the question package id
     */
    public String  getId()
    {
        return id;
    }

    /**
     * Set the question package id.
     * 
     * @param id the question package id
     */
    public void setId(String  id)
    {
        this.id = id;
    }

    /**
     * Returns the author of the package.
     *
     * @return the author of the package
     */
    public String getAuthor()
    {
        return author;
    }

    /**
     * Set the author of the package.
     *
     * @param author the author of the package
     */
    public void setAuthor(String author)
    {
        this.author = author;
    }

    /**
     * Returns date when package was created as string.
     *
     * @return date when package was created as string
     */
    public String getDate()
    {
        return date;
    }

    /**
     * Set date when package was created.
     *
     * @param date date when package was created
     */
    public void setDate(String date)
    {
        this.date = date;
    }

    /**
     * Add a question in questions package.
     *
     * @param question new question
     */
    public void addQuestion(Question question)
    {
        questions.add(question);
    }

    /**
     * Returns all questions of the questions package.
     *
     * @return all questions of the questions package
     */
    public List<Question> getQuestions()
    {
        return questions;
    }

    /**
     * Set questions of the questions package.
     *
     * @param questions questions of the questions package
     */
    public void setQuestions(List<Question> questions)
    {
        this.questions = questions;
    }

    /**
     * Remove question from the questions package.
     *
     * @param question removed question
     */
    public void removeQuestion(Question question)
    {
        questions.remove(question);
    }
}
