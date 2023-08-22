package org.pheonix.database;

import org.pheonix.models.Card;

import javax.sql.DataSource;
import java.util.Vector;

public class QueryHandler {
    DataSource dataSource;

    public QueryHandler(DataSource ds){
        dataSource = ds;
    }
    public Vector<String> getTags() {
        return new Vector<>();
    }
    public void insertCard(Card newCard){

    }

}
