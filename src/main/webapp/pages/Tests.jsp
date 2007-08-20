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

<!-- container begin -->
<div id="container">

    <%@ include file="/include/tabs.jsp" %>

    <!-- content begin -->
    <div id="content">

        <h2>Tests by topic</h2>

        <c:url var="beginTestUrl" value="/begin-test"/>
        <c:url var="deleteTestUrl" value="/delete-test"/>
        <c:url var="editTestUrl" value="/edit-test"/>
        <c:url var="imagesUrl" value="/images"/>
        <ul>
            <c:forEach var="topic" items="${topics}">
                <li class="topic-with-tests">
                    <h3>${topic.theme}</h3>
                    <ul>
                        <c:forEach var="test" items="${topic.tests}">
                            <li>
                                <a href="${beginTestUrl}?id=${test.id}">${test.title}</a>
                                <%--<a href="${editTestUrl}?id=${test.id}"> <img src="${imagesUrl}/cart_edit.png"/></a>--%>
                                <%--<a href="${deleteTestUrl}?id=${test.id}"> <img src="${imagesUrl}/cart_delete.png"/></a>--%>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
        </ul>

    </div>
    <!-- content end -->

    <%@ include file="/include/rightnavbar.jspf" %>

</div>
<!-- container end -->

</body>
</html>
