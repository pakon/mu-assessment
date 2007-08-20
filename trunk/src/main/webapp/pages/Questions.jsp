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
<%@ page import="org.apache.jackrabbit.demo.mu.model.Question" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sess" uri="http://jakarta.apache.org/taglibs/session-1.0" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/html" xml:lang="en" lang="en">

<%@ include file="/include/header.jspf" %>

<body>

<!-- container begin -->
<div id="container">

    <%@ include file="/include/tabs.jsp"%>

    <!-- content begin -->
    <div id="content">

        <h2>Questions in package ${packageId}</h2>

        <c:url var="questionEditUrl" value="/edit-question"/>

        <ol>
            <c:forEach var="question" items="${questions}">
                <%
                    Question question = (Question) pageContext.getAttribute("question");
                    pageContext.setAttribute("questionAbrText", StringUtils.abbreviate(question.getText(), 50));
                %>
                <li>
                    <a href="${questionEditUrl}?id=${question.id}">${questionAbrText}</a>
                </li>
            </c:forEach>
        </ol>

    </div>
    <!-- content end -->

    <%@ include file="/include/rightnavbar.jspf" %>

</div>
<!-- container end -->

</body>
</html>
