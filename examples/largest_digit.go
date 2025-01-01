// Finds the largest digit in a number.
var n = 3947;
var largest = 0;
var digit;

while (n != 0) {
    digit = n % 10;
   if (digit > largest) {
       largest = digit;
   }
   n = n / 10;
}
print(largest);