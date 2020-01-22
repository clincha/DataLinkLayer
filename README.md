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

### Sender Feedback

Marks are divided into several categories, each corresponding to a group
of related tests:

1) Frame decoding: short messages, no tricky chars________ 7.5/7.5
2) Frame decoding: short messages, trickier content_______ 6/7.5
3) Message reassembly: longer messages, no tricky chars___ 7/7
4) MTU compliance: various message lengths and MTU values_ 8/8
5) Robustness: frame format errors________________________ 11/11
6) Robustness: segmentation errors and borderline cases___ 3/6
7) Robustness: MTU errors and borderline cases____________ 5/5
8) Robustness: extreme error/borderline cases_____________ 4/8

**Total = 51.5/60 (86%)**

Additional feedback from marker:

Very good. Works in nearly all cases where data valid and detects nearly
all error conditions. Goes wrong if message contains control codes
(valid, note regex has problem with certain codes); physical layer
signals end of stream (i.e. receiveFrame returns null); receiveFrame
returns string including noise before/after frame (valid, affects
several tests).


For further details of how your work was marked see:
https://www.cs.kent.ac.uk/~pgk/teaching/networks/cw/ug/sender-marking.html
