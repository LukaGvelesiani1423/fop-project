// Finds the greatest common divisor (GCD) of two integers using the Euclidean algorithm.
var a = 48;
var b = 18;
var temp;

while (b != 0) {
  temp = b;
  b = a % b;
  a = temp;
}
print(a);