# Introduction #

The distance between two airfields is calculated using the [Great-circle distance](http://en.wikipedia.org/wiki/Great-circle_distance). The calculation assumes that the earth is a perfectly shaped ball, which can cause insignificant calculation errors. The route calculation is simply adding the distances. The shortest route is found simply by using brute force algorithm.

# Implementation Details #

The GreatCircle object contains the Great-circle distance formula and calculates the distances between two airfields. The total distance of a route is calculated on RouteCalculator which simply adds the distances between the airfields and returns the result. The shortest route brute-force algorithm is optimized so that it doesn't add exactly same routes twice to the list of all possible routes.