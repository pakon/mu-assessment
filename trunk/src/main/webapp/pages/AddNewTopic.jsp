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

    <h2>Topic creation page</h2>
    <c:url var="createTopicUrl" value="/create-topic"/>
    <form action="${createTopicUrl}">
        <div class="form-row">
            <label for="topicTheme" class="required">Topic theme:</label>
            <input type="text" id="topicTheme" name="name" size="30" value="" maxlength="255"/>
        </div>
        <input type="submit" value="Create topic"/>
        <input type="hidden" value="1" name="_popup"/>
        <script type="text/javascript">document.getElementById("topicTheme").focus();</script>
    </form>
</div>
<!-- container end -->

</body>
</html>
