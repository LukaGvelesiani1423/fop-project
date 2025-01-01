// Computes the factorial of a given number N.
var n = 5;
var factorial = 1;
var i = 1;

while (i <= n) {
  factorial = factorial * i;
  i = i + 1;
}
print(factorial);