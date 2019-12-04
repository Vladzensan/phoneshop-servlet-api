package com.es.phoneshop.filterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArrayListVisitorDao implements VisitorDao{
    private List<Visitor> visitors;


    public static ArrayListVisitorDao getInstance(){
        return VisitorDaoHolder.instance;
    }

    private ArrayListVisitorDao(){
        visitors = new ArrayList<>();
    }

    @Override
    public void addVisitor(Visitor visitor) {
        if(!containsVisitorWithAddress(visitor.getRemoteAddress())){
            visitors.add(visitor);
        }
    }

    @Override
    public boolean containsVisitorWithAddress(String remoteAddress) {
        Optional<Visitor> optionalVisitor = visitors.stream()
                .filter(visitor1 -> visitor1.getRemoteAddress().equals(remoteAddress))
                .findAny();
        return optionalVisitor.isPresent();
    }

    private static class VisitorDaoHolder{
        final static ArrayListVisitorDao instance = new ArrayListVisitorDao();
    }
}
