# Known Limitations #

Unfortunately there are several things that are either not supported or not very well supported.
  * All methods on the jQuery object that don't require a constructor (like .ajax()) are not supported
  * Deferreds are not supported
  * Function "overloading" is not supported. Some functions like .html() are "overloading" where the function behaves differently on the number of arguments. Either as a setter or a getter. In such cases only one version of the function is shown.
  * Optional arguments are not very well supported. Some functions have optional arguments. This is not very well presented.
  * event object type inference doesn't work if the jQuery object is in a variable
```
var a = $("a");
a.on("click", function(e) {
  // should get completion on e but doesn't work
});
```