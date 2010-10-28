package org.suggs.roo.pizzashop.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import org.suggs.roo.pizzashop.domain.Base;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooEntity
@Entity
public class Pizza {

    @NotNull
    @Size(min = 2)
    private String name;

    private Float price;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<org.suggs.roo.pizzashop.domain.Topping> toppings = new java.util.HashSet<org.suggs.roo.pizzashop.domain.Topping>();

    @ManyToOne
    private Base base;
}