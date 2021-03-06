# Game of Three

## Overview
Game of Three is a game with two independent units – the players communicating with each other using an API.
When a player starts, it incepts a random (whole) number and sends it to the second player as an approach of starting the game. The receiving player can now always choose between adding one of​ {­1, 0, 1} ​to get to a number that is divisible by​ 3. Divide it by three. The resulting whole number is then sent back to the original sender. The same rules are applied until one player reaches the number​ 1 (after the division).

## Implementation detail
The communication protocol between players is JMS message broker. The reason of choosing MOM architecture for this game is that it perfectly fits to the following requirements:

- Communication via an API (REST, Sockets, WebRTC, ...) - the JMS broker is configured to use tcp protocol, so communication via sockets is covered.
- A player may not be available when the other one starts - JMS stores the message until a destination consumer is connected.
- Each player runs on its own (independent programs, two browsers, web‐workers,...) - the JMS broker is a mediator and each player is a separate process which communicates via the broker.

For each player, the JMS destinations on which it listens and send numbers are discovered using spring profiles. Every player will have a separate profile and a separate JMS queue names for listening and sending.

*Workflow:*
![alt tag](https://github.com/dgyordanov/game_of_three/blob/master/Game%20of%20three%20flow.png)

## Run
You need JRE 8 in order to run a game node.
Instructions how to run the whole setup:
- Run a JMS broker instance located in https://github.com/dgyordanov/embedded_jms
- Run a game node after the JMS broker is started

How to run game node  
1. Build using: ./gradlew build  
2. Run the spring boot fat jar using one of the two available profiles(player1, player2)  
* *java -Dspring.profiles.active=player1 -jar build/libs/game_of_three-0.1.0.jar*
* *java -Dspring.profiles.active=player2 -jar build/libs/game_of_three-0.1.0.jar*


NOTE: gradle bootRun task doesn't handle the System.in correctly so please use the commands from point 2 to start game nodes.


After the 3 processes are up and running, the game could start. Enjoy!



