=================================
Sudoku Solver

By: Gilberto Perez and Kana Horie
=================================

The SudokuSolver project for CS271 F2016 implements three different solvers, which are incrementally more powerful.

Based on our results, BTSConstraintMRV appears to be the most the stable. As such, that is the solver that is used by default.

The ThreeSudoku.jar is the driver program that was used to run the benchmarks provided to us. Using it is very simple:

$ java -jar ThreeSudoku.jar /path/to/benchmark/file

A second driver program is also provided called 'SudokuDriver.jar'.  

$ java -jar SudokuDriver.jar

This program does not take any command line arguments. Instead it prompts the user for what he would like to do. It starts by asking the user what problem he would like to solve(e.g 3Sudoku, 4Sudoku).

Next, the user will be prompted with the following menu:

1) Generate a random Sudoku puzzle
2) Enter a Sudoku puzzle
0) Quit

If a Sudoku puzzle is generated or entered, the user is then asked what solver he would like to use:

1) BTS Constraint
2) BTS Constraint with MRV
3) BTS Constraint with MRV and MAV

Since the first two solvers have a random component, the user is then asked how many times would he like to run the solver.

Finally, the program will output the solution as well as runtime and backtracks statistics.