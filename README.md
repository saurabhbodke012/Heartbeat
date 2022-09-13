# Heartbeat

Objectives:

You are supposed to implement the “Heartbeat” Tactic for improving the availability of a self-driving (autonomous) car. The implementation is minimum prototyping of the tactic than full implementation of a self-driving car.

Please consider the following items:
1.	Develop a critical process ( with minimum functionality)
2.	Design a Non-deterministic failure in this process which makes it crash.
3.	Implement Heartbeat to monitor the process
4.	Your heartbeat implementation should have all the required fault detection features.

Rule 1: Do not embed a failure in a static if statement. The failure must be random and it must cause the process crash, avoid making the process sleep.

Rule 2: Implement send/receive/monitoring functions on different processes 

Rule 3: Select a relevant domain: Monitoring connections, Monitoring process, … 

Rule 4: Use inter-process communication mechanisms and remote method invocations; solutions based on networking protocols (e.g. UPD etc.) are not considered for this assignment.

