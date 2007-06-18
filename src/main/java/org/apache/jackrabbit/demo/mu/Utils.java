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
package org.apache.jackrabbit.demo.mu;

import org.apache.log4j.Logger;
import org.apache.jackrabbit.demo.DemoUtils;

import javax.jcr.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Contains some usefull mu-assessment demo applicaion specific methods.
 */
public class Utils
{
    private static final Logger log = Logger.getLogger(Utils.class);

    /**
     * Create new test contains all available questions.
     */
    public static void createSmapleTest(Session session, String title) throws RepositoryException
    {
        Node rootNode = session.getRootNode();
        Node testsNode = rootNode.getNode("mu-root/tests");

        // get all question nodes
        NodeIterator questionsIterator = session.getWorkspace().getQueryManager().createQuery("select * from mu:question", "sql").execute().getNodes();

        // create new test node and add to one references to all available questions.
        Node testNode = testsNode.addNode("test", "mu:test");
        List<Value> questionFefs = new ArrayList<Value>();
        while (questionsIterator.hasNext()) {
            questionFefs.add(questionsIterator.nextNode().getProperty("jcr:uuid").getValue());
        }
        testNode.setProperty("mu:questionrefs", questionFefs.toArray(new Value [0]));

        // set title for test
        testNode.setProperty("mu:title", title);

        // print dump of new test node
        DemoUtils.dump(testNode);
    }
}
