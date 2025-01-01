// Checks if a number is prime.
var n = 13;
var isPrime = 1;

if (n <= 1) {
   isPrime = 0;
} else {
    var i = 2;
    while (i*i <= n){
        if(n % i == 0) {
           isPrime = 0;
        }
         i = i + 1;
    }
}

print(isPrime);