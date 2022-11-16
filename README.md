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

## Building the Run Configurations
Two run configurations are necessary to be built to run the project. One called "PlayRunner Host" and another called 
"PlayRunner" or "PlayRunner Peer." In IntelliJ, click the dropdown between the build and run options and select "Edit 
Configurations..." to create a new run configuration.
<br>
![Location of dropdown in IntelliJ GUI](https://i.imgur.com/iro85c9.png)
<br><br>
In the following window, fill out the information as follows:

![Run configurations details](https://i.imgur.com/grelVkG.png)

|    Field     |               Values                |
|:------------:|:-----------------------------------:|
|     Type     |             Application             |
| Java Version |               java 11               |
|     -cp      |    SWE400PeerToPeerProject.main     |
|  Main Class  | edu.ship.engr.peertopeer.PlayRunner |
|  Arguments   |   host (only on the Host Config)    |


Both instances (Host and Peer) will need to run off of the same machine by default, because the program uses localhost.