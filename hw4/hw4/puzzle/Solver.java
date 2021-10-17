package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;
import java.util.List;


public class Solver {
    private SearchNode initNode;
    private SearchNode goalNode;
    private int i;

    private class  SearchNode implements  Comparable<SearchNode>{
        private WorldState state;
        private int moves;
        private SearchNode prev;
        private int priority;

        public SearchNode(WorldState state,int moves,SearchNode prev){
            this.state=state;
            this.moves=moves;
            this.prev=prev;
            this.priority = moves + state.estimatedDistanceToGoal();

        }
        @Override
        public int compareTo(SearchNode o) {
            return this.priority - o.priority;
        }

    }


    /*
    Constructor which solves the puzzle, computing
    everything necessary for moves() and solution() to
    not have to solve the problem again. Solves the
    puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial){
        MinPQ<SearchNode> queue = new MinPQ<>();
        initNode=new SearchNode(initial,0,null);
        initNode.prev=initNode;
        queue.insert(initNode);
        while(!queue.isEmpty()){
            SearchNode minNode=queue.delMin();
            if(minNode.state.isGoal()){
                goalNode=minNode;
                break;
            }
            for(WorldState neighbor:minNode.state.neighbors()){
                if(!neighbor.equals(minNode.prev.state)){
                    int moves= minNode.moves+1;
                    queue.insert(new SearchNode(neighbor,moves,minNode));
                }
            }
        }
        //search(queue);
    }
    private void search(MinPQ<SearchNode> queue){
        i=i+1;
        System.out.println(i);
       SearchNode minNode=queue.min();
       queue.delMin();
       if(minNode.state.isGoal()){
           goalNode=minNode;
           return;
       }
       for(WorldState neighbor:minNode.state.neighbors()){
           if(neighbor!=minNode.prev.state){
               int moves= minNode.moves+1;
               queue.insert(new SearchNode(neighbor,moves,minNode));
           }
       }
       search(queue);

    }

    /*
    Returns the minimum number of moves to solve the puzzle starting
    at the initial WorldState.
     */
    public int moves(){
        return goalNode.moves;
    }
    /*
    Returns a sequence of WorldStates from the initial WorldState
    to the solution.
     */
    public Iterable<WorldState> solution(){
        List<WorldState> path=new LinkedList<>();
        for(SearchNode node=goalNode;node!=node.prev;node=node.prev){
            path.add(0,node.state);
        }
        return path;
    }
}

