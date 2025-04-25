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
	public BSTreeNode<E> getRoot() throws NullPointerException
	{
		if (root == null)
		{
			throw new NullPointerException("This tree is empty");
		}
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
		if (entry == null)
		{
			throw new NullPointerException("Cannot search for a null");
		}
		
		return search(entry) != null;
	}

	@Override
	public BSTreeNode<E> search(Comparable entry) throws NullPointerException
	{
		if (entry == null)
		{
			throw new NullPointerException("Cannot search for a null");
		}
		
		if (isEmpty())
		{
			return null;
		}
		
		BSTreeNode<E> current = root;
		
		while(current != null)
		{
			int direction = entry.compareTo(current.getElement());
			
			if (direction == 0)
			{
				return current;
			}
			
			else if(direction < 0 )
			{
				current = current.getLeft();
			}
			
			else
			{
				current = current.getRight();
			}
		}
		
		return null; // not found
	}

	@Override
	public boolean add(Comparable newEntry) throws NullPointerException
	{
		if (newEntry == null)
		{
			throw new NullPointerException("Cannot add a null object");
		}
		
		//create the node
		BSTreeNode<E> newNode = new BSTreeNode<E>((E) newEntry, null, null);
		
		if(root == null)
		{
			root = newNode;
			size++;
			return true;
		}
		
		//traverse tree if its not empty
		BSTreeNode<E> current = root;
		
		while(true)
		{
			int direction = newEntry.compareTo(current.getElement());
			
			if (direction == 0)
			{
				// element is already in the tree
				return false;
			}
			
			else if (direction < 0) // left
			{
				if(current.getLeft() == null)
				{
					current.setLeft(new BSTreeNode<E> ((E) newEntry, null, null));
					size++;
					return true;					
				}
				
				current = current.getLeft();				
			}
			
			else  // right
			{
				if(current.getRight() == null)
				{
					current.setRight(new BSTreeNode<E> ((E) newEntry, null, null));
					size++;
					return true;
				}
				
				current = current.getRight();
			}
		}
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

	public Iterator<E> inorderIterator()
	{
		//in-order = left to right, small to big
		/**
		 * Generates an in-order iteration over the contents of the tree. Elements are
		 * in their natural order.
		 * 
		 * @return an iterator with the elements in the natural order
		 */
		
		java.util.List<E> treeTrack = new java.util.ArrayList<>(); // create the arraylist to track the nodes
		
		class InOrderTraverser
		{
			void inOrder(BSTreeNode<E> p)
			{
				if (p != null)
				{
					inOrder(p.getLeft()); // L , recursion
					treeTrack.add(p.getElement()); // V
					inOrder(p.getRight()); //R					
				}
			}
		}
		new InOrderTraverser().inOrder(root);
		
		return new BSTreeIterator(treeTrack);
	}

	public Iterator<E> preorderIterator()
	{
		/**
		 * Generates a pre-order iteration over the contents of the tree. Elements are
		 * order in such a way as the root element is first.
		 * 
		 * @return an iterator with the elements in a root element first order
		 */
		
		java.util.List<E> treeTrack = new java.util.ArrayList<>();
		
		class PreOrderTraverser
		{
			void preOrder(BSTreeNode<E> p)
			{
				if (p != null)
				{
					treeTrack.add(p.getElement()); //V
					preOrder(p.getLeft()); // L
					preOrder(p.getRight()); // R					
				}
			}
		}
		
		new PreOrderTraverser().preOrder(root);
		
		return new BSTreeIterator(treeTrack);
	}

	public Iterator<E> postorderIterator()
	{
		/**
		 * Generates a post-order iteration over the contents of the tree. Elements are
		 * order in such a way as the root element is last.
		 * 
		 * @return an iterator with the elements in a root element last order
		 */
		
		java.util.List<E> treeTrack = new java.util.ArrayList<>();
		
		class PostOrderTraverser
		{
			void postOrder(BSTreeNode<E> p)
			{
				if (p != null)
				{
					postOrder(p.getLeft());
					postOrder(p.getRight());
					treeTrack.add(p.getElement());
				}
			}
		}
		
		new PostOrderTraverser().postOrder(root);

		return new BSTreeIterator(treeTrack);
	}
	
	private class BSTreeIterator implements Iterator<E>
	{
		private int position = 0;
		private java.util.List<E> elements; // list of the <E>
		
		@SuppressWarnings("unused") // delete later when used in BSTree
		public BSTreeIterator(java.util.List<E> elements)
		{
			this.elements = elements;
			this.position = 0;
		}
		
		@Override
		public boolean hasNext()
		{
			if (position < elements.size())
			{
				return true;
			}
			
			else
			{
				return false;	
			}
		}

		@Override
		public E next() throws NoSuchElementException
		{
			if (hasNext() == false)
			{
				throw new NoSuchElementException("There are no more elements.");
			}
			
			return elements.get(position++);
		}
		
	}

}
