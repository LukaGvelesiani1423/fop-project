// Checks if a number is a palindrome.
var n = 121;
var temp = n;
var reversed = 0;
var digit;
var isPalindrome;

while (temp != 0) {
  digit = temp % 10;
  reversed = reversed * 10 + digit;
  temp = temp / 10;
}
isPalindrome = 0;
if(n == reversed) {
  isPalindrome = 1;
}
print(isPalindrome);