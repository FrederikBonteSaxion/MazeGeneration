/**
 * <h2>Mazes in general</h2>
 * <p>This package defines the interfaces that can be used to implement {@link nl.saxion.mazes.Maze}s
 * through which {@link nl.saxion.mazes.Character}s can travel. This package doesn't specify the
 * structure of the maze. If may be two-dimensional, or more. It may define directions as simply
 * north, east, south and west ({@link nl.saxion.mazes.nesw.NESWMaze}), but other ways are possible
 * as well ({@link nl.saxion.mazes.compass.CompassMaze}).</p>
 * <h2>Example</h2>
 * <p>The package {@link nl.saxion.mazes.nesw} can be used to create a maze in the following way:</p>
 * <pre>
 *     	NESWMaze maze = new NESWMaze(15,15);
 * 		maze.printMaze();
 * </pre>
 * <p>To allow a character to navigate the maze you can use the defautl {@link nl.saxion.mazes.Character} class.</p>
 * <pre>
 *     	Character<NESWDirection> pc = new Character<>();
 * 		maze.enter(pc);
 * </pre>
 * <p>Please not that the {@link nl.saxion.mazes.nesw.NESWDirection} is required by this type of maze.</p>
 * <p>Now you can navigate the maze using for instance:</p>
 * <pre>
 *     // Test if moving forward would succeed.
 *     if (pc.canMoveForward()) {
 *         // Actually move forward.
 *         pc.moveForward();
 *     } else {
 *         // Try a different direction.
 *         pc.turnLeft();
 *     }
 * </pre>
 */
package nl.saxion.mazes;
