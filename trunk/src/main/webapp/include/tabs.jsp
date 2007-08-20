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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    pageContext.getSession().setAttribute("currentPage", request.getServletPath());
%>

<c:url var="previewUrl" value="${currentPage}" />
<c:url var="jcrFeaturesUrl" value="#" />
<c:url var="architectureUrl" value="#" />
<c:url var="sourcesUrl" value="/source-files" />
<c:url var="logoUrl" value="/images/logo-100.jpg" />
<c:url var="rootUrl" value="/" />

<h1><a href="${rootUrl}"><img src="${logoUrl}" align="middle"/></a> mu-assessment - Jackrabbit Demo Application</h1>

<%--<div class="debug">${currentPage}</div>--%>

<!-- tabs begin -->
<div id="tabs">
<%--
    <a href="${previewUrl}"><span>page</span></a>
    <a href="${jcrFeaturesUrl}"><span>features</span></a>
    <a href="${architectureUrl}"><span>architecture</span></a>
    <a href="${sourcesUrl}"><span>source files</span></a>
--%>
    <a href="#"><span>page</span></a>
    <a href="#"><span>features</span></a>
    <a href="#"><span>architecture</span></a>
    <a href="#"><span>source files</span></a>
</div>
<!-- tabs end -->
