# Qlearning_Simulator

## Description
This is an an attempt to simulate the [Q-learning](https://en.wikipedia.org/wiki/Q-learning) algorithm on a GridWorld-esque environment, where the agent's objective is to find a path (not necessarily the shortest path) to a given target, while evading obstacles. This is different from classical AI. 
<br> 
<br>
The self-playing agent "learns" a utility function (in this case, the [bellman equation](https://en.wikipedia.org/wiki/Bellman_equation)), which predicts the value of a given state and an associated action (i.e, "what have I gained out of moving left at state X"). In all subsequent iterations, at any given state, the agent makes a move intending to optimize on it state-action values and updates its Q-table (aka "knowledge") based on the outcome. In essence, it "learns on the go", becoming more and more powerful after every iteration, a concept pivotal to Reinforcement Learning.   
