<%
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
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.jcr.Node" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sess" uri="http://jakarta.apache.org/taglibs/session-1.0" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/html" xml:lang="en" lang="en">

<%@ include file="/include/header.jspf" %>

<body>
<c:url var="addTopicUrl" value="/pages/AddNewTopic.jsp"/>
<script type="text/javascript">
    function showWindow()
    {
        var win = window.open('${addTopicUrl}', name, 'width=640,height=480,resizable=no,scrollbars=no');
        win.focus();
        return false;
    }

    function dismissAddAnotherPopup(win, newId, newRepr)
    {
        var elem = document.getElementById("topic");
        var o = new Option(newRepr, newId);
        elem.options[elem.options.length] = o;
        o.selected = true;
        win.close();
    }
</script>
<!-- container begin -->
<div id="container">

    <%@ include file="/include/tabs.jsp" %>

    <!-- content begin -->
    <div id="content">

        <h2>Create new test</h2>

        <c:url var="createTestUrl" value="/create-test"/>
        <form action="${createTestUrl}" method="post">
            <ul>
                <c:forEach var="pack" items="${packages}">
                    <li>
                        <h4>Package created at "${pack.date}" by ${pack.author}</h4>
                        <ul>
                            <c:forEach var="question" items="${pack.questions}">
                                <li style="list-style-type:none; ">
                                    <input type="checkbox" name="${question.id}" id="${question.id}"/>&nbsp
                                    <label for="${question.id}">Question "${question.text}",
                                        ball ${question.weight}</label>
                                </li>
                            </c:forEach>
                        </ul>
                    </li>
                </c:forEach>
            </ul>

            <div class="form-row">
                <label for="testTitle" class="required">Test title:</label>
                <input type="text" id="testTitle" name="title" size="30" value="" maxlength="255"/>
            </div>

            <div class="form-row">
                <label for="topic" class="required">Topic theme:</label>
                <select id="topic" name="topicTheme" size="1">
                    <!--<option value="" selected="selected">---------</option>-->
                    <c:forEach var="topic" items="${topics}">
                        <option value="${topic.theme}">[Topic] ${topic.theme}</option>
                    </c:forEach>
                </select>
                <c:url var="addImageUrl" value="/images/icon_addlink.gif"/>
                <a href="#" class="add-another" id="add_id_region_regionid" onclick="return showWindow()">
                    <img src="${addImageUrl}" alt="Add new topic" title="Add new topic"/>
                </a>
            </div>

            <input type="submit" value="Create"/>

        </form>

    </div>

    <!-- content end -->

    <%@ include file="/include/rightnavbar.jspf" %>

</div>
<!-- container end -->

</body>
</html>
