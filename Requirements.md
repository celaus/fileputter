# Introduction #

As many projects fileputter is was also created as an answer to a problem. This problem was that every time I wanted to "just share something" e.g. a document, I was not able to do this in a quick and easy way. While in Windows a shared folder is required ... and sometimes also a considerable amout of configuration.

When trying to use instant messengers (like [Pidgin](http://www.pidgin.im)) - file transfers hardly ever work (especially cross-platform ...).

Being a developer my solution was to write a Java program that enables me to do that - without any configuration. I put togehter some requirements:

# Requirements #


## Functional Requirements ##
### The program must be able to: ###
  1. send a file (binary or text) over a network based on IPv4 adresses
  1. receive a file (binary or text) from a network based on IPv4 adresses
  1. detect and add fellow computers automatically within the local network to a list
  1. block adding or being added by other computers
  1. add other computers by their DNS name or IPv4 adress from user input
  1. feature a Graphical User Interface showing the list with the computers name and ip adress
  1. let every user choose an individual name

### The program should be able to: ###
  1. maintain the list automatically (add or remove computers based on their availability)
  1. send folders containing files and subfolders over a network based on IPv4 adresses
  1. remain fully functional without a Graphical User Interface
  1. support sending a file via dragging and dropping files and folders on the GUI
  1. perform a integrity check after transfer




## Non-Functional Requirements ##

### The program must: ###
  1. enable the user to send the files and folders with three clicks (when the path is given)
  1. be developed as an open source project
  1. be able to perform with zero configuration

This list is yet to be completed!