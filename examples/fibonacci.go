// Computes the Nth Fibonacci number using iteration.
var n = 10;
var a = 0;
var b = 1;
var i = 2;
var result;

if (n == 0){
  print(0);
} else if (n == 1){
 print(1);
} else {
    while (i <= n) {
      result = a + b;
      a = b;
      b = result;
      i = i + 1;
    }
    print(result);
}