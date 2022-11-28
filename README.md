# SWE400PeerToPeerProject



## Getting started

This project contains the communication layer for the peer-to-peer project, forked from the main repository.  
For details, check out the wiki .  .  .

## Group Members
* Scott Bucher
* Alex Schreffler
* Adam Haertter
* Joelle Godwin
* Brennan Mulligan
* MaKaylie Lucas

## Building the Gradle Project
This must be done first. In IntelliJ, navigate to the build tab and start the build with either the hammer icon or the 
refresh icon. If this is the first time, the Gradle Daemon will begin to download files and configure the project.

![Build tab](https://i.imgur.com/J4eBcns.png)

When this is done in IntelliJ, the 'build' and '.gradle' folders will be highlighted orange in the file tree.

![Gradle folders appropriately highlighted](https://i.imgur.com/Am9qLT0.png)

## Building the Run Configurations
Two run configurations are necessary to be built to run the project. One called "PlayRunner Host" and another called 
"PlayRunner" or "PlayRunner Peer." In IntelliJ, click the dropdown between the build and run options and select "Edit 
Configurations..." to create a new run configuration.
<br>
![Location of dropdown in IntelliJ GUI](https://i.imgur.com/iro85c9.png)


In the following window, fill out the information as follows:

![Run configurations details](https://i.imgur.com/grelVkG.png)

|    Field     |               Values                |
|:------------:|:-----------------------------------:|
|     Type     |             Application             |
| Java Version |               java 11               |
|     -cp      |    SWE400PeerToPeerProject.main     |
|  Main Class  | edu.ship.engr.peertopeer.PlayRunner |
|  Arguments   |   host (only on the Host Config)    |


## Running the Game
Run one of the two above run configurations to start the game. The host configuration will need to run first, followed 
by the peer configuration. Otherwise, the connection will fail.

As of now, both instances (Host and Peer) will need to run off of the same machine by default, because the program uses
localhost as its server.

### Controls
Movement is controlled by either WASD or the arrow keys.
<br>
Interacting with objects is done with the space bar

### Understanding the Board
* \- and | are walls
* \* are unactivated levers
* \# are activated levers
* D are closed doors
* P are players
* 0 are pressure plates
* B are boxes

The host's player always spawns in the left room, and the peer player spawns in the right room. 

### Winning the Game
The goal of the game is to work together to get both players out of the maze. 