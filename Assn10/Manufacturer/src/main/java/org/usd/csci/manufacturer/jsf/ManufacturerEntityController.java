package org.usd.csci.manufacturer.jsf;

import org.usd.csci.manufacturer.ManufacturerEntity;
import org.usd.csci.manufacturer.ManufacturerEntityFacade;
import org.usd.csci.manufacturer.jsf.util.JsfUtil;
import org.usd.csci.manufacturer.jsf.util.JsfUtil.PersistAction;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("manufacturerEntityController")
@SessionScoped
public class ManufacturerEntityController implements Serializable {

    @EJB
    private org.usd.csci.manufacturer.ManufacturerEntityFacade ejbFacade;
    private List<ManufacturerEntity> items = null;
    private ManufacturerEntity selected;

    public ManufacturerEntityController() {
    }

    public ManufacturerEntity getSelected() {
        return selected;
    }

    public void setSelected(ManufacturerEntity selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ManufacturerEntityFacade getFacade() {
        return ejbFacade;
    }

    public ManufacturerEntity prepareCreate() {
        selected = new ManufacturerEntity();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Manufacturer").getString("ManufacturerEntityCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Manufacturer").getString("ManufacturerEntityUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Manufacturer").getString("ManufacturerEntityDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ManufacturerEntity> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Manufacturer").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Manufacturer").getString("PersistenceErrorOccured"));
            }
        }
    }

    public ManufacturerEntity getManufacturerEntity(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<ManufacturerEntity> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ManufacturerEntity> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ManufacturerEntity.class)
    public static class ManufacturerEntityControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ManufacturerEntityController controller = (ManufacturerEntityController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "manufacturerEntityController");
            return controller.getManufacturerEntity(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ManufacturerEntity) {
                ManufacturerEntity o = (ManufacturerEntity) object;
                return getStringKey(o.getManufacturerId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ManufacturerEntity.class.getName()});
                return null;
            }
        }

    }

}
