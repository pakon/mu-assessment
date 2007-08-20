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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sess" uri="http://jakarta.apache.org/taglibs/session-1.0" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/html" xml:lang="en" lang="en">

<%@ include file="/include/header.jspf" %>

<body>

<!-- container begin -->
<div id="container">

    <%@ include file="/include/tabs.jsp" %>

    <!-- content begin -->
    <div id="content">

        <h2>Question</h2>
        <c:url var="updateQuestionUrl" value="/update-question"/>
        <form action="${updateQuestionUrl}?id=${question.id}" method="post">
            <textarea name="questionText" rows="20" cols="50">${question.text}</textarea><br/>
            <label for="weight">Ball</label>
            <input type="text" id="weight" name="weight" value="${question.weight}"/><br/>
            <ul>
                <c:forEach var="answer" items="${question.answers}" varStatus="count">
                    <li>
                        <c:choose>
                            <c:when test="${answer.correct}">
                                <input type="checkbox" checked="checked" name="answer${count.index}"/>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" name="answer${count.index}"/>
                            </c:otherwise>
                        </c:choose>
                        <label for="answer${count.index}">${answer.text}</label>
                    </li>
                </c:forEach>
            </ul>
            <input type="submit" value="Change"/>
        </form>
        <c:url var="questionsUrl" value="/package"/>
        <a href="${questionsUrl}?id=${packageId}">Back to package</a>
    </div>
    <!-- content end -->

    <%@ include file="/include/rightnavbar.jspf" %>

</div>
<!-- container end -->

</body>
</html>
