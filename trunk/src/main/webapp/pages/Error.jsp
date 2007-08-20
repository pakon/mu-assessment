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

    <h1>mu-assessment - Jackrabbit Demo Application</h1>

    <!-- content begin -->
    <div id="content">

        <h1>System Error!</h1>

        <p>${errorDescription}</p>

        <c:url var="welcomeUrl" value="/welcome"/>

        <p>
            <a href="${welcomeUrl}">Go to welcome page...</a>
        </p>

    </div>
    <!-- content end -->

    <%@ include file="/include/rightnavbar.jspf" %>

</div>
<!-- container end -->

</body>
</html>
