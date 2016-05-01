package com.rodrigosasaki.taxi.agents;

public enum TaxiState {

    FREE('1'),
    EN_ROUTE_TO_PASSENGER('2'),
    OCCUPIED('3');

    private char value;

    TaxiState(char value){
        this.value = value;
    }

    public char getValue(){
        return value;
    }

}
