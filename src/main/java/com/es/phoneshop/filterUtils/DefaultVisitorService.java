package com.es.phoneshop.filterUtils;

import javax.servlet.http.HttpServletRequest;

public class DefaultVisitorService implements VisitorService{

    private DefaultVisitorService(){}

    public static DefaultVisitorService getInstance(){
        return VisitorServiceHolder.instance;
    }


    @Override
    public boolean isPresentVisitor(HttpServletRequest request) {
        VisitorDao visitorDao = ArrayListVisitorDao.getInstance();
        String address = request.getRemoteAddr();
        boolean isPresentVisitor = visitorDao.containsVisitorWithAddress(address);
        if(!isPresentVisitor){
            visitorDao.addVisitor(new Visitor(address));
        }
        return isPresentVisitor;
    }


    private static class VisitorServiceHolder{
        static final DefaultVisitorService instance = new DefaultVisitorService();
    }
}
