
Application Type

This is a simple Spring boot Appplicationn which runs on port 8080. It has no JPA layer or UI. It exposes single rest enpoint which takes map of strings, and retuns number of steps it takes to go from point A to B according to given problem.


How to Run

gradle bootRun


The web service has one end point to find number stpes in the given map. POST method that accepts "map" as its body. returns a JSON response that
contains a single integer property `steps` that represents the number of steps to get from A to B.

Post Example:

 $curl -X POST --header "Content-Type: application/json" --head
  \"##########\",
  \"#A...#...#\",
  \"#.#.##.#.#\",
  \"#.#.##.#.#\",
  \"#.#....##\",
  \"#.#.##.#.#\",
  \"#....#...#\",
  \"#####.###.##.B\"
]" "http://localhost:8080/api/manageMaps/steps"

Output: 26






