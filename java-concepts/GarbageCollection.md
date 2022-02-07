# garbage - collect
1. Serial and Parallel
- both freezes the application
- serial: single thread
- parallel: can input number of threads 
2. Concurrent Mark Sweep / CMS
- multiple threads to mark unused and sweep
- 2 cases only where application pause:
    - marking unused in old generation space
    - while gc is running, then there is a change in heap memory
3. G1 
- designed for larger heap (>4GB)
- replaced CMS for more efficient performance

Reference Material:
https://www.geeksforgeeks.org/types-of-jvm-garbage-collectors-in-java-with-implementation-details/

----
# Concepts
Garbage Collector is a background / daemon thread;

JVM Contents:
- Method Area
- Heap
- JT
- PCR
- NIT

Reference are create in `STACK`. Also, stores the primitive type.
Objects are created in `HEAP`

# Collection
When is garbage collection happen?
- no live thread object
- no object reference
    - point to null
        ``java
            GC g1 = new GC();
            g1 = null;
        ```
    - point to another object reference
        ```java
            GC g1 = new GC();
            GC g2 = new GC();

            g1 = g2;
        ```
    - island of isolations
        ```java
            class GC  {
                GC gc3;
                main() {
                    GC g1 = new GC();
                    GC g2 = new GC();

                    g1.gc3 = g2;
                    g2.gc3 = g1;
                    g1 = null;
                    g2 = null;
                }
            }
            
        ```
# Running

To invoke manually:
```java
    System.gc();
    //or
    Runtime.getRuntime().gc();
    //to check, override the finalize method (invoke just before gc and called once)
```
# Tips
Preferred is `Local` Variable over `Global/Instance` Variable to aid in Garbage Collection

Reference Material: [Code Decode YouTube](https://www.youtube.com/c/CodeDecode/playlists)