<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<portlet:defineObjects/>
<ul class="languageList">
<% 
    String flags = (java.lang.String)renderRequest.getPreferences().getValue("display.languages", "EN"); 
    for(String f : flags.split(",")) {
%>
        <li class="languageList-item">
            <a href="<portlet:actionURL><portlet:param name="language" value="<%=f%>"/></portlet:actionURL>">
                <img src="/languageSwitchPortlet/images/flag_<%=f%>.png" />
            </a>
        </li>
<%
    }
%>
</ul>
