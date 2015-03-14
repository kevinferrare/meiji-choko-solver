# Introduction
This is a Java / Swing program that can solve the "[chocolate puzzles](http://www.strapya-world.com/products/22376.html Meiji)" and other [Polyominos](http://en.wikipedia.org/wiki/Polyomino) (tetris-like shape) based puzzles.

![Actual puzzle](https://raw.githubusercontent.com/kevinferrare/meiji-choko-solver/master/src/doc/milk_puzzle.jpg)

Use the Launch button below to run the program using java web start. You need to have java properly installed and [configured](http://mindprod.com/jgloss/installingjws.html) for the program to work.

[![JNLP](https://raw.githubusercontent.com/kevinferrare/meiji-choko-solver/master/src/doc/webstart.png)](https://raw.githubusercontent.com/kevinferrare/meiji-choko-solver/master/src/doc/demo.jnlp)

Alternatively, you can [download the program](https://github.com/kevinferrare/meiji-choko-solver/releases/download/1.0/MeijiChokoSolver-1.0.jar) and launch it.

To quote wikipedia : "A polyomino is a plane geometric figure formed by joining one or more equal squares edge to edge."

The puzzle is solved when all the shapes are placed inside the required area.

This is a form of [tessellation](http://en.wikipedia.org/wiki/Tessellation).

![All found](https://raw.githubusercontent.com/kevinferrare/meiji-choko-solver/master/src/doc/screenshot_meijiblack_all_found.png)

I wrote this some years ago after spending a few hours trying to solve one of the puzzles (the black version) by hand without success.

The program can currently solve the 3 original meiji puzzles and some variants I made.
The algorithm should be able to solve any polyomino based puzzle.

# Running the program
The program can be run by simply using the launch link on the top, or by launching the downloaded jar file.

If you launch it from the command line you can specify additional options.
  * --help : Print help
  * --puzzle : Name of the puzzle to start with, choose one from (WHITE, MILK, MILK_5x12, MILK_4x15, MILK_3x20, MILK_8x8_WITH_RANDOM_BLOCKS, BLACK, BLACK_8x9_WITH_RANDOM_BLOCKS)
  * --output_type : The way the solutions are output, the default is gui but there is also a command line mode ('batch')

# For developpers
The project can be compiled using [maven](http://maven.apache.org/).

Just download the source code and compile it with the following command :
{{{
mvn clean package
}}}

# Author
  * Kévin Ferrare

# License
This project is licensed under the GNU LGPL v3
