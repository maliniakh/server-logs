## Running
    ./gradlew run --args="input_file.txt"


## Notes
 - I selected GSON as JSON parser, it seems to have good balance beween being lightweight and robust.  
 - Assumption is made that every file line is a separate JSON entry.  
 - Decided not to use Spring as DI, went for "manual" dependecy injection instead.  
 - I prefer not to use interfaces (for services etc.) without clear reason - same case here.  
 - JDBC connections are not being closed, as I didn't have enough time to figure how to handle that.  
 - A few additional remarks are present in the code as comments.  


## Further improvements
 - Add more test cases, for example with malformed input.    
 - Improve error handling 
 - Probably introducing some project-specific exceptions is the way to go.  
 - Improved resiliancy - for example, currently a single wrong line leads to an exception and runtime termination  
 - Duration threshold could be made configurable.  
 - Using JDBC feels old-fashioned to say the least, it could be nice to improve it somehow.  
 - Adding some constraints is worth considering.  

