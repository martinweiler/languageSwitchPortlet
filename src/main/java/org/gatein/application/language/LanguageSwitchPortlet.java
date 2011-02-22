package org.gatein.application.language;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.MimeResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.UnavailableException;
import javax.portlet.ValidatorException;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.portal.application.PortalRequestContext;
import org.exoplatform.services.resources.LocaleConfig;
import org.exoplatform.services.resources.LocaleConfigService;

import org.w3c.dom.Element;

public class LanguageSwitchPortlet extends GenericPortlet
{
   private LocaleConfigService localeConfigService;


   public void init(PortletConfig config) throws javax.portlet.PortletException {
      super.init(config);
      // retrieve LocalConfigService
      localeConfigService = (LocaleConfigService) PortalContainer.getInstance().getComponentInstanceOfType(LocaleConfigService.class);

   }  
   
   public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException
   {
      response.setContentType("text/html");
      PortletRequestDispatcher prd = getPortletContext().getRequestDispatcher("/jsp/index.jsp");
      prd.include(request, response);
       
   }

   public void doEdit(RenderRequest request, RenderResponse response) throws PortletException, IOException
   {
      response.setContentType("text/html");
      PortletRequestDispatcher prd = getPortletContext().getRequestDispatcher("/jsp/edit.jsp");
      prd.include(request, response);
       
   }   

   public void processAction(ActionRequest aRequest, ActionResponse aResponse) throws PortletException, IOException,  UnavailableException
   {
         if ("true".equals(aRequest.getParameter("update"))) {
             updatePreferences(aRequest);
         } else {
             String language = (String) aRequest.getParameter("language");
             if (language == null || language.trim().length() < 1 || localeConfigService == null) 
                 return;
           
             LocaleConfig localeConfig = localeConfigService.getLocaleConfig(language.toLowerCase());
             if (localeConfig == null)
                localeConfig = localeConfigService.getDefaultLocaleConfig();
             PortalRequestContext prqCtx = PortalRequestContext.getCurrentInstance();
             prqCtx.setLocale(localeConfig.getLocale());
         }
   }
   
   private void updatePreferences(ActionRequest aRequest) throws ReadOnlyException, IOException, ValidatorException {
       String newPrefFlags[] = aRequest.getParameterValues("flags");
       StringBuilder sb = new StringBuilder();
       if(newPrefFlags == null || newPrefFlags.length == 0) {
            sb.append("EN");
       } else {
           for (String f : newPrefFlags) {
                sb.append(f + ",");
           }
           sb.delete(sb.length()-1, sb.length());      
       }
       aRequest.getPreferences().setValue("display.languages", sb.toString());       
       aRequest.getPreferences().store();
   }
   
	public void doHeaders(RenderRequest request, RenderResponse response)
	{
		Element css = response.createElement("link");
		css.setAttribute("id", "languageSwitchCss");
		css.setAttribute("type", "text/css");
		css.setAttribute("rel", "stylesheet");
		css.setAttribute("href", request.getContextPath() + "/css/lang.css");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, css);
	}   

}
