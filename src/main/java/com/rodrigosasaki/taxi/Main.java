package com.rodrigosasaki.taxi;

import com.overload.loc.Node;
import com.rodrigosasaki.taxi.agents.Passenger;
import com.rodrigosasaki.taxi.agents.Taxi;
import com.rodrigosasaki.taxi.stage.Stage;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);

    private static final Stage stage = new Stage();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        addTaxi(5, 5);
        addTaxi(9, 9);
        addPassenger(8, 12, 11, 11);
        System.out.println(stage);
        do {
            String line = sc.nextLine();
            List<String> e = Arrays.asList(line.split(" "));
            String command = e.get(0);
            if("taxi".equals(command)){
                addTaxi(Integer.parseInt(e.get(1)), Integer.parseInt(e.get(2)));
            } else if ("pass".equals(command)){
                addPassenger(Integer.parseInt(e.get(1)), Integer.parseInt(e.get(2)), Integer.parseInt(e.get(3)), Integer.parseInt(e.get(4)));
            } else if ("step".equals(command)){
                step();
            } else if ("restart".equals(command)){
                restart();
            } else {
                step();
            }
            System.out.println(stage);
        } while (true);
    }

    private static void addTaxi(int x, int y){
        stage.addTaxi(new Taxi(new Node(x, y), stage));
    }

    private static void addPassenger(int sourceX, int sourceY, int destX, int destY){
        stage.addPassenger(new Passenger(new Node(sourceX, sourceY), new Node(destX, destY)));
    }

    private static void step(){
        stage.nextStep();
    }

    private static void restart(){
        stage.init(null);
    }

}
