package com.stackify.deepjsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@RequestScoped
public class PhaseListenerBean {

    public void beforeListener(PhaseEvent event) {
        if (!event.getPhaseId().equals(PhaseId.RENDER_RESPONSE)) {
            return;
        }
        UIViewRoot root = event.getFacesContext().getViewRoot();

        boolean showNewFeature = showNewFeatureForIp(event);

        processComponentTree(root, event, showNewFeature);
    }

    private boolean showNewFeatureForIp(PhaseEvent event) {
        HttpServletRequest request = (HttpServletRequest) event.getFacesContext()
                .getExternalContext().getRequest();
        String ip = request.getRemoteAddr();
        return !ip.startsWith("127.0");
    }

    private void processComponentTree(UIComponent component, PhaseEvent event, boolean show) {
        component.visitTree(VisitContext.createVisitContext(event.getFacesContext()),
                (context, target) -> {
                    if (target.getId() != null
                            && target.getId().startsWith("new-feature-")
                            && !show) {
                        target.setRendered(false);
                    }
                    return VisitResult.ACCEPT;
                });
    }

}
