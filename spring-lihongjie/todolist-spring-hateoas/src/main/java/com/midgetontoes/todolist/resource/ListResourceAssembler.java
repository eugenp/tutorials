package com.midgetontoes.todolist.resource;

import com.midgetontoes.todolist.controller.ListRestController;
import com.midgetontoes.todolist.model.List;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class ListResourceAssembler extends ResourceAssemblerSupport<List, ListResource> {

    public ListResourceAssembler() {
        super(ListRestController.class, ListResource.class);
    }

    @Override
    public ListResource toResource(List list) {
        ListResource resource = createResourceWithId(list.getId(), list);
        return resource;
    }

    @Override
    protected ListResource instantiateResource(List entity) {
        return new ListResource(entity);
    }
}
