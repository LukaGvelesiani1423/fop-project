// Reverses the digits of an integer.
var n = 1234;
var reversed = 0;
var digit;

while (n != 0) {
  digit = n % 10;
  reversed = reversed * 10 + digit;
  n = n / 10;
}
print(reversed);