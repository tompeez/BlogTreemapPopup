package de.hahn.blog.treemappopup.view.beans;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;


/**
 * Treemap handler bean
 * @author Timo Hahn
 */
public class TreemapBean {
    private static ADFLogger logger = ADFLogger.createADFLogger(TreemapBean.class);

    public TreemapBean() {
    }

    /**
     * listen to popup fetch.
     * @param popupFetchEvent event triggerd the fetch
     */
    public void fetchListener(PopupFetchEvent popupFetchEvent) {
        //#{bindings.nodeText1.inputValue}
        //#{source.currentRowData.lastName}
        String lastName = (String) getValueFromExpression("#{source.currentRowData.lastName}");
        Integer id = (Integer) getValueFromExpression("#{source.currentRowData.EmployeeId}");
        //build info string
        String res = lastName + " id: " + id;
        logger.info("Information: " + res);
        // get the binding container
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();

        // get an ADF attributevalue from the ADF page definitions
        AttributeBinding attr = (AttributeBinding) bindings.getControlBinding("nodeInfo1");
        //set the value to it
        attr.setInputValue(res);
    }

    // get a value from an expression
    private Object getValueFromExpression(String name) {
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        Application app = facesCtx.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesCtx.getELContext();
        Object obj = elFactory.createValueExpression(elContext, name, Object.class).getValue(elContext);
        return obj;
    }
}
