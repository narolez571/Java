// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.suggs.roo.pizzashop.domain;

import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;
import org.suggs.roo.pizzashop.domain.Pizza;

privileged aspect PizzaDataOnDemand_Roo_DataOnDemand {
    
    declare @type: PizzaDataOnDemand: @Component;
    
    private Random PizzaDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<Pizza> PizzaDataOnDemand.data;
    
    public Pizza PizzaDataOnDemand.getNewTransientPizza(int index) {
        org.suggs.roo.pizzashop.domain.Pizza obj = new org.suggs.roo.pizzashop.domain.Pizza();
        obj.setBase(null);
        obj.setName("name_" + index);
        obj.setPrice(new Integer(index).floatValue());
        return obj;
    }
    
    public Pizza PizzaDataOnDemand.getSpecificPizza(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Pizza obj = data.get(index);
        return Pizza.findPizza(obj.getId());
    }
    
    public Pizza PizzaDataOnDemand.getRandomPizza() {
        init();
        Pizza obj = data.get(rnd.nextInt(data.size()));
        return Pizza.findPizza(obj.getId());
    }
    
    public boolean PizzaDataOnDemand.modifyPizza(Pizza obj) {
        return false;
    }
    
    public void PizzaDataOnDemand.init() {
        data = org.suggs.roo.pizzashop.domain.Pizza.findPizzaEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Pizza' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<org.suggs.roo.pizzashop.domain.Pizza>();
        for (int i = 0; i < 10; i++) {
            org.suggs.roo.pizzashop.domain.Pizza obj = getNewTransientPizza(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}
