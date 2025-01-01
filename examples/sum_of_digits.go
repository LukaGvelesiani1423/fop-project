// Calculates the sum of the digits of a number.
var n = 1234;
var sum = 0;
var digit;

while (n != 0) {
  digit = n % 10;
  sum = sum + digit;
  n = n / 10;
}
print(sum);