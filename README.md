# Qlearning_Simulator

## Description
This is an an attempt to simulate the [Q-learning](https://en.wikipedia.org/wiki/Q-learning) algorithm on a GridWorld-esque environment, where the agent's objective is to find a path (not necessarily the shortest path) to a given target, while evading obstacles. This is different from classical AI. 
<br> 
<br>
The self-playing agent "learns" a utility function (in this case, the [bellman equation](https://en.wikipedia.org/wiki/Bellman_equation)), which predicts the value of a given state and an associated action (i.e, "what have I gained out of moving left at state X"). It does so by playing the games many times. On a given iteration, the agent makes a move intending to optimize on it state-action values and updates its Q-table (read: "brain") based on the outcome. It then uses the updated Q-table to make the best moves in the next iteration, and repeats until convergece.  In essence, it "learns on the go", becoming more and more powerful after every iteration, a concept pivotal to Reinforcement Learning.   
