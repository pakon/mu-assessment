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

/**
 * Represent the answer to a question.
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class Answer
{
    /**
     * The text of the answer.
     */
    private String text;

    /**
     * True if answer is correct.
     */
    private Boolean correct;

    /**
     * Create answer with given text and "correct" value.
     *
     * @param text    the text of the answer
     * @param correct is the answer correct or not
     */
    public Answer(String text, Boolean correct)
    {
        this.text = text;
        this.correct = correct;
    }

    /**
     * Returns the text of the answer.
     *
     * @return the text of the answer
     */
    public String getText()
    {
        return text;
    }

    /**
     * Set the text of the answer.
     *
     * @param text the text of the answer
     */
    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * Returns answer "correct" value.
     *
     * @return true if answer is correct
     */
    public Boolean getCorrect()
    {
        return correct;
    }

    /**
     * Set answer "correct" value.
     *
     * @param correct is the answer correct or not
     */
    public void setCorrect(Boolean correct)
    {
        this.correct = correct;
    }
}
