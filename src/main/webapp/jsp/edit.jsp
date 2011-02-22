<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<portlet:defineObjects/>

<form method="post" action="<portlet:actionURL/>">
    <input type="hidden" name="update" value="true"/>
	<table class="languageList-table" width="100%">
        <tr>
<% 
    int counter = 1;
    String checked = "";
    String supportedFlags = (java.lang.String)renderRequest.getPreferences().getValue("supported.languages", "EN"); 
    String displayFlags   = (java.lang.String)renderRequest.getPreferences().getValue("display.languages", "EN"); 
    for(String f : supportedFlags.split(",")) {
        if(displayFlags.indexOf(f)>=0) {
            checked = " checked=\"checked\"";
        } else {
            checked = "";
        }
%>
        <td class="languageList-td">
            <input type="checkbox" name="flags" value="<%=f%>" <%=checked%>><img src="/languageSwitchPortlet/images/flag_<%=f%>.png" />&nbsp;[<%=f%>]
        </td>
<%
        if(counter % 3 == 0) {
%>
            </tr><tr>
<%        
        }
        counter++;
    }
%>
        </tr>
        <tr>
            <td colspan="3" align="center">
                <input type="submit" value="Save"/>
            </td>
        </tr>
    </table>
</form>