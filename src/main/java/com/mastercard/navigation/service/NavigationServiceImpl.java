package com.mastercard.navigation.service;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * This is the Navigation Service Implementation class
 *
 * @author Suresh Maddineni
 * @since 10/03/2020
 *
 */
@Service(value = "navigationService")
public class NavigationServiceImpl implements NavigationService {

    @Value("${master_roads_file}")
    private String cities_file;

    private Graph<String, DefaultEdge> roadGraph;

    /**
     * This Method is invoked by the controller. This is the entry point to find whether both the Starting city and
     * destination city have a connection or not
     * @param startingCity Origin City
     * @param destinationCity Destination City
     * @return true if both the cities have a connection, else false
     *
     */
    @Override
    public boolean connectedRoad(String startingCity, String destinationCity) {

        // Check for the NULL conditions
        if (!StringUtils.isEmpty(startingCity) || !StringUtils.isEmpty(destinationCity)) {
            if (roadGraph == null) {
                roadGraph = createGraph();
            }
            return isConnectedWithDestination_BreadthFirst_Search(startingCity.trim().toLowerCase(), destinationCity.trim().toLowerCase());
        }

        return false;
    }

    /**
     * This will load all the Cities and its connecting roads to the Graph.
     * Cities are loaded as Vertexes and Roads are loaded as Edges, so that We can traverse the Graph while finding
     * whether the connection between the road exists or not
     *
     * @return Graph object, which is populated with all roads and cities
     */
    private Graph<String, DefaultEdge> createGraph() {
        Graph<String, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        List<String> allRoads = new ArrayList<>();

        try {
            allRoads = Files.readAllLines(ResourceUtils.getFile("classpath:" + cities_file).toPath());
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        allRoads.forEach(road -> {
            String[] cities = road.split(",");
            // Make sure add the cities to graph, only when we have 2 cities on a single line
            if (cities.length == 2) {
                String origin = cities[0].trim().toLowerCase();
                String destination = cities[1].trim().toLowerCase();
                graph.addVertex(origin);
                graph.addVertex(destination);
                graph.addEdge(origin, destination);
            }
        });

        return graph;
    }

    /**
     * This will return true if the Origin is connected to Destination by using Depth First Search Algorithm
     *
     * @param origin Origin City
     * @param destination Destination City
     * @return true, if both the cities are connected else false
     */
    public boolean isConnectedWithDestination_DepthFirst_Search(String origin, String destination) {
        Optional<String> startingCity = roadGraph.vertexSet().stream()
                .filter(city -> city.equalsIgnoreCase(origin))
                .findAny();
        if (!startingCity.isPresent()) return false; // If Origin is not in the Graph, return false

        Iterator<String>  cityIterator = new DepthFirstIterator<>(roadGraph, startingCity.get());
        while (cityIterator.hasNext()) {
            String c = cityIterator.next();
            if (c.equals(destination)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This will return true if the Origin is connected to Destination by using Breadth First Search Algorithm
     *
     * @param origin Origin City
     * @param destination Destination City
     * @return true, if both the cities are connected else false
     */
    public boolean isConnectedWithDestination_BreadthFirst_Search(String origin, String destination) {
        Optional<String> startingCity = roadGraph.vertexSet().stream()
                .filter(city -> city.equalsIgnoreCase(origin))
                .findAny();
        if (!startingCity.isPresent()) return false; // If Origin is not in the Graph, return false

        Iterator<String>  cityIterator = new BreadthFirstIterator<>(roadGraph, startingCity.get());
        while (cityIterator.hasNext()) {
            String c = cityIterator.next();
            if (c.equals(destination)) {
                return true;
            }
        }
        return false;
    }
}
