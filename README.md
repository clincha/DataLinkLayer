## Create an implementation of the Data Link Layer in Java

### Sender Feedback

Marks are divided into several categories, each corresponding to a group
of related tests:

1) Frame encoding: short messages, no tricky chars________ 10/10
2) Frame encoding: short messages, trickier content_______ 5/5
3) Message segmentation: longer messages, no tricky chars_ 8/8
4) MTU compliance: various message lengths and MTU values_ 7/7
5) Robustness: data valid but near borderline_____________ 4/5
6) Robustness: various error conditions___________________ 5/5

**Total = 39/40 (98%)**

Additional feedback from marker:

Excellent. Just one bug. Goes wrong if MTU=10 and message empty (this
doesn't break the rules of the protocol, but exception thrown).



For further details of how your work was marked see:

 
https://www.cs.kent.ac.uk/~pgk/teaching/networks/cw/ug/sender-marking.html
