# Mastercard code challenge

This is a Spring Boot Application, which determines whether the given 2 cities are connected or not based on the initial 'citi_roads.txt' file.

This uses 'jgrapht' library, which is very effective in performing the Graph Search and easy to implement. It has both BreadthFirst Search and DepthFirst Search algorithms implemented. But the current application is enabled with BreadthFirst algorithm.

# Prerequisites

1) Java 8+
2) Gradle

# Installation and Running

1) Checkout the repo from the below git respository location

git clone https://github.com/smaddineni16/mastercard.git

2) Do Gradle build

gradle build

3) Run the Application

gradle bootRun

4) Launch the URL http://localhost:8080/connected?origin=new york& destination=washington dc
  
# Test cases

In order to run the test cases, you can execute the below command

gradle test

# Happy Coding
