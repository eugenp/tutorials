package com.midgetontoes.todolist.aspect;

import com.midgetontoes.todolist.AccessDeniedException;
import com.midgetontoes.todolist.EntityNotFoundException;
import com.midgetontoes.todolist.jpa.ItemRepository;
import com.midgetontoes.todolist.jpa.ListRepository;
import com.midgetontoes.todolist.model.Item;
import com.midgetontoes.todolist.model.List;
import com.midgetontoes.todolist.resource.ListResource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class EntityPermissionCheck {

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Around("execution(public com.midgetontoes.todolist.model.List com.midgetontoes.todolist.service.ListService.*(..))")
    public Object checkQueryListAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object entity = joinPoint.proceed();
        List list = (List) entity;
        checkListAccess(list);
        return list;
    }

    @Around("execution(public com.midgetontoes.todolist.model.Item com.midgetontoes.todolist.service.ItemService.*(..))")
    public Object checkQueryItemAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object entity = joinPoint.proceed();
        Item item = (Item) entity;
        checkItemAccess(item);
        return item;
    }

    @Before("execution(public * com.midgetontoes.todolist.service.ListService.*(Long))")
    public void checkUpdateListAccess(JoinPoint joinPoint) {
        Long id = (Long)joinPoint.getArgs()[0];
        List list = listRepository.findOne(id);
        checkListAccess(list);
    }

    @Before("execution(public * com.midgetontoes.todolist.service.ItemService.*(Long))")
    public void checkUpdateItemAccess(JoinPoint joinPoint) {
        Long id = (Long)joinPoint.getArgs()[0];
        Item item = itemRepository.findOne(id);
        checkItemAccess(item);
    }

    private void checkListAccess(List list) {
        if (list == null) {
            throw new EntityNotFoundException();
        }
        else if (list.getUser() == null
                || list.getUser().getUsername() == null
                || !list.getUser().getUsername().equals(getCurrentUser())) {
            throw new AccessDeniedException();
        }
    }

    private void checkItemAccess(Item item) {
        if (item != null && item.getList() != null) {
            checkListAccess(item.getList());
        }
        else {
            throw new EntityNotFoundException();
        }
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            User user = (User) authentication.getPrincipal();
            return user.getUsername();
        }
        return null;
    }
}
