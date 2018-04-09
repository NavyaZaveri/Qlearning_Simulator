# Qlearning_Simulator

## Description
This is an an attempt to simulate the [Q-learning](https://en.wikipedia.org/wiki/Q-learning) algorithm on a GridWorld-esque environment. The agent's objective is to find a path (not necessarily the shortest path) to a given target, while evading obstacles. This is different from classical AI. 
<br> 
<br>
The self-playing agent tries to optimize a utility function (in this case, the [bellman equation](https://en.wikipedia.org/wiki/Bellman_equation)), which predicts the value of a given state and an associated action. It does so by playing the game many times. On a given iteration, the agent makes the best move it thinks it can make and updates its Q-table (read: "brain") based on the outcome. It then uses the updated Q-table to make the best moves in the next iteration, and repeats until convergence.  In essence, it "learns on the go", becoming more and more powerful after every iteration, a concept pivotal to Reinforcement Learning.   
