# SVOIPClient
The GUI client component of SVOIP

reinventing the wheel to gain experience using JavaFX and FXML

Source code for SVOIP.jar (found in build/externalLibs) can be found here https://github.com/lylerolleman/SVOIP_Protocol


Right now this is VERY basic and very much WIP. 

To use, simply run the SVOIPClient.jar (found in build/dist). Enter a nickname you wish to use in the top right. 
Then, type in the address and port number of another instance of SVOIPClient (the currnet listening port will be listed on startup). 
This does not need to be on a different computer, as it will find an available port on its own. Once connected, the other client's
nickname will appear in the window on the right. Multiple users can be connected at the same time. To send a message to those clients, 
click on their name in the list to select them. Only those selected will see the message sent. ctrl click deselects. 

Currently text is only supported, though voice is coming (along with a lot more QoL features). 
