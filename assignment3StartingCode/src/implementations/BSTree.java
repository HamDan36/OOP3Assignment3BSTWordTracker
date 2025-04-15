package implementations;

import java.util.NoSuchElementException;

import utilities.BSTreeADT;
import utilities.Iterator;

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT
{
	private BSTreeNode<E> root;
	private int size;
	
	public BSTree()
	{
		this.root = null;
		this.size = 0;
	}
	
	public BSTree (E element)
	{
		this.root = new BSTreeNode<E>(element, null, null);
		size = 1;
	}

	@Override
	public BSTreeNode getRoot() throws NullPointerException
	{
		return root;
	}

	@Override
	public int getHeight() // height of the full tree as per the ADT specs (check w prof)
	{
		if (root == null)
		{
			return 0;
		}
		
		return root.getHeight(); // reuse code from the node class
	}

	@Override
	public int size() // size of the entire tree, tracked. Add new method to count entire tree??
	{
		return size;
	}

	@Override
	public boolean isEmpty()
	{
		return (root == null);
	}

	@Override
	public void clear()
	{
		root = null;
		size = 0;
		// remove all the other nodes??
	}

	@Override
	public boolean contains(Comparable entry) throws NullPointerException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BSTreeNode search(Comparable entry) throws NullPointerException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(Comparable newEntry) throws NullPointerException
	{
		// add plus one to the size at end of code
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BSTreeNode removeMin()
	{
		//subtract from size
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BSTreeNode removeMax()
	{
		//subtract from size
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator inorderIterator()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator preorderIterator()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator postorderIterator()
	{
		// TODO Auto-generated method stub
		return null;
	}
	

}
