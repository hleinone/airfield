Airfield is a command-line application written in Scala that counts the distance of a route formed of several airfields. The main purpose of doing this application was to learn Scala and functional programming. You can read the implementation detais on the documentation in [the wiki](http://code.google.com/p/airfield/w/list).

To form a route, the airfields are listed in a text file, which is given to the application as a command-line argument. The application can count the total distance of the predefined
route and also figure out the shortest route of the list of airfields. The starting point of the shortest route can also be specified explicitly.

Usage:
```
java -jar airfield-1.0.jar FILE [--printRouteLength [--findShortestRoute [--startingPoint=XXXX]]]
```

Initially this project was created as the final exercise of [a university course](http://www.cs.helsinki.fi/u/pohjalai/ke08/scala/).