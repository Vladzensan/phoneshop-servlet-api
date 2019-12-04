package com.es.phoneshop.filterUtils;

public interface VisitorDao {
    void addVisitor(Visitor visitor);

    boolean containsVisitorWithAddress(String remoteAddress);
}
