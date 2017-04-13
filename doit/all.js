#!/usr/bin/jjs -fv
var uri = 'http://localhost:8080/doit/api/todos';
var command = "curl ${uri}";
print(command);
$EXEC(command);
var result = $OUT;
print(result);
var resultsAsArray = JSON.parse(result);
print(resultsAsArray);
for (todo in resultsAsArray) {
   print(resultsAsArray[todo].caption + ' - ' + resultsAsArray[todo].description);
}