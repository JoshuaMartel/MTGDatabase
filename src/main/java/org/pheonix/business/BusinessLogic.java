package org.pheonix.business;

import org.pheonix.database.QueryHandler;
import org.pheonix.models.Card;

import java.sql.PreparedStatement;
import java.util.Vector;

public class BusinessLogic {
    QueryHandler queryHandler;

    public BusinessLogic(QueryHandler qh) {
        queryHandler = qh;
    }

    public Vector<String> getTags() {
        return  queryHandler.getTags();
    }

    public void insertCard(Card newCard){
        queryHandler.insertCard(newCard);
    }

    public Vector<Card> loadCards(){
        throw new UnsupportedOperationException("");
    }

    public Vector<Card> retrieveSearchResults(PreparedStatement statement){
        throw new UnsupportedOperationException("");
    }


}
