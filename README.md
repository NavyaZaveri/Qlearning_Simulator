# Qlearning_Simulator

## Description
This is an an attempt to simulate the [Q-learning](https://en.wikipedia.org/wiki/Q-learning) algorithm on a GridWorld-esque environment, where the agent's objective is to find a path (not necessarily the shortest path) to a given target, while evading obstacles. This is different from classical AI. The self-playing agent "learns" a utility function (in this case, the [bellman equation](https://en.wikipedia.org/wiki/Bellman_equation)), which predicts the rewards gained at the end of a known iteration. In all subsequent iterations, at any given state, the agent makes a move based on a heuristic evalution of known rewards and updates its "knowledge" based on the outcome. In essence, it "learns on the go" -- a concept pivotal to Reinforcement Learning.   
