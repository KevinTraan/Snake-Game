import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Snake extends JPanel implements ActionListener
{

	private final int SQUARE_SIZE = 25;
	private final int GRID_SIZE = 20;
	private final int OFFSET = 11;
	private final int GAMESPEED = 200;
	private Point food;
	private Point head;
	private Point tail;

	private int score;
	private int deaths = -1;
	private Point[] snakePoints;
	private int snakeLength;
	private enum Direction
	{UP, DOWN, LEFT, RIGHT}
	private Direction dir;

	public Snake()
	{
		makeFood();
		addKeyListener(new Listener());
		setBackground(Color.white);
		setFocusable(true);
		setPreferredSize(new Dimension(GRID_SIZE * SQUARE_SIZE, GRID_SIZE * SQUARE_SIZE + OFFSET));
		initSnake();
		startGame();
	}

	private void drawBoard(Graphics g)
	{
		g.setColor(Color.GRAY.brighter());
		for (int i = 0; i < GRID_SIZE * SQUARE_SIZE; i += SQUARE_SIZE)
		{
			for (int j = OFFSET; j < GRID_SIZE * SQUARE_SIZE; j += SQUARE_SIZE)
			{
				if (i % 2 == 0 && j % 2 == 0)
					g.fillRect(i, j, SQUARE_SIZE, SQUARE_SIZE);
				else if (i % 2 == 1 && j % 2 == 1)
					g.fillRect(i, j, SQUARE_SIZE, SQUARE_SIZE);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		checkFood();
		moveSnake();
		checkWallCollision();
		checkSnakeCollision();
		repaint();
	}

	private void checkSnakeCollision()
	{
		for(int i = 1; i < snakeLength; i++)
		{
			if (head.getLocation().equals(snakePoints[i].getLocation()))
			{
				initSnake();
			}
		}
	}
	private void checkWallCollision()
	{
		if(((head.getX() < 0) || (head.getX() >= GRID_SIZE) || (head.getY() < 0) || (head.getY() >= GRID_SIZE)))
		{
			initSnake();
		}

	}
	private void makeFood()
	{
		Random rand = new Random();
		boolean flag = true; // prevent food from appearing inside snake
		Point temp = new Point(0,0);;
		while(flag)
		{
			flag = false;
			temp = new Point(rand.nextInt(GRID_SIZE), rand.nextInt(GRID_SIZE));
			for(int i = 0; i < snakeLength; i++)
			{
				if (temp.getLocation().equals(snakePoints[i]))
				{
					flag = true;
				}
			}
		}
		food = temp;
	}


	private void startGame()
	{
		Timer timer = new Timer(GAMESPEED, this);
		timer.start();
	}

	private void drawText(Graphics g)
	{
		g.drawString("Score: " + score, 5, 10);
		g.drawString("Deaths: " + deaths, 75, 10);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		drawBoard(g);
		drawText(g);
		drawStuff(g);
	}

	private void drawStuff(Graphics g)
	{
		g.setColor(Color.RED);
		g.fillRect((int)food.getX() * SQUARE_SIZE, (int)food.getY() * SQUARE_SIZE + OFFSET, SQUARE_SIZE, SQUARE_SIZE);
		g.setColor(Color.GREEN);
		for (int i = 0; i < snakeLength; i++)
		{
			g.fillRect((int) snakePoints[i].getX() * SQUARE_SIZE, (int) snakePoints[i].getY() * SQUARE_SIZE + OFFSET, SQUARE_SIZE, SQUARE_SIZE);
		}
		g.setColor(Color.black);
		g.fillRect((int) head.getX() * SQUARE_SIZE+5, (int) head.getY() * SQUARE_SIZE + OFFSET + 5, 3, 3);
		g.fillRect((int) head.getX() * SQUARE_SIZE+18, (int) head.getY() * SQUARE_SIZE + OFFSET + 5, 3, 3);
	}

	private void moveSnake()
	{
		if(dir == Direction.UP)
		{
			moveSnake(new Point ((int)head.getX(),(int)head.getY()-1));
		}
		if(dir == Direction.DOWN)
		{
			moveSnake(new Point ((int)head.getX(),(int)head.getY()+1));
		}
		if(dir == Direction.LEFT)
		{
			moveSnake(new Point ((int)head.getX()-1,(int)head.getY()));
		}
		if(dir == Direction.RIGHT)
		{
			moveSnake(new Point ((int)head.getX()+1,(int)head.getY()));
		}
	}

	private void checkFood()
	{
		if(food.getLocation().equals(head.getLocation()))
		{
			score++;
			extendSnake(tail);
			makeFood();
		}
	}

	private void initSnake()
	{
		snakePoints = new Point[GRID_SIZE * GRID_SIZE]; //size of grid
		snakeLength = 0;
		head = new Point (10,10);
		tail = new Point (10,10);
		extendSnake(new Point(10, 10));
		makeFood();
		dir = null;
		deaths++;
		score = 0;
	}

	public void extendSnake(Point point)
	{
		snakePoints[snakeLength] = point;
		snakeLength++;
	}

	public void moveSnake(Point point)
	{
		for (int i = snakeLength; i >= 1; i--)
		{
			snakePoints[i] = snakePoints[i - 1];
		}
		head.setLocation(point);
		snakePoints[0] = point; //new head of snake
	}

	private class Listener implements KeyListener
	{
		@Override
		public void keyTyped(KeyEvent e)
		{
		}

		@Override
		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_W && dir != Direction.DOWN)
			{
				dir = Direction.UP;
			} else if (e.getKeyCode() == KeyEvent.VK_A && dir != Direction.RIGHT)
			{
				dir = Direction.LEFT;
			} else if (e.getKeyCode() == KeyEvent.VK_S && dir != Direction.UP)
			{
				dir = Direction.DOWN;
			} else if (e.getKeyCode() == KeyEvent.VK_D && dir != Direction.LEFT)
			{
				dir = Direction.RIGHT;
			}
		}

		@Override
		public void keyReleased(KeyEvent e)
		{

		}
	}

}

