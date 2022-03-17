package cs451;

import java.lang.reflect.Array;
import java.rmi.MarshalException;
import java.util.ArrayList;
import java.util.Random;

public class GenericAlgorithmSolver {
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 13;
    private static final boolean elitism = true;

    public static RouteManager evolveRoute(RouteManager routes) {
        // YOUR CODE HERE
        RouteManager newRoutes = new RouteManager(routes.getLength(), false);

        int x = 0;
        if(elitism){ // if elitism == true , then make the first element of new generation elite.
            newRoutes.setRoute(0,routes.findBestFitness());
            x = 1;
        }
        for (; x < routes.getLength(); x++) {
            Route parent1 = tournamentSelection(routes);
            Route parent2 = tournamentSelection(routes);
            Route child = crossover(parent1,parent2);
            mutate(child);
            newRoutes.setRoute(x,child);
        }

        return newRoutes;
    }

    public static Route crossover(Route parent1, Route parent2) {
        // YOUR CODE HERE
        Route child = new Route();
        // Create random cross over space
        int length = parent1.getLength();
        int startIndex = (int) (Math.random() * length);
        int endIndex = startIndex + ( (int) Math.random() * (length-startIndex)) ;

        // between start - end : take genes from parent1
        for (int i = startIndex; i < endIndex; i++) {
            child.setCity(i,parent1.getCity(i));
        }
        int x= 0;
        for (int i = 0; i < length; i++) {

            while (x < length){
                if(child.getCity(i) == null && !child.containsCity(parent2.getCity(x)) ){
                    child.setCity(i,parent2.getCity(x));
                    break;
                }
                x++;
            }

        }


        return child;
    }
    private static void mutate(Route route) {
        // YOUR CODE HERE
        double random;
        for (int i = 0; i < route.getLength(); i++) {
            random = Math.random();
            if(random < mutationRate){
                City cityFirst = route.getCity(i);
                int secondPosition = (int) Math.random() * route.getLength();
                City citySecond = route.getCity(secondPosition);
                route.setCity(i,citySecond);
                route.setCity(secondPosition,cityFirst);
            }
        }
    }

    private static Route tournamentSelection(RouteManager routes) {
        // YOUR CODE HERE
        RouteManager tournament = new RouteManager(tournamentSize,false);
        Route selected = null;
        int randIndex = 0;
        double totalFitness = 0 ;
        for (int i = 0; i < tournamentSize; i++) {
            randIndex = (int) ( Math.random() * routes.getLength() ) ;
            Route r = routes.getRoute(randIndex);
            tournament.setRoute(i,r);
            totalFitness += r.getFitness();
        }

        double rand = Math.random();
        double cumulative = 0;
        for (int i = 0; i < tournament.getLength(); i++) {
            cumulative += tournament.getRoute(i).getFitness() / totalFitness;
            if (rand < cumulative)
                selected = tournament.getRoute(i);
        }

        return selected;
    }


}