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
 * Represent the topic wich collect tests by theme.
 *
 * @author Pavel Konnikov
 * @version $Revision$ $Date$
 */
public class Topic
{
    /**
     * The theme of the topic.
     */
    private String theme;

    /**
     * The test of the topic.
     */
    private List<Test> tests;

    /**
     * Constructor. Assign theme to the topic.
     *
     * @param theme the theme of the topic
     */
    public Topic(String theme)
    {
        this.theme = theme;
        tests = new ArrayList<Test>();
    }

    /**
     * Returns the theme of the topic.
     *
     * @return the theme of the topic
     */
    public String getTheme()
    {
        return theme;
    }

    /**
     * Set the theme of the topic.
     *
     * @param theme the theme of the topic
     */
    public void setTheme(String theme)
    {
        this.theme = theme;
    }

    /**
     * Add new test in the topic.
     *
     * @param test new test
     */
    public void addTest(Test test)
    {
        tests.add(test);
    }

    /**
     * Returns tests of the topic.
     *
     * @return all tests of the topic
     */
    public List<Test> getTests()
    {
        return tests;
    }

    /**
     * Remove test from the topic.
     *
     * @param test removed test
     */
    public void removeTest(Test test)
    {
        this.tests.remove(test);
    }
}
