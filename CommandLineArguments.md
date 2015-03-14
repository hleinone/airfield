# Introduction #

There are five command-line arguments that can be given to the application.

| `FILE` | The path to the file from where the route is read. |
|:-------|:---------------------------------------------------|
| `--help` | Shows the application help. |
| `--findShortestRoute` | Indicates that the application should calculate the shortest route through the aifields listed in the input file. |
| `--startingPoint=XXXX` | The route starting point can be explicitly set using this command in addition to the `--findShortestRoute` command. |
| `--printRouteLength` | Prints the route length. |

# Implementation details #

## Command ##
The functionality required by a command-line argument is encapsulated in a corresponding Command class. This is by Gang of Four [Command pattern](http://en.wikipedia.org/wiki/Command_pattern).

### Chain-of-Responsibility ###
Since the Commands can be piped in certain order we're using Gang of Four [Chain-of-Responsibility](http://en.wikipedia.org/wiki/Chain-of-responsibility_pattern) to pass the result of preceding Command to the next one. Each Command is generized to declare what type of Commands it can follow.

### Factory Method ###
The Commands are being created at CommandFactory, which is done by Gang of Four [Factory Method pattern](http://en.wikipedia.org/wiki/Factory_method_pattern). It takes the command-line arguments as a parameter and returns the Command that is to be executed.

![http://airfield.googlecode.com/svn/commands.png](http://airfield.googlecode.com/svn/commands.png)<br></br>
_The Command object hierarchy of the application._